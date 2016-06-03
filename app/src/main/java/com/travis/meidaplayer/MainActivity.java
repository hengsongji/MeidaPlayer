package com.travis.meidaplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private MediaPlayer mMediaPlayer;

    private String DEFAULT_PATH = "http://7xnujb.com2.z0.glb.qiniucdn.com/ytcx01/001.mp4?e=2997426444&token=jwxfQuqBTiy39mEfpU1ahrq4jh043M2hUrrG1Zlf:RHAWkVIEyvA92SFqkmdDHuFKNsI=";

    private static final int STATE_IDLE               = 0;
    private static final int STATE_PREPARING          = 1;
    private static final int STATE_PLAYING            = 2;
    private static final int STATE_PAUSED             = 3;


    private int mCurrentState = STATE_IDLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        //为SurfaceView绑定监听事件
        mSurfaceView.getHolder().addCallback(this);


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG,"surfaceCreated()");
        mSurfaceHolder = holder;
        play(DEFAULT_PATH);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG,"surfaceChanged()");


    }

    //一旦SurfaceView不可见就会调用此方法
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mSurfaceHolder = null;


        Log.d(TAG,"surfaceDestroyed()");


    }

    private void play(String path) {
        try {
            if (mSurfaceHolder != null) {
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setDisplay(mSurfaceHolder);
                mMediaPlayer.setDataSource(path);
                mMediaPlayer.prepareAsync();
                mMediaPlayer.setOnPreparedListener(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        mMediaPlayer.start();
    }

    @Override
    protected void onPause() {
        if(mMediaPlayer !=null && mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /*
     * release the media player in any state
     */
    private void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
            mCurrentState = STATE_IDLE;
        }
    }
}
