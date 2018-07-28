package com.pfohl.bakingapp.bakingapp.RecipeList;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.pfohl.bakingapp.bakingapp.Repo.Model.Recipe;
import com.pfohl.bakingapp.bakingapp.Repo.RecipeRepository;

import java.util.List;

public class RecipeViewModel extends ViewModel {

    private String TAG = RecipeViewModel.class.getSimpleName();

    public MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();

    public void loadRecipes(Context context){
        List<Recipe> list = RecipeRepository.getRecipes(context);
        recipes.setValue(list);
    }
}
