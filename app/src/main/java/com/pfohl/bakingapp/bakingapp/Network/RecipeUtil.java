package com.pfohl.bakingapp.bakingapp.Network;

import com.pfohl.bakingapp.bakingapp.Repo.Model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class RecipeUtil {

    public static String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net";

    public interface RecipeService {
        @GET("topher/2017/May/59121517_baking/baking.json")
        Call<List<Recipe>> listRecipes();
    }


    public static void getRecipes(Callback<List<Recipe>> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RECIPE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeService service = retrofit.create(RecipeService.class);
        Call<List<Recipe>> recipeCall = service.listRecipes();

        recipeCall.enqueue(callback);
    }
}
