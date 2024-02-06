package com.harini.todoapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    private List<Task> taskList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private EditText editTextTaskName;
    private EditText editTextTaskDescription;
    private EditText editTextEndDate;
    private Spinner spinnerPriority;
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
        editTextEndDate = findViewById(R.id.editTextEndDate);
        spinnerPriority = findViewById(R.id.spinnerPriority);
        buttonAddTask = findViewById(R.id.buttonAddTask);

        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(this,
                R.array.priority_levels, android.R.layout.simple_spinner_item);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(priorityAdapter);

        loadData(); // Load saved tasks from SharedPreferences

        adapter = new TaskAdapter(this, taskList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = editTextTaskName.getText().toString();
                String taskDescription = editTextTaskDescription.getText().toString();
                String endDateString = editTextEndDate.getText().toString();
                Date endDate = null;
                try {
                    endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Priority priority = Priority.valueOf(spinnerPriority.getSelectedItem().toString());

                Task newTask = new Task(taskName, taskDescription, endDate, priority);
                taskList.add(newTask);
                adapter.notifyItemInserted(taskList.size() - 1);

                saveData(); // Save updated task list to SharedPreferences

                // Clear input fields after adding task
                editTextTaskName.getText().clear();
                editTextTaskDescription.getText().clear();
                editTextEndDate.getText().clear();
                spinnerPriority.setSelection(0); // Reset spinner to default
            }
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
        Type type = new TypeToken<ArrayList<Task>>() {}.getType();
        taskList = gson.fromJson(json, type);

        if (taskList == null) {
            taskList = new ArrayList<>();
        }
    }
}