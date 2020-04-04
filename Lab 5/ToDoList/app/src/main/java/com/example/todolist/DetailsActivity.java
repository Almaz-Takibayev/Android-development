package com.example.todolist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    EditText id;
    EditText title;
    EditText description;
    EditText status;
    EditText category;
    EditText duration;
    Button deleteBtn, updateBtn;
    AppDatabase appDatabase;
    TaskDAO taskDAO;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        appDatabase = MyApplication.getInstance().getDatabase();
        taskDAO = appDatabase.taskDAO();

        id = findViewById(R.id.id);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        status = findViewById(R.id.status);
        category = findViewById(R.id.category);
        duration = findViewById(R.id.duration);
        deleteBtn = findViewById(R.id.deleteBtn);
        updateBtn = findViewById(R.id.updateBtn);
        String arr[] = getIntent().getStringArrayExtra("choosenTask");
        id.setText(arr[0]);
        title.setText(arr[1]);
        description.setText(arr[2]);
        status.setText(arr[3]);
        category.setText(arr[4]);
        duration.setText(arr[5]);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskDAO.delete(taskDAO.getById(Integer.parseInt(id.getText().toString())));
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newtitle = findViewById(R.id.title);
                EditText newdescription = findViewById(R.id.description);
                EditText newstatus = findViewById(R.id.status);
                EditText newcategory = findViewById(R.id.category);
                EditText newduration = findViewById(R.id.duration);

                title.setText(newtitle.getText());
                description.setText(newdescription.getText());
                status.setText(newstatus.getText());
                category.setText(newcategory.getText());
                duration.setText(newduration.getText());

                Task task = taskDAO.getById(Integer.parseInt(id.getText().toString()));
                task.title = title.getText().toString();
                task.description = description.getText().toString();
                task.status = status.getText().toString();
                task.category = category.getText().toString();
                task.duration = duration.getText().toString();
                taskDAO.update(task);
            }
        });
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.recycler);
        TaskAdapter adapter = new TaskAdapter(this, new ArrayList<Task>(MyApplication.getInstance().getDatabase().taskDAO().getAll()));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
