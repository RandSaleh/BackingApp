package com.example.actc.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.actc.myapplication.Adapters.StepsAdapter;
import com.example.actc.myapplication.IngradiantsActivity;
import com.example.actc.myapplication.MainActivity;
import com.example.actc.myapplication.MainActivityDetails;
import com.example.actc.myapplication.Model.Ingredients;
import com.example.actc.myapplication.Model.Recipe;
import com.example.actc.myapplication.Model.Steps;
import com.example.actc.myapplication.R;
import com.example.actc.myapplication.VideoActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class MasterStepsFragment extends Fragment implements StepsAdapter.ListStepsClickListener, Serializable {
    private static boolean twoPanel;
    Recipe temp;
    public static int currentClickedStepsIndex;
    public static String CLICKED_STEPS = "clickedStep";
    public static String ALL_STEPS = "allSteps";
    ArrayList<Ingredients> ingredients;
    static onClickStepsTowPanel lsn2panel;
    ArrayList<Steps> steps;

    public static void setLsn2panel(onClickStepsTowPanel mlsn2panel) {
        lsn2panel = mlsn2panel;
    }

    public interface onClickStepsTowPanel {
        void onClickStepsTowPanel(int pos);
    }
    public MasterStepsFragment() {
    }
    public static void setTowPanel(boolean iftwoPanel) {
        twoPanel = iftwoPanel;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_master_steps, container, false);
        RecyclerView masterStepsRecyclerView = rootView.findViewById(R.id.RecyclerViewSteps);
        Button btnIng = rootView.findViewById(R.id.btn_ing);
        Intent Recieve = getActivity().getIntent();
        if (Recieve.hasExtra(MainActivity.SEND_OBJECT)) {
            temp = (Recipe) Recieve.getSerializableExtra(MainActivity.SEND_OBJECT);
            ingredients = temp.getIngredient();
            steps = temp.getStep();
        }

        if (temp != null) {
            System.out.println("!@#$" + temp.getStep().get(0).getDescription());
            StepsAdapter adapter = new StepsAdapter(temp.getStep().size(), temp.getStep(), this);
            GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
            masterStepsRecyclerView.setLayoutManager(manager);
            masterStepsRecyclerView.setHasFixedSize(true);
            masterStepsRecyclerView.setAdapter(adapter);

        }

        btnIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getActivity(), IngradiantsActivity.class);
                intent.putExtra(IngradiantsActivity.INGREDIANT, ingredients);
                startActivity(intent);

            }
        });

        return rootView;
    }
    @Override
    public void onClickStepsAdapter(int pos) {
        ExoPlayerFragment.pausedPosition=0;

        if (twoPanel == false) {
            currentClickedStepsIndex = pos;
            Steps clickedStep = temp.getStep().get(pos);
            Intent intent = new Intent(getContext(), VideoActivity.class);
            intent.putExtra(CLICKED_STEPS, clickedStep);
            intent.putExtra(ALL_STEPS, temp.getStep());
            startActivity(intent);
        } else {
            lsn2panel.onClickStepsTowPanel(pos);
            ExoPlayerFragment.setAllStepsForRecipie(temp.getStep());
            ExoPlayerFragment.clickedSteps = temp.getStep().get(pos);
        }

    }

}
