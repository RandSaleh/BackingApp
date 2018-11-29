package com.example.actc.myapplication.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.actc.myapplication.MainActivityDetails;
import com.example.actc.myapplication.Model.Steps;
import com.example.actc.myapplication.R;
import com.example.actc.myapplication.ui.ExoPlayerFragment;


import java.io.Serializable;
import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolderSteps> implements Serializable {

    int number;
    ArrayList<Steps> Steps;


    final private ListStepsClickListener mOnClickListener;


    public interface ListStepsClickListener {
        void onClickStepsAdapter(int pos);
    }


    public StepsAdapter(int number, ArrayList<Steps> Steps, ListStepsClickListener mOnClickListener) {
        this.number = number;
        this.Steps = Steps;
        this.mOnClickListener = mOnClickListener;
    }


    @NonNull
    @Override
    public ViewHolderSteps onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.listitemsteps;
        boolean shouldAttackToparent = false;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttackToparent);

        StepsAdapter.ViewHolderSteps viewHolderRecipes = new StepsAdapter.ViewHolderSteps(view);
        return viewHolderRecipes;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSteps viewHolderSteps, int i) {
        viewHolderSteps.bind(Steps.get(i));
    }

    @Override
    public int getItemCount() {
        return number;
    }
    class ViewHolderSteps extends RecyclerView.ViewHolder implements Serializable, View.OnClickListener {

        TextView listitemNumber;

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            mOnClickListener.onClickStepsAdapter(position);
        }

        public ViewHolderSteps(View itemView) {
            super(itemView);

            listitemNumber = (TextView) itemView.findViewById(R.id.tv_ingradiant);
            itemView.setOnClickListener(this);

        }

        void bind(Steps steps) {
            listitemNumber.setText(steps.getShortDescription());
        }
    }
}
