package com.example.philoniare.todoapp.activities;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.philoniare.todoapp.R;
import com.example.philoniare.todoapp.models.TodoItem;
import com.example.philoniare.todoapp.persistence.TodoContract;
import com.example.philoniare.todoapp.persistence.TodoProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by philoniare on 8/28/2016.
 */

public class AddUpdateTodoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    @BindView(R.id.todo_title) EditText todoTitle;
    @BindView(R.id.todo_priority) Spinner todoPriority;
    @BindView(R.id.todo_status) Spinner todoStatus;
    @BindView(R.id.todo_notes) EditText todoNotes;
    @BindView(R.id.btn_add) Button submitButton;
    private boolean isEditingTodo = false;
    private Long todoId;
    private List<String> PRIORITIES = Arrays.asList("High", "Normal", "Low");
    private List<String> STATUS = Arrays.asList("Not started", "In progress", "Complete");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_todo_add);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();

        // Initialize the priority spinner
        todoPriority.setOnItemSelectedListener(this);
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, PRIORITIES);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        todoPriority.setAdapter(priorityAdapter);

        // Initialize the status spinner
        todoPriority.setOnItemSelectedListener(this);
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, STATUS);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        todoStatus.setAdapter(statusAdapter);

        if(bundle != null) {
            todoId = bundle.getLong(TodoItem.ID_KEY);
            String bundleTodoTitle = bundle.getString(TodoItem.TITLE_KEY);
            String bundleTodoNotes = bundle.getString(TodoItem.NOTES_KEY);
            String bundleTodoStatus = bundle.getString(TodoItem.STATUS_KEY);
            String bundleTodoPriority = bundle.getString(TodoItem.PRIORITY_KEY);
            isEditingTodo = true;
            submitButton.setText(getString(R.string.update_button));
            todoPriority.setSelection(PRIORITIES.indexOf(bundleTodoPriority));
            todoStatus.setSelection(STATUS.indexOf(bundleTodoStatus));
            todoNotes.setText(bundleTodoNotes);
            todoTitle.setText(bundleTodoTitle);
        }
    }

    @OnClick(R.id.btn_add)
    public void addUpdateTodo(View view) {
        String title = todoTitle.getText().toString();
        String notes = todoNotes.getText().toString();
        String priority = todoPriority.getSelectedItem().toString();
        String status = todoStatus.getSelectedItem().toString();

        if(isFormValid(title, notes)) {
            ContentValues values = new ContentValues();
            values.put(TodoContract.TodoEntry.COLUMN_TITLE, title);
            values.put(TodoContract.TodoEntry.COLUMN_PRIORITY, priority);
            values.put(TodoContract.TodoEntry.COLUMN_STATUS, status);
            values.put(TodoContract.TodoEntry.COLUMN_NOTES, notes);
            if(isEditingTodo) {
                Uri singleUri = ContentUris.withAppendedId(TodoProvider.CONTENT_URI, todoId);
                this.getContentResolver().update(singleUri, values, null, null);
            } else {
                getContentResolver().insert(TodoProvider.CONTENT_URI, values);
            }
            finish();
        }
    }

    private boolean isFormValid(String title, String notes) {
        // Validate the form input
        return !title.isEmpty() && !notes.isEmpty();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Stub method
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Stub method
    }

}
