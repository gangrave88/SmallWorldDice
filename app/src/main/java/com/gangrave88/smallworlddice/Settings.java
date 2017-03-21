package com.gangrave88.smallworlddice;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Settings extends DialogFragment {

    @BindView(R.id.switch_play_anim)Switch playAnim;
    @BindView(R.id.switch_play_music)Switch playMusic;

    public interface ReturnPropertis{
        public void returnProp(boolean music,boolean anim);
    }

    public Settings() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        ButterKnife.bind(this,view);
        Bundle bundle = getArguments();
        playMusic.setChecked(bundle.getBoolean("play_sound"));
        playAnim.setChecked(bundle.getBoolean("play_anim"));
        return view;
    }


    @OnClick(R.id.button_save)
    public void clicS(){
        ReturnPropertis returnPropertis = (ReturnPropertis)getActivity();
        returnPropertis.returnProp(playMusic.isChecked(),playAnim.isChecked());
        dismiss();
    }
}
