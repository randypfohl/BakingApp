package com.pfohl.bakingapp.bakingapp.Recipe;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.pfohl.bakingapp.bakingapp.R;
import com.pfohl.bakingapp.bakingapp.Repo.Model.Recipe;
import com.pfohl.bakingapp.bakingapp.Repo.Model.Step;
import com.pfohl.bakingapp.bakingapp.StepDetails.StepDetailsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;
import butterknife.Unbinder;

public class RecipeActivity extends AppCompatActivity implements StepListFragment.OnSelectClickListener, StepDetailsFragment.NavigationListener {

    private Recipe recipe;
    private Unbinder unbinder;
    private int position;

    boolean tablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        unbinder = ButterKnife.bind(this);
        Intent intent = getIntent();
        this.recipe = intent.getParcelableExtra(getString(R.string.recipe_intent_tag));

        tablet = findViewById(R.id.details_frame) != null;

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("steplist", recipe.getSteps());
        StepListFragment stepList = new StepListFragment();
        stepList.setOnSelectListener(this);
        stepList.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.step_fragment_frame, stepList, "stepListFrag");
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @Override
    public void onItemClick(Step step) {
    setStepDetails(step);
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
            transaction.addToBackStack("StepDetails");
            transaction.commit();
        }
        else {
            Bundle bundle = new Bundle();
            bundle.putParcelable("step", step);

            StepDetailsFragment fragment = new StepDetailsFragment();
            fragment.setListener(this);
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.step_fragment_frame, fragment, "StepDetails");
            transaction.addToBackStack("StepDetails");
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


}
