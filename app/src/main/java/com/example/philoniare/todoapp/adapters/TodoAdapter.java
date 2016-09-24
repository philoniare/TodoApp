package com.example.philoniare.todoapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.philoniare.todoapp.R;
import com.example.philoniare.todoapp.interfaces.RefreshInterface;
import com.example.philoniare.todoapp.activities.AddUpdateTodoActivity;
import com.example.philoniare.todoapp.models.TodoItem;

public class TodoAdapter extends CursorAdapter {

    public TodoAdapter(Context context, Cursor cursor, RefreshInterface refreshInterface) {
        super(context, cursor, 0);
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
                Intent editTodoIntent = new Intent(context, AddUpdateTodoActivity.class);
                editTodoIntent.putExtra("TODO_ID", currentTodo.getId());
                editTodoIntent.putExtra("TODO_TITLE", currentTodo.getTitle());
                context.startActivity(editTodoIntent);
            }
        });
    }
}
