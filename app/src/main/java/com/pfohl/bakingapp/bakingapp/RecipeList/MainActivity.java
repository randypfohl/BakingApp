package com.pfohl.bakingapp.bakingapp.RecipeList;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pfohl.bakingapp.bakingapp.R;
import com.pfohl.bakingapp.bakingapp.Repo.Model.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    // static
    private static String TAG = MainActivity.class.getSimpleName();

    // View Binding
    @BindView(R.id.recipe_rv) RecyclerView recipesRV;

    //Data
    List<Recipe> recipeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        RecipeViewModel viewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        viewModel.recipes.observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> data) {
                recipeList = data;
                recipesRV.swapAdapter(new RecipeListAdapter(data), true);
            }
        });

        viewModel.loadRecipes(this);
        RecipeListAdapter adapter = new RecipeListAdapter(recipeList);
        recipesRV.setAdapter(adapter);
        recipesRV.setLayoutManager(new LinearLayoutManager(this));
    }
}
