package com.pfohl.bakingapp.bakingapp.Recipe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pfohl.bakingapp.bakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.stepViewHolder> {

    private List<String> names;
    private Context context;
    private OnStepClickListener listener;

    public StepListAdapter(List<String> names, OnStepClickListener listener){
        this.names = names;
        this.listener = listener;
    }

    public interface OnStepClickListener {
        void onItemClick(int position);
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
    public void onBindViewHolder(stepViewHolder holder, final int position) {
        holder.stepDescription.setText(names.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position -1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class stepViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.step_list_description_tv) TextView stepDescription;

        public stepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
