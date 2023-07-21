package com.example.testknowledge;

public class Word {
    private String hindi_word;
    private String english_word;
    private static final int NO_PROVIDE = -1;

    private int image_resource_id = NO_PROVIDE;
    private int audio_resource_id;

    // Constructor for Phrases Activity
    public Word(String hindi_word,String english_word,int audio_resource_id){
        this.hindi_word = hindi_word;
        this.english_word = english_word;
        this.audio_resource_id = audio_resource_id;
    }


    // Constructor for Numbers Activity,FamilyMembers Activity,Colors Activity
    public Word(String hindi_word,String english_word,int audio_resource_id,int image_resource_id){
        this.hindi_word = hindi_word;
        this.english_word = english_word;
        this.audio_resource_id = audio_resource_id;
        this.image_resource_id = image_resource_id;
    }


    public String getHindi_word() {return hindi_word;}

    public String getEnglish_word() {return english_word;}

    public int getImage_resource_id() {return image_resource_id;}

    public int getAudio_resource_id() {return audio_resource_id;}

    public boolean hasImage(){return image_resource_id != NO_PROVIDE;}
}
