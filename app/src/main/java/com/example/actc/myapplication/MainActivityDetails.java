package com.example.actc.myapplication;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.actc.myapplication.Adapters.StepsAdapter;
import com.example.actc.myapplication.Model.Recipe;
import com.example.actc.myapplication.Model.Steps;
import com.example.actc.myapplication.ui.ExoPlayerFragment;
import com.example.actc.myapplication.ui.MasterStepsFragment;

import java.io.Serializable;
import java.util.ArrayList;

import timber.log.Timber;

import static com.example.actc.myapplication.VideoActivity.PASED_POSITION;

public class MainActivityDetails extends AppCompatActivity implements Serializable, MasterStepsFragment.onClickStepsTowPanel {
    public static boolean mTwoPane = false;
    ArrayList<Steps> allSteps;
    public static Recipe clickedRecipe;
    public static String LAST_CLICKED_POSITION = "lastClickedPosition";
    int lastClkPos = 0;
    public static String CLICKED_STEPS = "clickedsteps";
    ExoPlayerFragment exoPlayerFragment;
    FragmentManager fragmentManager;
    Steps stepsToShow;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Timber.d("onSaveInstanceState");

        outState.putInt(LAST_CLICKED_POSITION, lastClkPos);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Timber.d("onRestoreInstanceState");

        if (savedInstanceState == null){}

        else {
            System.out.println("not null ");
            lastClkPos = savedInstanceState.getInt(LAST_CLICKED_POSITION);
        }
    }

    ////--------------------------------------------------------------------------------------------

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onResumeVideoActivity");
        if (mTwoPane == true) {
            if (ExoPlayerFragment.isExoPlayerFragmentCreated) {
                replaceFragment(lastClkPos);
            } else {
                exoPlayerFragment = new ExoPlayerFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(ExoPlayerFragment.PLAY_WHEN_READY, ExoPlayerFragment.playWhenReady);
                bundle.putSerializable(CLICKED_STEPS, stepsToShow);
                exoPlayerFragment.setArguments(bundle);
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.exo_container, exoPlayerFragment)
                        .commit();
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_details);
        MasterStepsFragment.setLsn2panel(this);
        if (savedInstanceState != null)
            if (savedInstanceState.getInt(LAST_CLICKED_POSITION) != -1)
                lastClkPos = savedInstanceState.getInt(LAST_CLICKED_POSITION);
        Intent intent = getIntent();
        clickedRecipe = (Recipe) intent.getSerializableExtra(MainActivity.SEND_OBJECT);
        allSteps = clickedRecipe.getStep();
        ExoPlayerFragment.clickedSteps = allSteps.get(lastClkPos);/////defult
        if (findViewById(R.id.linear_sw600) != null) {
            mTwoPane = true;
            MasterStepsFragment.setTowPanel(mTwoPane);


        } else {
            mTwoPane = false;
            MasterStepsFragment.setTowPanel(mTwoPane);
            Timber.d("Mobile mode ");

        }

    }


    @Override
    public void onClickStepsTowPanel(int pos) {
        System.out.println("clickedSteps!"+ExoPlayerFragment.pausedPosition);
        if(ExoPlayerFragment.pausedPosition!=0) {
            ExoPlayerFragment.pausedPosition = 0;
            System.out.println("test1");

        }
        replaceFragment(pos);
        lastClkPos = pos;
        Timber.d("new Item Selected ");

    }



    public void replaceFragment(int pos) {

        String urlVideo = clickedRecipe.getStep().get(pos).getVideoURL();
        ExoPlayerFragment fragment = new ExoPlayerFragment();
        fragment.setVedioUrl(urlVideo);
        Bundle bundle = new Bundle();
        bundle.putBoolean(ExoPlayerFragment.PLAY_WHEN_READY, ExoPlayerFragment.playWhenReady);
        bundle.putLong(ExoPlayerFragment.PAUSED_POSITION,ExoPlayerFragment.pausedPosition);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.exo_container, fragment)
                .commit();
        System.out.println("!!!!replace fragment");

    }


}
