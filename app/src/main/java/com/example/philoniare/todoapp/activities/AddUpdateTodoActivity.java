package com.example.philoniare.todoapp.activities;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.philoniare.todoapp.R;
import com.example.philoniare.todoapp.persistence.TodoContract;
import com.example.philoniare.todoapp.persistence.TodoProvider;

import java.util.ArrayList;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_todo_add);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();

        // Initialize the priority spinner
        todoPriority.setOnItemSelectedListener(this);
        List<String> priorities = new ArrayList<>();
        priorities.add("High");
        priorities.add("Normal");
        priorities.add("Low");
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, priorities);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        todoPriority.setAdapter(priorityAdapter);

        // Initialize the status spinner
        todoPriority.setOnItemSelectedListener(this);
        List<String> status = new ArrayList<>();
        status.add("Not started");
        status.add("In progress");
        status.add("Complete");
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, status);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        todoStatus.setAdapter(statusAdapter);

        if(bundle != null) {
            todoId = bundle.getLong("TODO_ID");
            String bundleTodoTitle = bundle.getString("TODO_TITLE");
            isEditingTodo = true;
            submitButton.setText(getString(R.string.update_button));
            todoTitle.setText(bundleTodoTitle);
        }
    }

    @OnClick(R.id.btn_add)
    public void addTodo(View view) {
        String title = todoTitle.getText().toString();
        if(isFormValid(title)) {
            ContentValues values = new ContentValues();
            if(isEditingTodo) {
                values.put(TodoContract.TodoEntry.COLUMN_TITLE, title);
                Uri singleUri = ContentUris.withAppendedId(TodoProvider.CONTENT_URI, todoId);
                this.getContentResolver().update(
                        singleUri,
                        values,
                        null,
                        null
                );
            } else {
                values.put(TodoContract.TodoEntry.COLUMN_TITLE, todoTitle.getText().toString());
                values.put(TodoContract.TodoEntry.COLUMN_PRIORITY, 0);
                getContentResolver().insert(TodoProvider.CONTENT_URI, values);
            }
            finish();
        }
    }

    private boolean isFormValid(String title) {
        return !title.isEmpty();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String priority = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Stub method
    }
}
