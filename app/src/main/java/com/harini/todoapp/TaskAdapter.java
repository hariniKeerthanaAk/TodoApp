package com.harini.todoapp;

import static android.content.Context.MODE_PRIVATE;
import static com.harini.todoapp.MainActivity.SHARED_PREFS;
import static com.harini.todoapp.MainActivity.TASK_LIST_KEY;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> taskList;

    private Context context;

    public TaskAdapter(Context context, List<Task> taskList) {
        this.taskList = taskList;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewDescription;

        private TextView textViewPriority;
        private TextView textViewEndDate;
        private ImageButton buttonDelete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewPriority = itemView.findViewById(R.id.textViewPriority);
            textViewEndDate = itemView.findViewById(R.id.textViewEndDate);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }

        public void bind(Task task) {
            textViewName.setText(task.getName());
            textViewDescription.setText(task.getDescription());
            textViewPriority.setText(task.getPriority());
            textViewEndDate.setText(task.getEndDate());
            buttonDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                taskList.remove(position);
                notifyItemRemoved(position);
                saveData();
            });
        }

        private void saveData() {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(taskList);
            editor.putString(TASK_LIST_KEY, json);
            editor.apply();
        }
    }
}
