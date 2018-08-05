package com.pfohl.bakingapp.bakingapp.Recipe;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pfohl.bakingapp.bakingapp.R;
import com.pfohl.bakingapp.bakingapp.Repo.Model.Recipe;
import com.pfohl.bakingapp.bakingapp.Repo.Model.Step;
import com.pfohl.bakingapp.bakingapp.StepDetails.StepDetailsFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeActivity extends AppCompatActivity implements StepListFragment.OnSelectClickListener {

    private Recipe recipe;
    private Unbinder unbinder;

    //@Optional @BindView(R.id.)

    Step step;

    boolean tablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        unbinder = ButterKnife.bind(this);
        Intent intent = getIntent();
        this.recipe = intent.getParcelableExtra(getString(R.string.recipe_intent_tag));


        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("steplist", recipe.getSteps());

        StepListFragment stepList = new StepListFragment();
        stepList.setOnSelectListener(this);
        stepList.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.step_fragment_frame, stepList, "stepListFrag");
        transaction.commit();

        if(tablet){

        }
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

        }
        else {
            StepDetailsFragment fragment = new StepDetailsFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.step_fragment_frame, fragment, "StepDetails");
            transaction.addToBackStack("StepDetails");
            transaction.commit();
        }
    }
}
