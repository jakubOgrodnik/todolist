package pl.kuba.todolist2.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import pl.kuba.todolist2.R;
import pl.kuba.todolist2.model.TodoTask;

public class TodoTaskAdapter extends RecyclerView.Adapter<TodoTaskAdapter.TodoViewHolder> {
    private List<TodoTask> mTasks;
    private OnClickListener mClickListener;

    public TodoTaskAdapter(List<TodoTask> tasks, OnClickListener mClickListener) {
        this.mTasks = tasks;
        this.mClickListener = mClickListener;
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rowView = inflater.inflate(R.layout.list_item_todo, parent, false);
        return new TodoViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        //pobranie elementu danych na position
        TodoTask task = mTasks.get(position);

        // uzupelnienie widoku na podstawie pobranego obiektu
        holder.mCurrentPosition = task.getId();
        holder.mCurrentTask = task;
        holder.mTitle.setText(task.getName());
        holder.mDone.setChecked(task.isDone());

    }

    public void setmTasks(List<TodoTask> mTasks) {
        this.mTasks = mTasks;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.task_done)
        CheckBox mDone;
        @BindView(R.id.task_title)
        TextView mTitle;
        TodoTask mCurrentTask;
        int mCurrentPosition;

        public TodoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @OnClick
        void OnItemClick() {
            if (mClickListener != null) {
                mClickListener.onClick(mCurrentTask,mCurrentPosition);
            }
        }
        @OnCheckedChanged(R.id.task_done)
        void onCheckedChanged(boolean checked){
            if (mClickListener !=null && mCurrentTask !=null){
                mClickListener.onTaskDoneChenged(mCurrentTask, mCurrentPosition, checked);
            }
        }
    }

public interface OnClickListener {
    void onClick (TodoTask task, int position);
    void onTaskDoneChenged(TodoTask task, int position,  boolean isDone);
}



}
