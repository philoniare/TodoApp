package com.example.philoniare.todoapp.model;

import android.database.Cursor;
import android.util.Log;

import com.example.philoniare.todoapp.utility.Utils;
import com.example.philoniare.todoapp.persistence.TodoContract;

public class TodoItem {
    private String title;
    private long id;
    private int completed = 0;

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

    public int getCompleted() {
        return completed;
    }

    public static boolean getStatus(int completionStatus) {
        return completionStatus == 1;
    }

    public static int covertStatus(boolean b) {
        return b ? 1 : 0;
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
