package com.example.philoniare.todoapp.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.philoniare.todoapp.R;
import com.example.philoniare.todoapp.interfaces.RefreshInterface;
import com.example.philoniare.todoapp.adapters.TodoAdapter;
import com.example.philoniare.todoapp.persistence.TodoProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodoListActivity extends AppCompatActivity {
    @BindView(R.id.todo_list_view) ListView mListView;
    @BindView(R.id.empty_view) TextView mEmptyView;
    private TodoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNewTodoIntent = new Intent(TodoListActivity.this, AddUpdateTodoActivity.class);
                startActivity(addNewTodoIntent);
            }
        });

        mAdapter = new TodoAdapter(this, null, new RefreshInterface() {
            @Override
            public void onRefreshCallback() {
                refreshValuesFromContentProvider();
            }
        });
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(mEmptyView);
        refreshValuesFromContentProvider();
    }

    private void refreshValuesFromContentProvider() {
        CursorLoader cursorLoader = new CursorLoader(this, TodoProvider.CONTENT_URI, null,
                null, null, null);
        Cursor c = cursorLoader.loadInBackground();
        mAdapter.swapCursor(c);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshValuesFromContentProvider();
    }
}
