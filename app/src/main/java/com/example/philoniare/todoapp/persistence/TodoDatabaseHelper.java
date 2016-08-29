package com.example.philoniare.todoapp.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.philoniare.todoapp.persistence.TodoContract;

import static com.example.philoniare.todoapp.persistence.TodoContract.TodoEntry.TABLE_NAME;

/**
 * Created by philoniare on 8/28/2016.
 */

public class TodoDatabaseHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "todos.db";
    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_TODOS = "CREATE TABLE " + TABLE_NAME + " (" +
            TodoContract.TodoEntry._ID + " INTEGER PRIMARY KEY," +
            TodoContract.TodoEntry.COLUMN_COMPLETED + " INTEGER" + COMMA_SEP +
            TodoContract.TodoEntry.COLUMN_TITLE + TEXT_TYPE + " )";

    private static final String SQL_DELETE_TODOS = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public TodoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TODOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_TODOS);
        onCreate(db);
    }

    public Cursor getTodos(String id, String[] projection, String selection,
                           String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqliteQueryBuilder = new SQLiteQueryBuilder();
        sqliteQueryBuilder.setTables(TABLE_NAME);

        if(id != null) {
            sqliteQueryBuilder.appendWhere("_id" + " = " + id);
        }

        if(sortOrder == null || sortOrder == "") {
            sortOrder = "TODOTITLE";
        }

        Cursor cursor = sqliteQueryBuilder.query(getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        return cursor;
    }

    public long addNewTodo(ContentValues values) throws SQLException {
        long id = getWritableDatabase().insert(TABLE_NAME, "", values);
        if(id <= 0) {
            throw new SQLException("Failed to add todo");
        }
        return id;
    }

    public int deleteTodos(String id) {
        if(id == null) {
            return getWritableDatabase().delete(TABLE_NAME, null, null);
        } else {
            return getWritableDatabase().delete(TABLE_NAME, "_id=?", new String[]{id});
        }
    }

    public int updateTodos(String id, ContentValues values) {
        if(id == null) {
            return getWritableDatabase().update(TABLE_NAME, values, null, null);
        } else {
            return getWritableDatabase().update(TABLE_NAME, values, "_id=?", new String[]{id});
        }
    }
}
