package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ToDoViewHolder>{

    private Context context;
    private ArrayList<Task> tasks;

    public TaskAdapter(Context context, ArrayList<Task> tasks){
        this.tasks = tasks;
        this.context = context;
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_viewholder, parent, false);
        ToDoViewHolder holder = new ToDoViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ToDoViewHolder toDoViewHolder,final int i) {
        toDoViewHolder.taskTitle.setText(tasks.get(i).getTitle() + " ");
        toDoViewHolder.taskStatus.setText(tasks.get(i).getStatus() + " ");
        toDoViewHolder.taskCategory.setText(tasks.get(i).getCategory());

        toDoViewHolder.parentLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                String arr[] = {
                        String.valueOf(tasks.get(i).getId()),
                        tasks.get(i).getTitle(),
                        tasks.get(i).getDescription(),
                        tasks.get(i).getStatus(),
                        tasks.get(i).getCategory(),
                        tasks.get(i).getDuration()
                };

                intent.putExtra("choosenTask", arr);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ToDoViewHolder extends RecyclerView.ViewHolder{

        public TextView taskTitle;
        public TextView taskStatus;
        public TextView taskCategory;
        public LinearLayout parentLayout;

        public ToDoViewHolder(View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.todoTitle);
            taskStatus = itemView.findViewById(R.id.todoStatus);
            taskCategory = itemView.findViewById(R.id.todoCategory);
            parentLayout = itemView.findViewById(R.id.parentLayout);
        }
    }


}
