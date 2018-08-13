package com.pfohl.bakingapp.bakingapp.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.pfohl.bakingapp.bakingapp.R;
import com.pfohl.bakingapp.bakingapp.Repo.Model.Ingredient;
import com.pfohl.bakingapp.bakingapp.Repo.Model.Recipe;
import com.pfohl.bakingapp.bakingapp.Repo.RecipeRepository;

import java.util.ArrayList;
import java.util.List;

public class ListViewWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new AppWidgetListView(getApplicationContext());
    }
}

class AppWidgetListView implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    List<Ingredient> ingredients = new ArrayList<>();
    Recipe recipe = null;

    public AppWidgetListView(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        recipe = RecipeRepository.getLastViewedRecipe(context);
        ingredients = recipe.getIngredients();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_list_item);

        remoteViews.setTextViewText(R.id.ingredient_name_widget_tv, ingredients.get(position).getIngredient());
        remoteViews.setTextViewText(R.id.ingredient_amount_widget_tv, ingredients.get(position).getQuantity() + " " + ingredients.get(position).getMeasure());

        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(RecipeRepository.RECIPE_KEY, recipe);
        remoteViews.setOnClickFillInIntent(R.id.parent_view, fillInIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
