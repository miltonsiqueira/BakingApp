package br.com.titomilton.bakingapp.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.titomilton.bakingapp.R;
import br.com.titomilton.bakingapp.entity.Recipe;
import br.com.titomilton.bakingapp.ui.RecipeListFragment.OnListFragmentInteractionListener;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Recipe} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder> {

    private List<Recipe> mRecipes;
    private final OnListFragmentInteractionListener mListener;

    public RecipeRecyclerViewAdapter(List<Recipe> items, OnListFragmentInteractionListener listener) {
        mRecipes = items;
        mListener = listener;
    }

    public void setRecipes(List<Recipe> data) {
        this.mRecipes = data;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recipe_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Recipe recipe = mRecipes.get(position);
        holder.mItem = recipe;
        holder.mContentView.setText(recipe.getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.content) TextView mContentView;
        public Recipe mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
