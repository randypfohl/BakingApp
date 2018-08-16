package com.pfohl.bakingapp.bakingapp.RecipeList;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pfohl.bakingapp.bakingapp.IdlingResource.BakingIdlingResource;
import com.pfohl.bakingapp.bakingapp.R;
import com.pfohl.bakingapp.bakingapp.Repo.Model.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    // static
    private static String TAG = MainActivity.class.getSimpleName();
    private static int GRID_COLUMNS = 3;

    // View Binding
    @Nullable @BindView(R.id.recipe_rv) RecyclerView recipesRV;

    @Nullable @BindView(R.id.recipe_tablet_rv) RecyclerView recipesTabletRV;

    @Nullable
    private BakingIdlingResource mIdlingResource;

    //Data
    ArrayList<Recipe> recipeList = new ArrayList<>();

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new BakingIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getIdlingResource();
        mIdlingResource.setIdleState(false);
        if(savedInstanceState != null){
            recipeList = savedInstanceState.getParcelableArrayList("Recipes");
        }

        RecipeViewModel viewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        viewModel.recipes.observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> data) {
                recipeList = new ArrayList<>(data);
                if(recipesTabletRV == null){
                    recipesRV.swapAdapter(new RecipeListAdapter(data), true);
                }
                else {
                    recipesTabletRV.swapAdapter(new RecipeListAdapter(data), true);
                }
            }
        });

        viewModel.loadRecipes();
        RecipeListAdapter adapter = new RecipeListAdapter(recipeList);

        if(recipesTabletRV == null) {
            recipesRV.invalidate();
            recipesRV.setAdapter(adapter);
            recipesRV.setLayoutManager(new LinearLayoutManager(this));
        }
        else {
            recipesTabletRV.invalidate();
            recipesTabletRV.setAdapter(adapter);
            recipesTabletRV.setLayoutManager(new GridLayoutManager(this, GRID_COLUMNS ));
        }
        mIdlingResource.setIdleState(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("Recipes", recipeList);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
