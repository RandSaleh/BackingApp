package com.example.actc.myapplication.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.actc.myapplication.IngradiantsActivity;
import com.example.actc.myapplication.MainActivity;
import com.example.actc.myapplication.MainActivityDetails;
import com.example.actc.myapplication.Model.Recipe;
import com.example.actc.myapplication.Model.Steps;
import com.example.actc.myapplication.R;
import com.example.actc.myapplication.VideoActivity;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.Serializable;
import java.util.ArrayList;

public class ExoPlayerFragment extends Fragment implements Serializable {
    private SimpleExoPlayerView mExoPlayerView;
    public static String PLAY_WHEN_READY = "playwhenready";
    private ExoPlayer mExoPlayer;
    FrameLayout frameExo;
    TextView stepDescription;
    Button nextStep;
    Button prevStep;
    Button btnHome;
    public static boolean playWhenReady = false;
    //---
    static long position = -1;
    static int clickedPosition = -1;
    public static boolean isExoPlayerFragmentCreated = false;
    static boolean isOnBackPressed = false;
    static boolean isOnNavigationUpPressed = false;
    public static long pausedPosition = 0;

    //--
    //Button btnSteps;
    public static String CLICKED_STEPS = "clickedStep";
    public static String ALL_STEPS = "allSteps";
    static ArrayList<Steps> allStepsForRecipie;
    String vedioUrl;
    public static Steps clickedSteps;
    int currentPosition;
    int nextPosition;
    int prevPosition;
    String userAgent;
    Bundle pausedPosition_Bundle;
    TextView descriptionLandScape;
    public static String PAUSED_POSITION = "pausedposition";

    public static void setAllStepsForRecipie(ArrayList<Steps> mallStepsForRecipie) {
        allStepsForRecipie = mallStepsForRecipie;
    }

    public void setVedioUrl(String vedioUrl) {
        this.vedioUrl = vedioUrl;
    }

    public ExoPlayerFragment() {
    }


    private void initializePlayer(Uri mediaUri, long position) {
        if (mExoPlayer == null) {
            System.out.println("initializing ");
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            userAgent = Util.getUserAgent(getContext(), "SessionActivity");
            MediaSource mediaSource = new ExtractorMediaSource(
                    mediaUri,
                    new DefaultDataSourceFactory(getContext(), userAgent),
                    new DefaultExtractorsFactory(), null, null);
            mExoPlayerView.setPlayer((SimpleExoPlayer) mExoPlayer);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.seekTo(position);
            mExoPlayer.setPlayWhenReady(playWhenReady);
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_exo_player, container, false);

        mExoPlayerView = rootView.findViewById(R.id.exoPlayer);
        System.out.println("onCreate view ExoPlayer Fragment");
        stepDescription = rootView.findViewById(R.id.descriptionStep);
        nextStep = rootView.findViewById(R.id.btnNext);
        prevStep = rootView.findViewById(R.id.btnPrev);
        frameExo = rootView.findViewById(R.id.frame_exo);

        if (isLandscape())
            descriptionLandScape = rootView.findViewById(R.id.description);

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(CLICKED_STEPS)) {
                clickedSteps = (Steps) intent.getSerializableExtra(CLICKED_STEPS);

                vedioUrl = clickedSteps.getVideoURL();
                if (intent.hasExtra(ALL_STEPS))
                    allStepsForRecipie = (ArrayList<Steps>) intent.getSerializableExtra("allSteps");
                if (!isLandscape()) {
                    stepDescription.setText(clickedSteps.getDescription());
                }


            }
        }

        if (!isLandscape()) {
            btnHome = rootView.findViewById(R.id.btn_home);
            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);

                }
            });

        }
        //---


        //--


        if (savedInstanceState != null)
            if (getArguments() != null) {
                playWhenReady = getArguments().getBoolean(PLAY_WHEN_READY);

            }

        if (!isLandscape() && MainActivityDetails.mTwoPane == false) {

            nextStep = rootView.findViewById(R.id.btnNext);
            prevStep = rootView.findViewById(R.id.btnPrev);
            prevStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//0 --- 13
                    mExoPlayer.seekTo(0);

                    currentPosition = MasterStepsFragment.currentClickedStepsIndex;


                    if (currentPosition == 0)
                        prevPosition = 0;
                    else
                        prevPosition = currentPosition - 1;


                    Intent intent = new Intent(getActivity(), VideoActivity.class);
                    intent.putExtra(ALL_STEPS, allStepsForRecipie);
                    intent.putExtra(CLICKED_STEPS, allStepsForRecipie.get(prevPosition));
                    startActivity(intent);

                    MasterStepsFragment.currentClickedStepsIndex = prevPosition;


                }


            });

            nextStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mExoPlayer.seekTo(0);
                    currentPosition = MasterStepsFragment.currentClickedStepsIndex;
                    if (currentPosition == allStepsForRecipie.size() - 1)
                        nextPosition = 0;

                    else
                        nextPosition = currentPosition + 1;

                    Intent intent = new Intent(getActivity(), VideoActivity.class);
                    intent.putExtra(ALL_STEPS, allStepsForRecipie);
                    intent.putExtra(CLICKED_STEPS, allStepsForRecipie.get(nextPosition));
                    startActivity(intent);
                    MasterStepsFragment.currentClickedStepsIndex = nextPosition;

                }
            });
        }

        if (MainActivityDetails.mTwoPane == true) {

            if (getArguments() != null)
                pausedPosition = getArguments().getLong(PAUSED_POSITION);
            if (!isLandscape()) {
                nextStep.setVisibility(View.GONE);
                prevStep.setVisibility(View.GONE);
            }
            if (clickedSteps != null)
                if (stepDescription != null)
                    stepDescription.setText(clickedSteps.getDescription());


        }


        if (vedioUrl != null) {
            if (vedioUrl.isEmpty()) {
                mExoPlayerView.setVisibility(View.GONE);
                if (descriptionLandScape != null) {
                    descriptionLandScape.setVisibility(View.VISIBLE);
                    descriptionLandScape.setText(clickedSteps.getDescription());
                }
            } else {
                mExoPlayerView.setPlayer((SimpleExoPlayer) mExoPlayer);
                initializePlayer(Uri.parse(vedioUrl), pausedPosition);
            }
        } else {

            mExoPlayerView.setVisibility(View.GONE);
        }

        isExoPlayerFragmentCreated = true;

        return rootView;
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
        }
        mExoPlayer = null;
    }

    @Override
    public void onPause() {
        System.out.println("onPause");

        if (mExoPlayer != null) {
            playWhenReady = mExoPlayer.getPlayWhenReady();
            pausedPosition = mExoPlayer.getCurrentPosition();
            System.out.println("###onPause" + pausedPosition);
            pausedPosition_Bundle = new Bundle();
            pausedPosition_Bundle.putLong(PAUSED_POSITION, pausedPosition);
        }
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();

        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }

    }

    private boolean isLandscape() {
        int orientation = getActivity().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE)
            return true;
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("onSaveInstanceStateFragment");
        System.out.println("###onSaveInstanceState" + pausedPosition);
        outState.putBoolean(PLAY_WHEN_READY, playWhenReady);
        outState.putLong(PAUSED_POSITION, pausedPosition);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("onActivityCreated");
        if (savedInstanceState != null)
            pausedPosition = savedInstanceState.getLong(PAUSED_POSITION);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mExoPlayer != null)
            mExoPlayer.setPlayWhenReady(playWhenReady);
        System.out.println("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("onResume");
        if (vedioUrl != null)
            initializePlayer(Uri.parse(vedioUrl), pausedPosition);
    }

}
