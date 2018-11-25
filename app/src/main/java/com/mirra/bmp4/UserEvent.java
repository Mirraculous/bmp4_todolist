package com.mirra.bmp4;


import java.io.Serializable;

public class UserEvent implements Serializable {
    String name, Comment;
    int day, month, year, id;
    boolean isFinished;

    public UserEvent(String name,
                     int day, int month, int year,
                     boolean isFinished,
                     String Comment)
    {
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
        this.isFinished = isFinished;
        this.Comment = Comment;
    }
}
