package com.example.actc.myapplication;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.actc.myapplication.ui.ExoPlayerFragment;

public class VideoActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    ExoPlayerFragment exoPlayerFragment;
    public static String PASED_POSITION = "pausedposition";
    public static String CLICKED_STEPS = "clickedSteps";
    public static String IS_ExoPlayer_CREATED = "isExoPlayerPrevioslyCreated";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_ExoPlayer_CREATED, ExoPlayerFragment.isExoPlayerFragmentCreated);

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
    }


    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onPauseVideoActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("onResumeVideoActivity");
        if (ExoPlayerFragment.isExoPlayerFragmentCreated) {
            exoPlayerFragment = new ExoPlayerFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(CLICKED_STEPS, ExoPlayerFragment.clickedSteps);
            bundle.putBoolean(ExoPlayerFragment.PLAY_WHEN_READY,ExoPlayerFragment.playWhenReady);
            bundle.putLong(ExoPlayerFragment.PAUSED_POSITION,ExoPlayerFragment.pausedPosition);
            exoPlayerFragment.setArguments(bundle);
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.exo_container, exoPlayerFragment)
                    .commit();

        } else {
            exoPlayerFragment = new ExoPlayerFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(CLICKED_STEPS, ExoPlayerFragment.clickedSteps);
            bundle.putBoolean(ExoPlayerFragment.PLAY_WHEN_READY,ExoPlayerFragment.playWhenReady);
            exoPlayerFragment.setArguments(bundle);
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.exo_container, exoPlayerFragment)
                    .commit();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onStartVideoActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("onStopVideoActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroyVideoActivity");
    }
}
