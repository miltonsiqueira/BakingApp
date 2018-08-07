package br.com.titomilton.bakingapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.titomilton.bakingapp.R;
import br.com.titomilton.bakingapp.entity.Recipe;

public class MainActivity extends AppCompatActivity implements RecipeListFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onListFragmentInteraction(Recipe recipe) {

    }


}
