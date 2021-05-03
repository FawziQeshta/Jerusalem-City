package com.iug.jerusalem_city.ui.play_video;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.iug.jerusalem_city.R;
import com.iug.jerusalem_city.databinding.ActivityPlayVideoBinding;
import com.iug.jerusalem_city.utils.Utilities;

public class PlayVideoActivity extends AppCompatActivity {

    private ActivityPlayVideoBinding binding;

    private SimpleExoPlayer simpleExoPlayer;

    private ImageView bt_fullscreen;
    private LinearLayout containerBake;

    private boolean flag = false;

    private static final String TAG = "PlayVideoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        binding = ActivityPlayVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bt_fullscreen = binding.playerView.findViewById(R.id.bt_fullscreen);
        containerBake = binding.playerView.findViewById(R.id.containerBake);

        contentScreen();

        bt_fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    //when flag is true
                    // set enter full screen image
                    bt_fullscreen.setImageResource(R.drawable.ic_fullscreen);
                    // set portrait orientation
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                    //set flag value
                    flag = false;
                } else {
                    //when flag is false
                    // set exit full screen image
                    bt_fullscreen.setImageResource(R.drawable.ic_fullscreen_exit);
                    // set portrait orientation
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                    //set flag value
                    flag = true;
                }
            }
        });

        containerBake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnReloadPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentScreen();
            }
        });

    }

    private void contentScreen() {
        if (Utilities.checkInternetConnected(this)) {
            binding.noWifiContainer.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();

            StorageReference pathReference = storageRef.child(getIntent().getStringExtra("videoUrl"));

            pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
//                Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/jerusalem-city-5c6b1.appspot.com/o/videos%2F%D8%AA%D8%B9%D8%B1%D9%81%20%D8%B9%D9%84%D9%89%20%D9%85%D8%AF%D9%8A%D9%86%D8%A9%20%D8%A7%D9%84%D9%82%D8%AF%D8%B3%20%D9%83%D8%A3%D9%86%D9%83%20%D9%81%D9%8A%D9%87%D8%A7.mp4?alt=media&token=13aa2717-cbef-44ce-b093-9112c2ef9fe5");
                    playVideo(uri);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    Log.e(TAG, "onFailure: " + exception.getMessage());
                }
            });

        } else {
            binding.progressBar.setVisibility(View.GONE);
            binding.noWifiContainer.setVisibility(View.VISIBLE);
        }
    }

    private void playVideo(Uri uri) {
        //Initialize load control
        LoadControl loadControl = new DefaultLoadControl();

        //Initialize band width meter
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        //Initialize track selector
        TrackSelector trackSelection = new DefaultTrackSelector(
                new AdaptiveTrackSelection.Factory(bandwidthMeter)
        );

        //Initialize simple exo player
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                PlayVideoActivity.this, trackSelection, loadControl
        );

        //Initialize data source factory
        DefaultHttpDataSourceFactory factory = new DefaultHttpDataSourceFactory(
                "exoplayer_video"
        );

        //Initialize extractors factory
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        //Initialize media source
        MediaSource mediaSource = new ExtractorMediaSource(uri, factory,
                extractorsFactory, null, null);

        //Set Player
        binding.playerView.setPlayer(simpleExoPlayer);

        //keep screen on
        binding.playerView.setKeepScreenOn(true);

        //Prepare media
        simpleExoPlayer.prepare(mediaSource);
        //Play video when ready
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.getPlaybackState();

        simpleExoPlayer.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (!Utilities.checkInternetConnected(PlayVideoActivity.this)) {
                    Toast.makeText(PlayVideoActivity.this, "الرجاء فحص الاتصال بالانترنت", Toast.LENGTH_LONG).show();
                }
                //check condition
                if (playbackState == Player.STATE_BUFFERING) {
                    // when buffering
                    //show progress bar
                    binding.progressBar.setVisibility(View.VISIBLE);

                } else if (playbackState == Player.STATE_READY) {
                    //when ready
                    //Hide progress bar
                    binding.progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (flag) {
            bt_fullscreen.setImageResource(R.drawable.ic_fullscreen);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
            flag = false;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (simpleExoPlayer != null) {
            // stop video when ready
            simpleExoPlayer.setPlayWhenReady(false);
            // get playback state
            simpleExoPlayer.getPlaybackState();
        }
    }

    @Override
    protected void onRestart() {
        if (simpleExoPlayer != null) {
            // start video when ready
            simpleExoPlayer.setPlayWhenReady(true);
            // get playback state
            simpleExoPlayer.getPlaybackState();
        }
        super.onRestart();
    }

}