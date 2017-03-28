package pl.kuba.todolist2.activty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.kuba.todolist2.NotificationsPlanner;
import pl.kuba.todolist2.database.ITaskDatabase;
import pl.kuba.todolist2.R;
import pl.kuba.todolist2.database.SqliteTaskDatabase;
import pl.kuba.todolist2.model.TodoTask;
import pl.kuba.todolist2.adapter.TodoTaskAdapter;

public class TodoListActivity extends AppCompatActivity implements TodoTaskAdapter.OnClickListener {
    @BindView(R.id.task_list)
    RecyclerView mTodoList;

    private ITaskDatabase mTaskDatabase;
    private TodoTaskAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        ButterKnife.bind(this);
        mTaskDatabase = new SqliteTaskDatabase(this);
        mTodoList.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new TodoTaskAdapter(mTaskDatabase.getTasks(), this);
        mTodoList.setAdapter(mAdapter);
        // mTodoList.setAdapter(listAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();

        new NotificationsPlanner(mTaskDatabase, this).planNotifications();
    }

    private void refreshList() {
        mAdapter.setmTasks(mTaskDatabase.getTasks());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_todolist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.menu_item_create){
            Intent createTaskIntent = new Intent(this, TaskCreateActivity.class);
            startActivity(createTaskIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(TodoTask task, int position) {
        Intent createTaskIntent = new Intent(this, TaskCreateActivity.class);
        createTaskIntent.putExtra("pos", position);
        startActivity(createTaskIntent);

    }

    @Override
    public void onTaskDoneChenged(TodoTask task, int position, boolean isDone) {
        task.setDone(isDone);
        task.setDateCreated(new Date());
        mTaskDatabase.updateTask(task, position);
        refreshList();
    }
}
