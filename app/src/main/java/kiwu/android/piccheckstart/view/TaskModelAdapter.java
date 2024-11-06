package kiwu.android.piccheckstart.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kiwu.android.piccheckstart.R;
import kiwu.android.piccheckstart.model.TaskModel;

public class TaskModelAdapter extends RecyclerView.Adapter<TaskModelAdapter.TaskViewHolder> {

    private ArrayList<TaskModel> tasks = new ArrayList<>();

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rec_view_list, parent, false);

        return new TaskViewHolder(view);
    } // end constructor

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskModel task = tasks.get(position);

        holder.onBind(task);
    } // end onBindViewHolder

    @Override
    public int getItemCount() {
        return tasks.size();
    } // end getItemCount
    public void addTask(TaskModel task) {
        tasks.add(task);
    } // end addStudent

    public void addTasks(ArrayList<TaskModel> tasks) {
        this.tasks = tasks;
    } // end addStudents

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvDate;
        private TextView tvTime;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
        } // end constructor

        public void onBind(TaskModel task) {
            tvTitle.setText(task.getTitle());
            tvDate.setText(task.getDate());
            tvTime.setText(task.getTime());
        } // end onBind
    } // end inner class
} // end class
