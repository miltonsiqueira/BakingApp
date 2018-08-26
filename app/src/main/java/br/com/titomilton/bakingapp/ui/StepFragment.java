package br.com.titomilton.bakingapp.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import br.com.titomilton.bakingapp.R;
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
    Unbinder unbinder;

    private SimpleExoPlayer player;
    private MainViewModel mainViewModel;
    private String videoUrl;
    private boolean playWhenReady;
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

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        videoUrl = mainViewModel.getStep().getVideoURL();
        tvStepDescription.setText(mainViewModel.getStep().getDescription());
        initializePlayer();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void initializePlayer() {
        Log.d(LOG, "open url " + videoUrl);

        Context context = getContext();

        if (player == null &&
                NetworkUtils.isConnected(context) && NetworkUtils.isValidUrl(context, videoUrl)) {

            TrackSelection.Factory adaptiveTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);

            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(context),
                    new DefaultTrackSelector(adaptiveTrackSelectionFactory),
                    new DefaultLoadControl());

            playerView.setPlayer(player);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);

            Uri uri = Uri.parse(videoUrl);
            MediaSource mediaSource = buildMediaSource(context, uri);
            player.prepare(mediaSource, true, false);

        }
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
            playWhenReady = player.getPlayWhenReady();
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

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
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
}
