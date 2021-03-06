package com.example.philoniare.todoapp.persistence;

import android.provider.BaseColumns;

/**
 * Created by philoniare on 8/27/2016.
 */

public final class TodoContract {
    private TodoContract() {}

    public static final class TodoEntry implements BaseColumns {
        public static final String TABLE_NAME = "todoItems";
        public static final String COLUMN_TITLE = "todoTitle";
        public static final String COLUMN_PRIORITY = "todoPriority";
        public static final String COLUMN_STATUS = "todoStatus";
        public static final String COLUMN_NOTES= "todoNotes";
    }
}
