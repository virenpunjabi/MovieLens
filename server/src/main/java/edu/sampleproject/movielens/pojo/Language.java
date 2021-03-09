package edu.sampleproject.movielens.pojo;

public enum Language {
    HINDI("Hindi"),
    ENGLISH("English"),
    MARATHI("Marathi"),
    TELUGU("Telugu"),
    TAMIL("Tamil");

    String languageName;
    Language(String languageName){
        this.languageName = languageName;
    }

    public String getLanguageName(){
        return languageName;
    }
}
