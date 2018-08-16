package com.pfohl.bakingapp.bakingapp.RecipeList;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.pfohl.bakingapp.bakingapp.Network.RecipeUtil;
import com.pfohl.bakingapp.bakingapp.Repo.Model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeViewModel extends ViewModel {

    private String TAG = RecipeViewModel.class.getSimpleName();

    public MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();

    public void loadRecipes(){
       RecipeUtil.getRecipes(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipes.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
