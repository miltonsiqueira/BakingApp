package br.com.titomilton.bakingapp.ui.recipe;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.titomilton.bakingapp.R;
import br.com.titomilton.bakingapp.ui.MainActivity;

public class RecipeDetailFragment extends Fragment {


    public RecipeDetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.addFragmentOnStepContainer();

        super.onActivityCreated(savedInstanceState);
    }
}
