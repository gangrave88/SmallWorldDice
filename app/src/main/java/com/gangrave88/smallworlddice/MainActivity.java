package com.gangrave88.smallworlddice;

import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.rtp.AudioStream;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements Settings.ReturnPropertis{

    private Random random;
    private SoundPool soundPool;

    @BindView(R.id.imageView)
    ImageButton imageView;
    @BindView(R.id.settings)
    ImageButton settings;

    private boolean playSound;
    private boolean playAnim;
    private SharedPreferences preferences;

    private int coin;
    private int noup;

    private int rezult;

    private Animation down;
    private Animation uper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        loadPref();

        random = new Random();

        ButterKnife.bind(this);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        coin = soundPool.load(this,R.raw.coin,1);
        noup = soundPool.load(this,R.raw.noup,1);

        down = AnimationUtils.loadAnimation(this,R.anim.down);
        uper = AnimationUtils.loadAnimation(this,R.anim.uper);

        down.setAnimationListener(animationListenerDown);
    }

    private void sound(){
        if (playSound){
            if (rezult<=3){
                soundPool.play(coin,1,1,0,0,1);
            }
            else {
                soundPool.play(noup,1,1,0,0,1);
            }
        }
    }

    private void loadPref(){
        preferences = getPreferences(MODE_PRIVATE);
        playSound = preferences.getBoolean("play_sound",true);
        playAnim = preferences.getBoolean("play_anim",true);
    }

    @Override
    protected void onDestroy() {
        savePref();
        super.onDestroy();
    }

    private void savePref() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("play_sound",playSound);
        editor.putBoolean("play_anim",playAnim);
        editor.commit();
    }

    @OnClick(R.id.imageView)
    public void clic(){
        if (playAnim){
            imageView.startAnimation(down);
        }
        else{
            newResult();
        }
    }

    @OnClick(R.id.settings)
    public void clicSettings(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Settings df = new Settings();
        Bundle bundle = new Bundle();
        bundle.putBoolean("play_sound",playSound);
        bundle.putBoolean("play_anim",playAnim);
        df.setArguments(bundle);
        df.show(ft,"settings");
    }

    private void newResult(){
        rezult = random.nextInt(6)+1;
        if (rezult == 1 || rezult == 2 || rezult == 3){
            switch (rezult){
                case 1: imageView.setImageDrawable(getResources().getDrawable(R.drawable.d1));
                    break;
                case 2: imageView.setImageDrawable(getResources().getDrawable(R.drawable.d2));
                    break;
                case 3: imageView.setImageDrawable(getResources().getDrawable(R.drawable.d3));
                    break;
            }
        }
        else {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.d0));
        }
        if (playAnim){
            imageView.startAnimation(uper);
        }
        sound();
    }

    Animation.AnimationListener animationListenerDown = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            newResult();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    @Override
    public void returnProp(boolean music, boolean anim) {
        playAnim = anim;
        playSound = music;
    }
}
