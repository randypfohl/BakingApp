package com.pfohl.bakingapp.bakingapp.Recipe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pfohl.bakingapp.bakingapp.R;
import com.pfohl.bakingapp.bakingapp.Repo.Model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.stepViewHolder> {

    private List<Step> stepList;
    private Context context;
    private OnStepClickListener listener;

    public StepListAdapter(List<Step> recipeList, OnStepClickListener listener){
        this.stepList = recipeList;
        this.listener = listener;
    }

    public interface OnStepClickListener {
        void onItemClick(Step step);
    }

    @Override
    public stepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.fragment_step_list_item, parent, false);
        stepViewHolder viewHolder = new stepViewHolder(contactView);
        return viewHolder;
        }

    @Override
    public void onBindViewHolder(stepViewHolder holder, int position) {
        final Step step = stepList.get(position);
        holder.stepDescription.setText(step.getShortDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(step);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    class stepViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.step_list_description_tv) TextView stepDescription;

        public stepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
