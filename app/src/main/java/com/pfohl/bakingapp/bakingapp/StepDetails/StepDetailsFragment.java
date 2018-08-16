package com.pfohl.bakingapp.bakingapp.StepDetails;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.pfohl.bakingapp.bakingapp.R;
import com.pfohl.bakingapp.bakingapp.Repo.Model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class StepDetailsFragment extends Fragment {

    @BindView(R.id.video_view) PlayerView playerView;
    @BindView(R.id.step_desc_tv) TextView stepDescription;

    @OnClick(R.id.back_button)
    public void prev(View view) {
        listener.onNavigationRequested(step.getId() - 1);
    }
    @OnClick(R.id.forward_button)
    public void next(View view) {
        listener.onNavigationRequested(step.getId() + 1);
    }

    private static String SELECTED_POSITION = "selected_position";
    private static String PLAYER_STATE = "player_state";
    private SimpleExoPlayer player;
    private Step step;
    private Unbinder unbinder;
    private NavigationListener listener;
    private long position;
    private boolean playing;

    public StepDetailsFragment() {
        // Required empty public constructor
    }

    public void setListener(NavigationListener listener){
        this.listener = listener;
    }

    public void removeListener(){
        this.listener = null;
    }

    public interface NavigationListener{
      void onNavigationRequested(int position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        this.step = getArguments().getParcelable("step");
        this.unbinder = ButterKnife.bind(this, view);
        initializeView();

        if(!step.getVideoURL().isEmpty()){
            initializePlayer(savedInstanceState);
        }
        else {
            playerView.setVisibility(View.GONE);
        }

        return  view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(SELECTED_POSITION, position);
        outState.putBoolean(PLAYER_STATE, playing);

    }

    @Override
    public void onPause() {
        super.onPause();
        if(player!= null) {
            position = player.getCurrentPosition();
            playing = player.getPlayWhenReady();
        }
        unbinder.unbind();
        removeListener();
        releasePlayer();
    }

    public void initializeView(){
        stepDescription.setText(step.getDescription());
    }

    private void initializePlayer(Bundle savedInstanceState) {
        player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()),
                new DefaultTrackSelector(), new DefaultLoadControl());
        playerView.setPlayer(player);
        MediaSource mediaSource = new ExtractorMediaSource.Factory(new
                DefaultHttpDataSourceFactory("bakingApp"))
                .createMediaSource(Uri.parse(step.getVideoURL()));
        player.prepare(mediaSource, false, false);
        playerView.setVisibility(View.VISIBLE);
        if (savedInstanceState == null) {
            player.setPlayWhenReady(true);
        } else {
            if (savedInstanceState.containsKey(SELECTED_POSITION)) {
                player.seekTo(savedInstanceState.getLong(SELECTED_POSITION));
            }

            if(savedInstanceState.containsKey(PLAYER_STATE)){
            player.setPlayWhenReady(savedInstanceState.getBoolean(PLAYER_STATE));
            }
        }

    }




    private void releasePlayer() {
        if (player != null) {
            player.stop(true);
            player.release();
            player = null;
        }
    }
}
