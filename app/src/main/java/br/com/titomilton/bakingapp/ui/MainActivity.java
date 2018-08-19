package br.com.titomilton.bakingapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import br.com.titomilton.bakingapp.R;
import br.com.titomilton.bakingapp.entity.Recipe;

public class MainActivity extends AppCompatActivity implements RecipeListFragment.OnListFragmentInteractionListener {
    private RecipeViewModel recipeViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recipeViewModel =  ViewModelProviders.of(this).get(RecipeViewModel.class);
    }

    @Override
    public void onListFragmentInteraction(Recipe recipe) {
        recipeViewModel.setRecipe(recipe);
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE, recipe);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "Recipe item clicked", Toast.LENGTH_SHORT).show();
    }


}
