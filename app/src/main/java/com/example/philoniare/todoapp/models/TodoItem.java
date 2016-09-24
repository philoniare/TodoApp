package com.example.philoniare.todoapp.models;

import android.database.Cursor;
import android.util.Log;

import com.example.philoniare.todoapp.utils.Utils;
import com.example.philoniare.todoapp.persistence.TodoContract;

public class TodoItem {
    public enum PRIORITY { LOW, MEDIUM, HIGH }
    public enum STATUS { NOT_STARTED, IN_PROGRESS, COMPLETE }
    private String title;
    private String notes;
    private long id;
    private int priority;
    private int status;

    public TodoItem(long id, String title, int priority, int status, String notes) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.status = status;
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public long getId() {
        return id;
    }

    public static TodoItem fromCursor(Cursor cursor) {
        try {
            int idIndex = cursor.getColumnIndexOrThrow(TodoContract.TodoEntry._ID);
            int titleIndex = cursor.getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_TITLE);
            int priorityIndex = cursor.getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_PRIORITY);
            int statusIndex = cursor.getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_STATUS);
            int notesIndex = cursor.getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NOTES);
            long id = cursor.getLong(idIndex);
            String title = cursor.getString(titleIndex);
            String notes = cursor.getString(notesIndex);
            int priority = cursor.getInt(priorityIndex);
            int status = cursor.getInt(statusIndex);
            return new TodoItem(id, title, priority, status, notes);
        } catch(Exception e) {
            e.printStackTrace();
            Log.e(Utils.LOG_TAG, "Conversion from Cursor to POJO failed");
        }
        return null;
    }
}
