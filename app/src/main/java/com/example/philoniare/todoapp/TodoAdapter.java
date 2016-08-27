package com.example.philoniare.todoapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private ArrayList<TodoItem> todoItems;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.todo_title) TextView mTodoTitle;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public TodoAdapter(ArrayList<TodoItem> todoItems) {
        this.todoItems = todoItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TodoItem currentTodo = todoItems.get(position);
        holder.mTodoTitle.setText(currentTodo.getTitle());

    }

    @Override
    public int getItemCount() {
        return todoItems.size();
    }
}
