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
import android.widget.Toast;

import java.util.ArrayList;


public class NumbersFragment extends Fragment {
    public NumbersFragment() {}


    private MediaPlayer mp;
    AudioManager Audiom;



    // we are creating media-player instance once bcoz there are multiple media files and we don't want to create new instance for
    // each media file.
    MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            Toast.makeText(getActivity(),"Its done !",Toast.LENGTH_SHORT).show();
            releaseMediaPlayer();
        }
    };

    // we are creating audio manager instance once so that we can control audio files,like where to play,if sudden message comes
    // then what should we do .. etc all things will be handled by AudioManager.
    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if(i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || i==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                // AUDIOFOCUS_LOSS_TRANSIENT -> lost audio focus for short amount of time.
                // AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> continue to play sound but at lower volume.
                // both these case can happen due to sudden interruption of other application while playing audio
                // in this case we have to stop media file.
                mp.pause();
                mp.seekTo(0); // start from beginning.
            }else if(i == AudioManager.AUDIOFOCUS_GAIN){
                // AUDIOFOCUS_GAIN -> means requesting audio focus.
                mp.start();
            }else if(i == AudioManager.AUDIOFOCUS_LOSS){
                // AUDIOFOCUS_LOSS -> loss of audio focus.
                // Then we have to stop playback
                releaseMediaPlayer();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        // create and setup link to request audio focus.
        Audiom = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> numbers = new ArrayList<>();
        numbers.add(new Word("one", "एक",R.raw.number_one ,R.drawable.number_one));
        numbers.add(new Word("two", "दो", R.raw.number_two,R.drawable.number_two));
        numbers.add(new Word("three", "तीन",R.raw.number_three ,R.drawable.number_three));
        numbers.add(new Word("four", "चार", R.raw.number_four, R.drawable.number_four));
        numbers.add(new Word("five", "पाँच", R.raw.number_five, R.drawable.number_five));
        numbers.add(new Word("six", "छह", R.raw.number_six, R.drawable.number_six));
        numbers.add(new Word("seven", "सात", R.raw.number_seven, R.drawable.number_seven));
        numbers.add(new Word("eight", "आठ", R.raw.number_eight, R.drawable.number_eight));
        numbers.add(new Word("nine", "नौ", R.raw.number_nine, R.drawable.number_nine));
        numbers.add(new Word("ten", "दस", R.raw.number_ten, R.drawable.number_ten));

        ListView view = rootView.findViewById(R.id.word_list);
        WordAdapter items = new WordAdapter(getActivity(),numbers,R.color.category_numbers);
        view.setAdapter(items);

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word current_word = numbers.get(i);
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