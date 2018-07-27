package com.pfohl.bakingapp.bakingapp.Repo;

import android.content.Context;

import com.google.gson.Gson;
import com.pfohl.bakingapp.bakingapp.R;
import com.pfohl.bakingapp.bakingapp.Repo.Model.Recipe;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class RecipeRepository {

    public static List<Recipe> getRecipes(Context context){
        Recipe[] recipes = null;
        InputStream in = context.getResources().openRawResource(R.raw.recipes);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        Gson gson = new Gson();
        recipes = gson.fromJson(bufferedReader, Recipe[].class);
        return Arrays.asList(recipes);
    }
}
