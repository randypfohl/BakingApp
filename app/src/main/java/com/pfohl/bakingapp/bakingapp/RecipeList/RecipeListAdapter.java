package com.pfohl.bakingapp.bakingapp.RecipeList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pfohl.bakingapp.bakingapp.R;
import com.pfohl.bakingapp.bakingapp.Recipe.RecipeActivity;
import com.pfohl.bakingapp.bakingapp.Repo.Model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList;
    private Context context;
    public RecipeListAdapter ( List<Recipe> recipeList){
        this.recipeList = recipeList;

    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.activity_main_recipe_listitem, parent, false);
        RecipeViewHolder viewHolder = new RecipeViewHolder(contactView);
        return viewHolder;
        }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        final Recipe recipe = recipeList.get(position);
        holder.recipeName.setText(recipe.getName());
        holder.servingSize.setText(this.context.getString(R.string.Serves_Prefix) + String.valueOf(recipe.getServings()));
        Picasso.get().load(recipe.getImage()).placeholder(R.drawable.exo_controls_play).into(holder.recipeImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecipeActivity.class);
                intent.putExtra(context.getString(R.string.recipe_intent_tag), recipe);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.recipe_name_tv) TextView recipeName;
        @BindView(R.id.recipe_serving_tv) TextView servingSize;
        @BindView(R.id.recipe_iv) ImageView recipeImage;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
