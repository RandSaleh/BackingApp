package com.example.actc.myapplication.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.actc.myapplication.R;
import com.example.actc.myapplication.Model.*;

import java.io.Serializable;
import java.util.ArrayList;

public class IngradiantsAdapter extends RecyclerView.Adapter<IngradiantsAdapter.ViewHolderIngradiant> {
    private int number;
    ArrayList<Ingredients> RecipeIng;

    public IngradiantsAdapter(int number, ArrayList<Ingredients> RecipeIng) {
        this.RecipeIng = RecipeIng;
        this.number = number;
    }

    @NonNull
    @Override
    public ViewHolderIngradiant onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.ingradiantlistitem;
        boolean shouldAttackToparent = false;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttackToparent);

        ViewHolderIngradiant viewHolderRecipes = new ViewHolderIngradiant(view);
        return viewHolderRecipes;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderIngradiant viewHolderRecipes, int i) {
        viewHolderRecipes.bind(RecipeIng.get(i));
    }

    @Override
    public int getItemCount() {
        return number;
    }

    class ViewHolderIngradiant extends RecyclerView.ViewHolder implements Serializable {

        TextView listitemNumber;


        public ViewHolderIngradiant(View itemView) {
            super(itemView);

            listitemNumber = (TextView) itemView.findViewById(R.id.tv_ingradiant);

        }

        void bind(Ingredients ingredient) {
            listitemNumber.setText(ingredient.getIngredient() + " : " + ingredient.getQuantity() + " of " + ingredient.getMeasure());

        }
    }
}
