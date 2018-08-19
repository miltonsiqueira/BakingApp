package br.com.titomilton.bakingapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import br.com.titomilton.bakingapp.R;
import br.com.titomilton.bakingapp.entity.Recipe;
import br.com.titomilton.bakingapp.entity.Step;

public class RecipeDetailActivity extends AppCompatActivity implements StepListFragment.OnStepListListener {

    public static final String EXTRA_RECIPE = "extra_recipe";
    private RecipeViewModel recipeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_RECIPE)) {
                Recipe recipe = intent.getParcelableExtra(EXTRA_RECIPE);
                recipeViewModel.setRecipe(recipe);
            }
        }
    }

    @Override
    public void onStepListClick(final Step item) {
        Log.d(RecipeDetailActivity.class.getSimpleName(), "Step item clicked " + item.getDescription());
        Toast.makeText(getBaseContext(), "Step item clicked", Toast.LENGTH_SHORT).show();
    }


}
