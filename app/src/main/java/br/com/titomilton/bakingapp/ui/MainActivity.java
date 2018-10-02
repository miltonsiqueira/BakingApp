package br.com.titomilton.bakingapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import br.com.titomilton.bakingapp.R;
import br.com.titomilton.bakingapp.entity.Recipe;
import br.com.titomilton.bakingapp.entity.Step;
import br.com.titomilton.bakingapp.ui.recipe.RecipeDetailFragment;
import br.com.titomilton.bakingapp.ui.recipe.RecipeListFragment;
import br.com.titomilton.bakingapp.ui.step.StepFragment;
import br.com.titomilton.bakingapp.ui.step.StepListFragment;

public class MainActivity extends AppCompatActivity implements RecipeListFragment.OnRecipeListFragmentListener, StepListFragment.OnStepListListener {

    private MainViewModel mainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        if (savedInstanceState == null) {
            Fragment fragment = new RecipeListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    @Override
    public void onListFragmentInteraction(Recipe recipe) {
        mainViewModel.setRecipe(recipe);
        replaceFragment(new RecipeDetailFragment());
    }

    @Override
    public void onStepListClick(Step item) {
        mainViewModel.setStep(item);
        replaceFragment(new StepFragment());
    }


}
