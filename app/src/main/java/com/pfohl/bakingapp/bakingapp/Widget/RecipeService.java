package com.pfohl.bakingapp.bakingapp.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.pfohl.bakingapp.bakingapp.R;
import com.pfohl.bakingapp.bakingapp.Repo.Model.Recipe;
import com.pfohl.bakingapp.bakingapp.Repo.RecipeRepository;

public class RecipeService extends IntentService {

    public static final String ACTION_UPDATE_LIST_VIEW = "get_recipe";
    public static final String RECIPE_KEY = "recipe";

    public RecipeService() {
        super("RecipeService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent !=null){
            final String action = intent.getAction();
            if (ACTION_UPDATE_LIST_VIEW.equals(action)){
                Recipe recipe = intent.getParcelableExtra(RECIPE_KEY);
                handleActionUpdateListView(recipe);
            }
        }
    }

    private void handleActionUpdateListView(Recipe recipe) {
        if (recipe != null) {
            RecipeRepository.setLastViewedRecipe(this, recipe);
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidget.class));

        for(int id : appWidgetIds){
            RecipeWidget.updateAppWidget(this,appWidgetManager,id);
        }

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.ingredients_list);
    }

    public static void startActionUpdateListView(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeService.class);
        intent.setAction(RecipeService.ACTION_UPDATE_LIST_VIEW);
        intent.putExtra(RECIPE_KEY, recipe);
        context.startService(intent);
    }
}