package kiwu.android.piccheckstart.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kiwu.android.piccheckstart.R;
import kiwu.android.piccheckstart.model.TaskModel;

public class TaskModelAdapter extends RecyclerView.Adapter<TaskModelAdapter.TaskViewHolder> {

    private ArrayList<TaskModel> tasks = new ArrayList<>();

    public void setItems(List<TaskModel> items) {
        tasks.clear();
        tasks.addAll(items);
        notifyDataSetChanged();  // 추가하여 RecyclerView 업데이트
    }

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
        this.tasks.clear(); // 기존 항목 제거
        this.tasks.addAll(tasks); // 새 항목 추가
        notifyDataSetChanged(); // 목록 갱신
    }

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
