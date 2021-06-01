package com.example.harukichi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.DisplayMetrics;
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

/**
 * MainActivityクラスは起動画面のクラスでゲーム画面に進むボタンとはるギャラリーに進むボタンを所持しています。
 */

public class MainActivity extends Activity implements View.OnClickListener {
    private final static int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final static int MP = ViewGroup.LayoutParams.MATCH_PARENT;
    private final static String TAG_GAME = "game";
    private final static String TAG_GALLERY ="gallery";
    private int sound1;

    /**
     * 背景フレーム・イメージボタンを作成するメソッド
     */
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);


        //フルスクリーンの指定
        getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //フレームレイアウトの生成
        FrameLayout layout = new FrameLayout(this);
        layout.setLayoutParams(new FrameLayout.LayoutParams(MP,MP));
        setContentView(layout);

        //背景(ImageView)
        layout.addView(bkgImgView(R.drawable.menu_background));

        //ゲームボタンフレーム(LinearLayout)
        layout.addView(makeFrameLeft(makeButton(TAG_GAME,R.drawable.game_btn)));

        //ギャラリーボタんフレーム(LinearLayout)
        layout.addView(makeFrameRight(makeButton(TAG_GALLERY,R.drawable.gallery_btn)));

        //サウンドの再生
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        SoundPool soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .setMaxStreams(1)
                .build();

        //効果音を読み込む(res\raw から取得する)
         sound1 = soundPool.load(this, R.raw.opning1, 1);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sound1, 1.0f, 1.0f, 0, 0, 1);
            }
        });


    }

    public ImageView bkgImgView(int resID){
        ImageView bkgImgView = new ImageView(this);
        bkgImgView.setImageResource(resID);
        return bkgImgView;
    }

    public LinearLayout makeFrameLeft(ImageButton imageButton){
        LinearLayout gameFrame = new LinearLayout(this);
        gameFrame.setBackgroundColor(Color.TRANSPARENT);
        gameFrame.setGravity(Gravity.LEFT|Gravity.BOTTOM);
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

    /**
     * クリックリスナーから画面遷移するメソッド
     */
    //ボタンリスナー
    public void onClick(View v){
        String tag = (String)v.getTag();

        if(TAG_GAME.equals(tag)){
            Intent intent = new Intent(this,TutorialView.class);
            startActivity(intent);
        }
        else if(TAG_GALLERY.equals(tag)){
            Intent intent = new Intent(this,GalleryView.class);
            startActivity(intent);
        }
    }



}
