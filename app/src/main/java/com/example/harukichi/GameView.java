package com.example.harukichi;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;




public class GameView extends Activity implements SensorEventListener, View.OnClickListener {

    private final static int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final static int MP = ViewGroup.LayoutParams.MATCH_PARENT;
    private final static String TAG_MAIN = "main";
    private final static String TAG_START = "start";

    private FrameLayout layout;

    private ImageView turnoffView;
    private ImageButton startBtn;
    private SensorManager mSensorManager;
    private Sensor mGravitySensor;
    private boolean mFacingDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //フルスクリーンの指定
        getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //フレームレイアウトの生成
        layout = new FrameLayout(this);
        layout.setBackgroundColor(Color.WHITE);
        setContentView(layout);

        //背景(ImageView)
        layout.addView(bkgImgView(R.drawable.game_background));

        //メニューボタンフレーム(LinearLayout)
        layout.addView(makeFrameRight(makeButton(TAG_MAIN, R.drawable.menu_btn)));

        //スタートボタンフレームレイアウト生成
        startBtn = makeButton(TAG_START,R.drawable.push_start);
        layout.addView(makeFrameCenter(startBtn));


        //ターンオフ画面
        turnoffView = new ImageView(this);
        turnoffView.setImageResource(R.drawable.turn_off_background);
        turnoffView.setLayoutParams(new FrameLayout.LayoutParams(MP, MP));
        turnoffView.setVisibility(View.GONE);
        layout.addView(turnoffView);


        //センサーマネージャーの取得
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mGravitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

    }
    public ImageView bkgImgView(int resID){
        ImageView bkgImgView = new ImageView(this);
        bkgImgView.setImageResource(resID);
        return bkgImgView;
    }

    public LinearLayout makeFrameCenter(ImageButton imageButton){
        LinearLayout gameFrame = new LinearLayout(this);
        gameFrame.setBackgroundColor(Color.TRANSPARENT);
        gameFrame.setGravity(Gravity.CENTER);
        gameFrame.addView(imageButton);
        return gameFrame;
    }

    public LinearLayout makeFrameRight(ImageButton imageButton){
        LinearLayout gameFrame = new LinearLayout(this);
        gameFrame.setBackgroundColor(Color.TRANSPARENT);
        gameFrame.setGravity(Gravity.RIGHT|Gravity.BOTTOM);
        gameFrame.addView(imageButton);
        return gameFrame;
    }

    public ImageButton makeButton(String tag, int resID){
        ImageButton imageButton = new ImageButton(this);
        imageButton.setTag(tag);
        imageButton.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(this);
        imageButton.setImageResource(resID);
        imageButton.setBackgroundColor(Color.TRANSPARENT);
        imageButton.setLayoutParams(new FrameLayout.LayoutParams(WC,WC));
        return imageButton;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // nothing
    }
    /**
     * このメソッドはセンサーイベントがGravityの時に発生するメソッドです。
     * スマホの画面が裏返ったかの判断をします。
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        final float down = 0.95F;
        //final float up = 0.55F;
        if (event.sensor == mGravitySensor) {
            boolean nowDown = event.values[2] < -SensorManager.GRAVITY_EARTH * down;
            boolean nowUp = event.values[2] > -SensorManager.GRAVITY_EARTH*down;

            if (nowDown != mFacingDown) {
                if (nowDown) {
                    turnoffView.setVisibility(View.VISIBLE);

                }if(nowUp){
                    Intent intent = new Intent(this, WellDoneView.class);
                    startActivity(intent);
                }
            }
            mFacingDown = nowDown;
        }
    }


    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (TAG_MAIN.equals(tag)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        if (TAG_START.equals(tag)) {
            startBtn.setVisibility(View.GONE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (mGravitySensor != null) {
                        mSensorManager.registerListener(GameView.this, mGravitySensor, SensorManager.SENSOR_DELAY_NORMAL);

                    }
                }
            },10000);
        }
    }

        @Override
        protected void onResume() {
            super.onResume();
            startBtn.setVisibility(View.VISIBLE);


            }


        @Override
        protected void onPause() {
                super.onPause();
                mSensorManager.unregisterListener(this);
        }
}