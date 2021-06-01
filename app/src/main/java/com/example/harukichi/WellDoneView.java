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

public class WellDoneView extends Activity implements View.OnClickListener {
    private final static int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final static String TAG_MAIN = "main";

    /**
     *GameViewクラスから画面遷移してせんべいが焼けた画像を表示するクラスです
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


        //フレームレイアウト・背景の生成
        layout.addView(bkgImgView(R.drawable.done_background));

        layout.addView(makeFrameRight(makeButton(TAG_MAIN,R.drawable.menu_btn)));



    }
    public ImageView bkgImgView(int resID) {
        ImageView bkgImgView = new ImageView(this);
        bkgImgView.setImageResource(resID);
        return bkgImgView;
    }

    public LinearLayout makeFrameRight(ImageButton imageButton) {
        LinearLayout gameFrame = new LinearLayout(this);
        gameFrame.setBackgroundColor(Color.TRANSPARENT);
        gameFrame.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
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
     *
     */
    //ボタンリスナー
    public void onClick(View v){
        String tag = (String) v.getTag();

        if(TAG_MAIN.equals(tag)){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }

}
