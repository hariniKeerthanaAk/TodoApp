package com.harini.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Task> taskList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private EditText editTextTaskName;
    private EditText editTextTaskDescription;
    private Button buttonAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        editTextTaskName = findViewById(R.id.editTextTaskName);
        editTextTaskDescription = findViewById(R.id.editTextTaskDescription);
        buttonAddTask = findViewById(R.id.buttonAddTask);

        adapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = editTextTaskName.getText().toString();
                String taskDescription = editTextTaskDescription.getText().toString();

                Task newTask = new Task(taskName, taskDescription);
                taskList.add(newTask);
                adapter.notifyItemInserted(taskList.size() - 1);
                editTextTaskName.getText().clear();
                editTextTaskDescription.getText().clear();
            }
        });
    }
}
