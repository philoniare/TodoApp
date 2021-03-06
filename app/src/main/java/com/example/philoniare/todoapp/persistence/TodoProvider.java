package com.example.philoniare.todoapp.persistence;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.philoniare.todoapp.persistence.TodoDatabaseHelper;

/**
 * Created by philoniare on 8/28/2016.
 */

public class TodoProvider extends ContentProvider {

    private static final String PROVIDER_NAME = "todoapp.contentprovider.todos";
    private static final String TODOS_BASE_URI = "todos";
    public static final Uri CONTENT_URI = Uri.parse("content://" +
            PROVIDER_NAME + "/" + TODOS_BASE_URI);
    private static final int TODOS = 1;
    private static final int TODO_ID = 2;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/todos";
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/todos";

    private static final UriMatcher uriMatcher = getUriMatcher();
    private static UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, TODOS_BASE_URI, TODOS);
        uriMatcher.addURI(PROVIDER_NAME, TODOS_BASE_URI + "/#", TODO_ID);
        return uriMatcher;
    }

    private TodoDatabaseHelper mTodoHelper;
    @Override
    public boolean onCreate() {
        mTodoHelper = new TodoDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        String id = null;
        if(uriMatcher.match(uri) == TODO_ID) {
            id = uri.getPathSegments().get(1);
        }
        return mTodoHelper.getTodos(id, projection, selection, selectionArgs, sortOrder);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case TODOS:
                return "vnd.android.cursor.dir/vnd.com.todoapp.contentprovider.provider.todos";
            case TODO_ID:
                return "vnd.android.cursor.item/vnd.com.todoapp.contentprovider.provider.todos";
        }
        return "";
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        try {
            long id = mTodoHelper.addNewTodo(values);
            return ContentUris.withAppendedId(CONTENT_URI, id);
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        String id = null;
        if(uriMatcher.match(uri) == TODO_ID) {
            id = uri.getPathSegments().get(1);
        }
        return mTodoHelper.deleteTodos(id);
    }

    @Override
    public int update(Uri uri, ContentValues values, String s, String[] strings) {
        String id = null;
        if(uriMatcher.match(uri) == TODO_ID) {
            id = uri.getPathSegments().get(1);
        }
        return mTodoHelper.updateTodos(id, values);
    }
}
