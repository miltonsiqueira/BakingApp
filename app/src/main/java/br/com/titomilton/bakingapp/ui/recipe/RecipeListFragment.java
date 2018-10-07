package br.com.titomilton.bakingapp.ui.recipe;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.titomilton.bakingapp.R;
import br.com.titomilton.bakingapp.api.RestAPI;
import br.com.titomilton.bakingapp.entity.Recipe;
import br.com.titomilton.bakingapp.utils.NetworkUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnRecipeListFragmentListener}
 * interface.
 */
public class RecipeListFragment extends Fragment implements Callback<List<Recipe>> {

    private static String LOG = RecipeListFragment.class.getName();

    // TODO: Customize parameters
    private int mColumnCount = 1;

    private List<Recipe> recipes = new ArrayList<>();
    private RecipeRecyclerViewAdapter mAdapter;

    private OnRecipeListFragmentListener mListener;

    @BindView(R.id.recipe_list)
    RecyclerView recyclerView;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar progressBar;

    Unbinder unbinder;

    public RecipeListFragment() {
    }

    @Override
    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
        try {
            Context context = this.getContext();
            setProgressOn(false);
            if (response.isSuccessful()) {

                Log.d(LOG, "Recipes loaded");

                this.recipes = response.body();
                mAdapter.setRecipes(this.recipes);


                Toast.makeText(context, "Recipes loaded", Toast.LENGTH_SHORT).show();
            } else {
                String errorMessage = response.errorBody().string();
                Log.e(LOG, errorMessage);
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();

            }

        } catch (IOException e) {
            Log.e(LOG, e.getMessage(), e);
            Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<List<Recipe>> call, Throwable t) {
        setProgressOn(false);
        Log.e(LOG, t.getMessage(), t);
        Toast.makeText(this.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        mColumnCount = getResources().getInteger(R.integer.recipes_columns);
        final Context context = view.getContext();
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        mAdapter = new RecipeRecyclerViewAdapter(recipes, mListener);
        recyclerView.setAdapter(mAdapter);

        setProgressOn(true);

        Log.d(LOG, "Getting recipes");

        if (NetworkUtils.isConnected(context)) {
            RestAPI.getRecipes(this);
        } else {
            setProgressOn(false);

            Toast.makeText(context, R.string.recipes_cannot_be_loaded, Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void setProgressOn(boolean isOn) {
        if(isOn) {
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeListFragmentListener) {
            mListener = (OnRecipeListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStepListListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(getString(R.string.app_title));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface OnRecipeListFragmentListener {
        void onListFragmentInteraction(Recipe recipe);
    }
}
