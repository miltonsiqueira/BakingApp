package br.com.titomilton.bakingapp.api;

import java.util.List;

import br.com.titomilton.bakingapp.entity.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

interface RecipeService {
    @GET("http://go.udacity.com/android-baking-app-json")
    Call<List<Recipe>> getRecipes();
}
