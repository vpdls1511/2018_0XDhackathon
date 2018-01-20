package com.keen.android.happckathon.libs;

/**
 * Created by Kimhyeongmin on 2018. 1. 20..
 */

public class Item {

    int boardImage;
    String boardTitle;
    String boardLoation;
    String boardDate;


    public int getBoardImage() {
        return boardImage;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public String getBoardLoation() {
        return boardLoation;
    }

    public String getBoardDate() {
        return boardDate;
    }



    public Item(int boardImage, String boardTitle, String boardLoation, String boardDate){
        this.boardImage = boardImage;
        this.boardTitle = boardTitle;
        this.boardLoation = boardLoation;
        this.boardDate = boardDate;

    }
}
