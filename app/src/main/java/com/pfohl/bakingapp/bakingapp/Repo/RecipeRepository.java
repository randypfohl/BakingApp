package com.pfohl.bakingapp.bakingapp.Repo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.pfohl.bakingapp.bakingapp.R;
import com.pfohl.bakingapp.bakingapp.Repo.Model.Recipe;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class RecipeRepository {

    public static String RECIPE_KEY = "rec_index";

    public static List<Recipe> getRecipes(Context context){
        Recipe[] recipes = null;
        InputStream in = context.getResources().openRawResource(R.raw.recipes);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        Gson gson = new Gson();
        recipes = gson.fromJson(bufferedReader, Recipe[].class);
        return Arrays.asList(recipes);
    }

    public static Recipe getLastViewedRecipe(Context context){
        List<Recipe> recipes = getRecipes(context);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int index = prefs.getInt(RECIPE_KEY, 0);
        return recipes.get(index);
    }

    public static void setLastViewedRecipe(Context context, Recipe recipe){
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putInt(RECIPE_KEY, recipe.getId());
        prefsEditor.commit();
    }
}
