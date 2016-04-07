package com.example.zq.mynote;

import java.util.Date;

/**
 * Created by Michael on 2015/1/28.
 */
public class Note {
    private String note_id;
    private Date date;

    private String Text;

    public Note(String note_id, Date date, String text) {
        this.note_id = note_id;
        this.date = date;
        Text = text;
    }

    public String getNote_id() {
        return note_id;
    }

    public void setNote_id(String note_id) {
        this.note_id = note_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }
}
