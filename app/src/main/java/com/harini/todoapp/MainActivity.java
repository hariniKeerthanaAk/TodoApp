package com.harini.todoapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static List<Task> taskList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private EditText editTextTaskName;
    private EditText editTextTaskDescription;
    private Button buttonAddTask;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TASK_LIST_KEY = "taskList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        editTextTaskName = findViewById(R.id.editTextTaskName);
        editTextTaskDescription = findViewById(R.id.editTextTaskDescription);
        buttonAddTask = findViewById(R.id.buttonAddTask);

        loadData();

        adapter = new TaskAdapter(this, taskList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonAddTask.setOnClickListener(v -> {
            String taskName = editTextTaskName.getText().toString();
            String taskDescription = editTextTaskDescription.getText().toString();

            Task newTask = new Task(taskName, taskDescription);
            taskList.add(newTask);
            adapter.notifyItemInserted(taskList.size() - 1);

            saveData();

            editTextTaskName.getText().clear();
            editTextTaskDescription.getText().clear();
        });
    }


    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(taskList);
        editor.putString(TASK_LIST_KEY, json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(TASK_LIST_KEY, null);
        Type type = new TypeToken<ArrayList<Task>>() {
        }.getType();
        taskList = gson.fromJson(json, type);

        if (taskList == null) {
            taskList = new ArrayList<>();
        }
    }
}