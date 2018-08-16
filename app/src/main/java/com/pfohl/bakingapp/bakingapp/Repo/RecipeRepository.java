package com.pfohl.bakingapp.bakingapp.Repo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.pfohl.bakingapp.bakingapp.Repo.Model.Recipe;


public class RecipeRepository {

    public static String RECIPE_KEY = "recipe";

    public static Recipe getLastViewedRecipe(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(RECIPE_KEY, null);
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(json, Recipe.class);
       return recipe;
    }

    public static void setLastViewedRecipe(Context context, Recipe recipe){
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(recipe);
        prefsEditor.putString(RECIPE_KEY, json);
        prefsEditor.commit();
    }
}
