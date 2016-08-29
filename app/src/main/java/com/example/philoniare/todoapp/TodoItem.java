package com.example.philoniare.todoapp;

import android.database.Cursor;
import android.util.Log;

public class TodoItem {
    private String title;
    private long id;
    private int completed;

    public TodoItem(long id, String title, int completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public String getTitle() {
        return title;
    }

    public long getId() {
        return id;
    }

    public int getStatus() {
        return completed;
    }

    public void setStatus(boolean b) {
        if(b) {
            completed = 1;
        } else {
            completed = 0;
        }
    }

    public static TodoItem fromCursor(Cursor cursor) {
        try {
            int idIndex = cursor.getColumnIndexOrThrow(TodoContract.TodoEntry._ID);
            int titleIndex = cursor.getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_TITLE);
            int completedIndex = cursor.getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_COMPLETED);
            long id = cursor.getLong(idIndex);
            String title = cursor.getString(titleIndex);
            int completed = cursor.getInt(completedIndex);
            return new TodoItem(id, title, completed);
        } catch(Exception e) {
            e.printStackTrace();
            Log.e(Utils.LOG_TAG, "Conversion from Cursor to POJO failed");
        }
        return null;
    }
}
