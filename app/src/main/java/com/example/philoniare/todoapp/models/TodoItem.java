package com.example.philoniare.todoapp.models;

import android.database.Cursor;
import android.util.Log;

import com.example.philoniare.todoapp.utils.Utils;
import com.example.philoniare.todoapp.persistence.TodoContract;

public class TodoItem {
    public static final String ID_KEY = "TODO_ID";
    public static final String TITLE_KEY = "TODO_TITLE";
    public static final String PRIORITY_KEY = "TODO_PRIORITY";
    public static final String NOTES_KEY = "TODO_NOTES";
    public static final String STATUS_KEY = "TODO_STATUS";

    private String title;
    private String notes;
    private long id;
    private String priority;
    private String status;

    public TodoItem(long id, String title, String priority, String status, String notes) {
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
            String priority = cursor.getString(priorityIndex);
            String status = cursor.getString(statusIndex);
            return new TodoItem(id, title, priority, status, notes);
        } catch(Exception e) {
            e.printStackTrace();
            Log.e(Utils.LOG_TAG, "Conversion from Cursor to POJO failed");
        }
        return null;
    }
}
