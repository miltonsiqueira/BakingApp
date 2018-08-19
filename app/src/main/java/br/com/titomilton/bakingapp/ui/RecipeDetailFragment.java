package br.com.titomilton.bakingapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.titomilton.bakingapp.R;
import br.com.titomilton.bakingapp.entity.Ingredient;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class RecipeDetailFragment extends Fragment {

    @BindView(R.id.tv_ingredients)
    TextView tvIngredients;
    private Unbinder unbinder;
//    private OnFragmentInteractionListener mListener;
    private RecipeViewModel recipeViewModel;


    public RecipeDetailFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recipeViewModel = ViewModelProviders.of(getActivity()).get(RecipeViewModel.class);

        if (recipeViewModel.getRecipe() != null) {
            setIngredients();
        }
    }

    private void setIngredients() {
        StringBuilder sb = new StringBuilder();
        for (Ingredient ingredient : recipeViewModel.getRecipe().getIngredients()) {
            sb.append("- ")
                    .append(ingredient.getIngredient())
                    .append(" (")
                    .append(ingredient.getQuantity())
                    .append(" ")
                    .append(ingredient.getMeasure())
                    .append(")\n");
        }
        this.tvIngredients.setText(sb.toString());
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
