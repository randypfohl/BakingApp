package com.pfohl.bakingapp.bakingapp.Recipe;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pfohl.bakingapp.bakingapp.R;
import com.pfohl.bakingapp.bakingapp.Repo.Model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepListFragment extends Fragment implements StepListAdapter.OnStepClickListener {

    @BindView(R.id.step_rv) RecyclerView stepListRV;

    ArrayList<Step> steps;
    Unbinder unbinder;
    private OnSelectClickListener listener;

    public interface OnSelectClickListener {
        void onItemClick(Step step);
    }

    public StepListFragment() {
        // Required empty public constructor
    }

    public void setOnSelectListener(OnSelectClickListener listener){
        this.listener = listener;
    }

    public void removeOnSelectListener(){
        this.listener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        steps = getArguments().getParcelableArrayList("steplist");

        StepListAdapter adapter = new StepListAdapter(steps, this);
        stepListRV.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getActivity());
        stepListRV.setLayoutManager(manager);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    // observer interfaces
    @Override
    public void onItemClick(Step step) {
        if(listener == null){
            throw new IllegalStateException("No listener was provided but onItemClick was called");
        }
        listener.onItemClick(step);
    }
}
