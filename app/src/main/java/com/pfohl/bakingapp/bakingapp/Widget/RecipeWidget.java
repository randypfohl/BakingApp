package com.pfohl.bakingapp.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.pfohl.bakingapp.bakingapp.R;
import com.pfohl.bakingapp.bakingapp.RecipeList.MainActivity;
import com.pfohl.bakingapp.bakingapp.Repo.Model.Recipe;
import com.pfohl.bakingapp.bakingapp.Repo.RecipeRepository;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Recipe recipe = RecipeRepository.getLastViewedRecipe(context);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        if(recipe != null){
            views.setTextViewText(R.id.recipe_list_name, recipe.getName());
        }

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.ingredient_name_widget_tv, pendingIntent);

        Intent intentService = new Intent(context, ListViewWidgetService.class);
        views.setRemoteAdapter(R.id.ingredients_list,intentService);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

