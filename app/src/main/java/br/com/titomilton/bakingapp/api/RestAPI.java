package br.com.titomilton.bakingapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import br.com.titomilton.bakingapp.entity.Recipe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RestAPI {
    private static String BASE_URL = "http://go.udacity.com/";

    public static void getRecipes(Callback<List<Recipe>> callback) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RecipeService service = retrofit.create(RecipeService.class);

        Call<List<Recipe>> call = service.getRecipes();
        call.enqueue(callback);

    }

}
