package br.com.titomilton.bakingapp.ui.recipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.titomilton.bakingapp.R;


public class RecipeDetailIngredientsStepsFragment extends Fragment {

    public RecipeDetailIngredientsStepsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_detail_ingredients_steps, container, false);
    }

}
