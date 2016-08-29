package com.example.philoniare.todoapp.adapter;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.philoniare.todoapp.R;
import com.example.philoniare.todoapp.utility.RefreshInterface;
import com.example.philoniare.todoapp.utility.Utils;
import com.example.philoniare.todoapp.activity.AddTodoActivity;
import com.example.philoniare.todoapp.model.TodoItem;
import com.example.philoniare.todoapp.persistence.TodoContract;
import com.example.philoniare.todoapp.persistence.TodoProvider;

public class TodoAdapter extends CursorAdapter {
    private RefreshInterface mRefreshInterface;

    public TodoAdapter(Context context, Cursor cursor, RefreshInterface refreshInterface) {
        super(context, cursor, 0);
        mRefreshInterface = refreshInterface;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.todo_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final TodoItem currentTodo = TodoItem.fromCursor(cursor);
        TextView todoTitle = (TextView) view.findViewById(R.id.todo_title);
        todoTitle.setText(currentTodo.getTitle());

        LinearLayout todoItemLayout = (LinearLayout) view.findViewById(R.id.todo_list_item);
        todoItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editTodoIntent = new Intent(context, AddTodoActivity.class);
                editTodoIntent.putExtra("TODO_ID", currentTodo.getId());
                editTodoIntent.putExtra("TODO_TITLE", currentTodo.getTitle());
                context.startActivity(editTodoIntent);
            }
        });

        CheckBox completionCheckbox = (CheckBox) view.findViewById(R.id.checkbox_completion);
        completionCheckbox.setChecked(TodoItem.getStatus(currentTodo.getCompleted()));
        completionCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ContentValues updateValues = new ContentValues();
                updateValues.put(TodoContract.TodoEntry.COLUMN_COMPLETED,
                        TodoItem.covertStatus(b));
                Uri singleUri = ContentUris.withAppendedId(TodoProvider.CONTENT_URI, currentTodo.getId());
                Log.d("SingleURI", singleUri.toString());
                context.getContentResolver().update(
                        singleUri,
                        updateValues,
                        null,
                        null
                );
            }
        });
    }
}
