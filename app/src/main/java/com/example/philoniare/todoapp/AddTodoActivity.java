package com.example.philoniare.todoapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by philoniare on 8/28/2016.
 */

public class AddTodoActivity extends AppCompatActivity {
    @BindView(R.id.todo_title) EditText todoTitle;
    @BindView(R.id.btn_add) Button submitButton;
    private boolean isEditingTodo = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_add);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            Long bundleTodoId = bundle.getLong("TODO_ID");
            String bundleTodoTitle = bundle.getString("TODO_TITLE");
            isEditingTodo = true;
            submitButton.setText("Update");
            todoTitle.setText(bundleTodoTitle);
        }
    }

    @OnClick(R.id.btn_add)
    public void addTodo(View view) {
        String title = todoTitle.getText().toString();
        if(!title.isEmpty()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TodoContract.TodoEntry.COLUMN_TITLE, todoTitle.getText().toString());
            contentValues.put(TodoContract.TodoEntry.COLUMN_COMPLETED, 0);
            getContentResolver().insert(TodoProvider.CONTENT_URI, contentValues);
            finish();
        }
    }
}
