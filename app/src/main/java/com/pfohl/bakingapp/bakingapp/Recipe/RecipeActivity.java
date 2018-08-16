package com.pfohl.bakingapp.bakingapp.Recipe;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pfohl.bakingapp.bakingapp.Ingredient.IngredientListFragment;
import com.pfohl.bakingapp.bakingapp.R;
import com.pfohl.bakingapp.bakingapp.Repo.Model.Recipe;
import com.pfohl.bakingapp.bakingapp.Repo.Model.Step;
import com.pfohl.bakingapp.bakingapp.StepDetails.StepDetailsFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeActivity extends AppCompatActivity implements StepListFragment.OnSelectClickListener, StepDetailsFragment.NavigationListener {

    private Recipe recipe;
    private Unbinder unbinder;
    boolean tablet = false;
    boolean list = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        unbinder = ButterKnife.bind(this);
        Intent intent = getIntent();
        this.recipe = intent.getParcelableExtra(getString(R.string.recipe_intent_tag));

        tablet = findViewById(R.id.details_frame) != null;

        if(savedInstanceState == null){
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("steplist", recipe.getSteps());
            StepListFragment stepList = new StepListFragment();
            stepList.setOnSelectListener(this);
            stepList.setArguments(bundle);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.step_fragment_frame, stepList, "stepListFrag");
            transaction.commit();
        }
        else {

            if(!list){
                if (savedInstanceState != null) {
                    StepListFragment step = (StepListFragment) getFragmentManager()
                            .findFragmentByTag("stepListFrag");
                    if (step != null) {
                        step.setOnSelectListener(this);
                    }
                }
            }

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @Override
    public void onItemClick(int position) {
        if(position >= 0){
            setStepDetails(recipe.getSteps().get(position));
        }
        else {
            showIngredients();
        }
    }

    private void setStepDetails(Step step){

        System.out.println("swap");

        if(tablet){
            Bundle bundle = new Bundle();
            bundle.putParcelable("step", step);

            StepDetailsFragment fragment = new StepDetailsFragment();
            fragment.setListener(this);
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.details_frame, fragment, "StepDetails");
            transaction.commit();
            list = true;
        }
        else {
            Bundle bundle = new Bundle();
            bundle.putParcelable("step", step);

            StepDetailsFragment fragment = new StepDetailsFragment();
            fragment.setListener(this);
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.step_fragment_frame, fragment, "StepDetails");
            transaction.commit();
            list = true;
        }
    }

    private void showIngredients(){
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("ingredients", recipe.getIngredients());
        list = true;

        if(tablet){
            IngredientListFragment fragment = new IngredientListFragment();
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.details_frame, fragment, "Ingredients");
            transaction.commit();
        }
        else {
            IngredientListFragment fragment = new IngredientListFragment();
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.step_fragment_frame, fragment, "Ingredients");
            transaction.commit();
        }
    }

    @Override
    public void onNavigationRequested(int position) {
        int length = recipe.getSteps().size();

        if(position >= 0 && position < length){
            setStepDetails(recipe.getSteps().get(position));
        }
    }

    @Override
    public void onBackPressed() {

        if(list){
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("steplist", recipe.getSteps());
            StepListFragment stepList = new StepListFragment();
            stepList.setOnSelectListener(this);
            stepList.setArguments(bundle);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.step_fragment_frame, stepList, "stepListFrag");
            transaction.commit();
            list = false;
        }
        else {
            super.onBackPressed();
        }


    }
}
