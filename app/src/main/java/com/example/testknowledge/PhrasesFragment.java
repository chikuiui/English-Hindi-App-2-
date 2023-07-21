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


public class PhrasesFragment extends Fragment {
    public PhrasesFragment(){}

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

        final ArrayList<Word> phrases = new ArrayList<>();
        phrases.add(new Word("Where are you going?", "आप कहां जा रहे हैं?", R.raw.phrase_where_are_you_going));
        phrases.add(new Word("What is your name?", "आपका क्या नाम है?", R.raw.phrase_what_is_your_name));
        phrases.add(new Word("My name is", "मेरा नाम है", R.raw.phrase_my_name_is));
        phrases.add(new Word("How are you feeling?", "तुम कैसा महसूस कर रहे हो?", R.raw.phrase_how_are_you_feeling));
        phrases.add(new Word("I’m feeling good.", "मैं अच्छा महसूस कर रहा हूँ।", R.raw.phrase_im_feeling_good));
        phrases.add(new Word("Are you coming?", "क्या आप आ रहे हैं?", R.raw.phrase_are_you_coming));
        phrases.add(new Word("Yes, I’m coming.", "हाँ, आ रहा हूं।", R.raw.phrase_yes_im_coming));
        phrases.add(new Word("I’m coming.", "मेँ आ रहा हूँ।", R.raw.phrase_im_coming));
        phrases.add(new Word("Let’s go.", "चल दर।", R.raw.phrase_lets_go));
        phrases.add(new Word("Come here.", "यहाँ आओ।", R.raw.phrase_come_here));

        ListView view = rootView.findViewById(R.id.word_list);
        WordAdapter items = new WordAdapter(getActivity(),phrases,R.color.category_phrases);
        view.setAdapter(items);

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word current_word = phrases.get(i);
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