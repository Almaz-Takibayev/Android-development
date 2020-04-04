package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    AppDatabase appDatabase;
    TaskDAO taskDAO;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDatabase = MyApplication.getInstance().getDatabase();
        taskDAO = appDatabase.taskDAO();

        initRecyclerView();

        Button button = findViewById(R.id.save_button);
        Button buttonDeleteAll = findViewById(R.id.deleteAll);
        final EditText newtitle = findViewById(R.id.newtitle);
        final EditText newdescription = findViewById(R.id.newdescription);
        final EditText newstatus = findViewById(R.id.newstatus);
        final EditText newcategory = findViewById(R.id.newcategory);
        final EditText newduration = findViewById(R.id.newduration);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = new Task();
                task.title = newtitle.getText().toString();
                task.description = newdescription.getText().toString();
                task.status = newstatus.getText().toString();
                task.category = newcategory.getText().toString();
                task.duration = newduration.getText().toString();
                taskDAO.insert(task);

                newtitle.setText("");
                newdescription.setText("");
                newstatus.setText("");
                newcategory.setText("");
                newduration.setText("");

                appDatabase = MyApplication.getInstance().getDatabase();
                taskDAO = appDatabase.taskDAO();

                initRecyclerView();
            }
        });

        buttonDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskDAO.deleteAll();
                appDatabase = MyApplication.getInstance().getDatabase();
                taskDAO = appDatabase.taskDAO();

                initRecyclerView();
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
