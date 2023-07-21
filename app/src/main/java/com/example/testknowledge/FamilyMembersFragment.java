package com.example.testknowledge;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class FamilyMembersFragment extends Fragment {
    public FamilyMembersFragment(){}

    private MediaPlayer mp;
    AudioManager Audiom;

    MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if(i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mp.pause();
                mp.seekTo(0);
            }else if(i == AudioManager.AUDIOFOCUS_GAIN){
                mp.start();
            }else if(i == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        Audiom = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> family = new ArrayList<>();

        family.add(new Word("father", "पिता", R.raw.family_father, R.drawable.family_father));
        family.add(new Word("mother", "मां", R.raw.family_mother, R.drawable.family_mother));
        family.add(new Word("son", "बेटा", R.raw.family_son, R.drawable.family_son));
        family.add(new Word("daughter", "बेटी", R.raw.family_daughter, R.drawable.family_daughter));
        family.add(new Word("older brother", "बड़े भाई", R.raw.family_older_brother, R.drawable.family_older_brother));
        family.add(new Word("younger brother", "छोटा भाई", R.raw.family_younger_brother, R.drawable.family_younger_brother));
        family.add(new Word("older sister", "बड़ी बहन", R.raw.family_older_sister, R.drawable.family_older_sister));
        family.add(new Word("younger sister", "छोटी बहन", R.raw.family_younger_sister, R.drawable.family_younger_sister));
        family.add(new Word("grandmother", "दादी मा", R.raw.family_grandmother, R.drawable.family_grandmother));
        family.add(new Word("grandfather", "दादा", R.raw.family_grandfather, R.drawable.family_grandfather));

        ListView view = rootView.findViewById(R.id.word_list);
        WordAdapter items = new WordAdapter(getActivity(),family,R.color.category_family);
        view.setAdapter(items);

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word current_word = family.get(i);
                // before playing current sound we check if the previous sound is playing then stop. using this below function.
                releaseMediaPlayer();

                int result = Audiom.requestAudioFocus(afChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    // now we have audio focus now
                    mp = MediaPlayer.create(getActivity(),current_word.getAudio_resource_id());
                    mp.start();
                    mp.setOnCompletionListener(completionListener);
                }
            }
        });



        return rootView;
    }


    private void releaseMediaPlayer(){
        if(mp != null){
            mp.release();
            mp = null;
            Audiom.abandonAudioFocus(afChangeListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}