package br.com.titomilton.bakingapp.ui.step;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import br.com.titomilton.bakingapp.R;
import br.com.titomilton.bakingapp.entity.Step;
import br.com.titomilton.bakingapp.ui.MainViewModel;
import br.com.titomilton.bakingapp.utils.NetworkUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StepFragment extends Fragment {

    private static final String LOG = StepFragment.class.getSimpleName();
    private static final DefaultBandwidthMeter BANDWIDTH_METER =
            new DefaultBandwidthMeter();

    @BindView(R.id.player_view)
    PlayerView playerView;

    @BindView(R.id.step_description)
    TextView tvStepDescription;

    @BindView(R.id.previous_button)
    Button btnPrevious;

    @BindView(R.id.next_button)
    Button btnNext;

    Unbinder unbinder;

    private SimpleExoPlayer player;
    private MainViewModel mainViewModel;
    private Step step;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    public StepFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step, container, false);
        unbinder = ButterKnife.bind(this, view);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToStep(1);
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToStep(-1);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        setStep(mainViewModel.getStep());
    }

    private void setStepAndVideo(Step step) {
        setStep(step);
        releasePlayer();
        initializePlayer();
    }

    private void setStep(Step step) {
        this.step = step;
        mainViewModel.setStep(this.step);
        tvStepDescription.setText(this.step.getDescription());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void initializePlayer() {
        Log.d(LOG, "open url " + step.getVideoURL());

        Context context = getContext();
        boolean canShowVideo = NetworkUtils.isConnected(context) && NetworkUtils.isValidUrl(context, step.getVideoURL());
        if (player == null && canShowVideo) {

            TrackSelection.Factory adaptiveTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);

            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(context),
                    new DefaultTrackSelector(adaptiveTrackSelectionFactory),
                    new DefaultLoadControl());

            playerView.setPlayer(player);
            player.setPlayWhenReady(true);

            player.seekTo(currentWindow, playbackPosition);

            Uri uri = Uri.parse(step.getVideoURL());
            MediaSource mediaSource = buildMediaSource(context, uri);
            player.prepare(mediaSource, true, false);

        }


        playerView.setVisibility(canShowVideo ? View.VISIBLE : View.GONE);
        playerView.setControllerAutoShow(false);

    }

    private MediaSource buildMediaSource(Context context, Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(context.getPackageName())).
                createMediaSource(uri);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {


    }

    private void showSystemUi() {

    }

    @Override
    public void onResume() {
        super.onResume();

        setSystemUI();

        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    private void setSystemUI() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        } else {
            playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
        initializePlayer();
    }

    @Override
    public void onPause() {
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

    private void goToStep(int increment) {
        List<Step> steps = mainViewModel.getRecipe().getSteps();
        int i = steps.indexOf(mainViewModel.getStep());
        i = i + increment;
        if (i >= 0 && i < steps.size()) {
            setStepAndVideo(steps.get(i));
        }
    }

}
