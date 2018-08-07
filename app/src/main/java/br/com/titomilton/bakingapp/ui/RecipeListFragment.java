package br.com.titomilton.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.titomilton.bakingapp.R;
import br.com.titomilton.bakingapp.api.RestAPI;
import br.com.titomilton.bakingapp.entity.Recipe;
import br.com.titomilton.bakingapp.utils.AppExecutors;
import br.com.titomilton.bakingapp.utils.NetworkUtils;
import database.AppDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class RecipeListFragment extends Fragment implements Callback<List<Recipe>> {
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";

    public static String LOG = RecipeListFragment.class.getName();

    // TODO: Customize parameters
    private int mColumnCount = 1;

    private List<Recipe> recipes = new ArrayList<>();
    private RecipeRecyclerViewAdapter mAdapter;

    private OnListFragmentInteractionListener mListener;
    private AppDatabase appDatabase;
    private AppExecutors appExecutors;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeListFragment() {
    }

    public static RecipeListFragment newInstance(int columnCount) {
        RecipeListFragment fragment = new RecipeListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
        try {
            Context context = this.getContext();
            if (response.isSuccessful()) {
                this.recipes = response.body();

                appDatabase.sync(context, recipes);

                mAdapter.setRecipes(this.recipes);

                Toast.makeText(context, "Recipes loaded", Toast.LENGTH_SHORT);
            } else {


                String errorMessage = response.errorBody().string();
                Log.e(LOG, errorMessage);
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG);

            }
        } catch (IOException e) {
            Log.e(LOG, e.getMessage(), e);
            Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onFailure(Call<List<Recipe>> call, Throwable t) {
        Log.e(LOG, t.getMessage(), t);
        Toast.makeText(this.getContext(), t.getMessage(), Toast.LENGTH_LONG);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        final Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view;
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        mAdapter = new RecipeRecyclerViewAdapter(recipes, mListener);
        recyclerView.setAdapter(mAdapter);

        appDatabase = AppDatabase.getInstance(context);
        appExecutors = AppExecutors.getInstance();

        if (recipes.isEmpty()) {
            final RecipeListFragment self = this;

            appExecutors.diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    recipes = appDatabase.recipeDao().getAll();
                    if (NetworkUtils.isConnected(context)) {
                        RestAPI.getRecipes(self);
                    }

                }
            });
        }


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Recipe recipe);
    }
}