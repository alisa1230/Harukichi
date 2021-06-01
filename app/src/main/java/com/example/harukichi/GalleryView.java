package com.example.harukichi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * *！！未完成クラスです！！
 * GalleryViewクラスは、はる君の画像をスクロールして鑑賞するクラスです。
 */

public class GalleryView extends Activity implements View.OnClickListener {
    private final static String TAG_MAIN = "main";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //フルスクリーンの指定
        getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //フレームレイアウトの生成
        FrameLayout layout = new FrameLayout(this);
        layout.setBackgroundColor(Color.WHITE);
        setContentView(layout);
    }


    /**
     * MainActivityクラスに遷移するメソッド
     */
    //ボタンリスナー
    public void onClick(View v) {
        String tag = (String) v.getTag();

        if (TAG_MAIN.equals(tag)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
