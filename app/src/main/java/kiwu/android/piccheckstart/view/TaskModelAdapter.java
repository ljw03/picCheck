package kiwu.android.piccheckstart.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kiwu.android.piccheckstart.model.TaskModel;

public class TaskModelAdapter extends RecyclerView.Adapter<TaskModelAdapter.TaskViewHolder> {

    private ArrayList<TaskModel> tasks = new ArrayList<>();

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout.list_view_list, parent, false);

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
    } // end addTask

    public void addTasks(ArrayList<TaskModel> tasks) {
        this.tasks = task;
    } // end addTasks

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNum;
        private TextView tvName;
        private TextView tvKor;
        private TextView tvEng;
        private TextView tvMat;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNum = itemView.findViewById(id.tvNum);
            tvName = itemView.findViewById(R.id.tvName);
            tvKor = itemView.findViewById(R.id.tvKor);
            tvEng = itemView.findViewById(R.id.tvEng);
            tvMat = itemView.findViewById(R.id.tvMat);
        } // end constructor

        public void onBind(TaskModel task) {
            tvNum.setText(task.getNum());
            tvName.setText(task.getName());
            tvKor.setText(String.valueOf(task.getKor()));
            tvEng.setText(String.valueOf(task.getEng()));
            tvMat.setText(String.valueOf(task.getMat()));
        } // end onBind
    } // end inner class
} // end class
