package br.com.titomilton.bakingapp.ui;

import android.arch.lifecycle.ViewModel;

import br.com.titomilton.bakingapp.entity.Recipe;

public class RecipeViewModel extends ViewModel {
    private Recipe recipe;

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}