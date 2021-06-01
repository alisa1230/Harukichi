package com.example.harukichi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
 * MainActivityクラスから遷移し、操作方法を表示しているクラス
 */
public class TutorialView extends Activity implements ViewGroup.OnClickListener {
    private final static int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final static String TAG_GAME = "game";
    private final static String TAG_MAIN = "main";
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
        layout.setBackgroundColor(Color.WHITE);
        setContentView(layout);

        //背景(ImageView)
        layout.addView(bkgImgView(R.drawable.tutorial_background));

        //ゲームボタンフレーム(LinearLayout)
        layout.addView(makeFrameLeft(makeButton(TAG_GAME, R.drawable.game_btn)));

        //メニューボタンフレーム(LinearLayout)
        layout.addView(makeFrameRight(makeButton(TAG_MAIN, R.drawable.menu_btn)));

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
     * クリックリスナーからインテントを持ってGameクラスかMainActivityクラスに遷移するメソッド
     */
    //ボタンリスナー
    public void onClick(View v){
        String tag = (String) v.getTag();
        if(TAG_GAME.equals(tag)){
            Intent intent = new Intent(this,GameView.class);
            startActivity(intent);
        }
        if(TAG_MAIN.equals(tag)){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }

}
