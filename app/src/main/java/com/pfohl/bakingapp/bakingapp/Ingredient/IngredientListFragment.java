package com.pfohl.bakingapp.bakingapp.Ingredient;


import android.app.Fragment;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pfohl.bakingapp.bakingapp.R;
import com.pfohl.bakingapp.bakingapp.Recipe.StepListAdapter;
import com.pfohl.bakingapp.bakingapp.Repo.Model.Ingredient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientListFragment extends Fragment {

    @BindView(R.id.ingredient_rv) RecyclerView ingredient_rv;

    private ArrayList<Ingredient> ingredients;

    private Unbinder unbinder;

    public IngredientListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredient__list, container, false);
       unbinder = ButterKnife.bind(this, view);

        this.ingredients = getArguments().getParcelableArrayList("ingredients");

        IngredientAdapter adapter = new IngredientAdapter(ingredients, getActivity());
        ingredient_rv.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getActivity());
        ingredient_rv.setLayoutManager(manager);

        return  view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
