package com.pfohl.bakingapp.bakingapp.Recipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pfohl.bakingapp.bakingapp.R;
import com.pfohl.bakingapp.bakingapp.Repo.Model.Recipe;

public class RecipeActivity extends AppCompatActivity {

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Intent intent = getIntent();
        this.recipe = intent.getParcelableExtra(getString(R.string.recipe_intent_tag));
    }
}
