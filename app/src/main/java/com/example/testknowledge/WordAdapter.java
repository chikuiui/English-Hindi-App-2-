package com.example.testknowledge;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;


public class WordAdapter extends ArrayAdapter<Word> {
    private int colorId;

    public WordAdapter(Activity context, ArrayList<Word> words, int colorId){
        super(context,0,words);
        this.colorId = colorId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Word current_word = getItem(position);

        View ListItemView = convertView;

        if(ListItemView == null){
            ListItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        TextView hindi_name =  ListItemView.findViewById(R.id.hindi_text_view);
        TextView english_name = ListItemView.findViewById(R.id.english_text_view);

        hindi_name.setText(current_word.getHindi_word());
        english_name.setText(current_word.getEnglish_word());

        ImageView image = ListItemView.findViewById(R.id.imagee);

        if(current_word.hasImage()){
            image.setImageResource(current_word.getImage_resource_id());
            image.setVisibility(View.VISIBLE);
        }else{
            image.setVisibility(View.GONE);
        }
        // this is linear layout
        View textContainer = ListItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(),colorId);
        textContainer.setBackgroundColor(color);

        return ListItemView;
    }






}
