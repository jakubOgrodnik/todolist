package pl.kuba.todolist2.database;

import android.content.Context;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import pl.kuba.todolist2.model.TodoTask;

public class SqliteTaskDatabase implements ITaskDatabase {


    private Dao<TodoTask, Integer> mDao;

    public SqliteTaskDatabase(Context context){
        TodoDbOpenHelper dbOpenHelper= new TodoDbOpenHelper(context);
        ConnectionSource cs = new AndroidConnectionSource(dbOpenHelper);
        try {
            mDao = DaoManager.createDao(cs, TodoTask.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TodoTask> getTasks() {
        try {
            return mDao.queryBuilder()
                    .orderBy("done", true)
                    .orderBy("dateCreated", false)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public List<TodoTask> getFutureTasksWithReminder(Date now) {
        try {
            return mDao.queryBuilder()
                    .where().eq("reminder", true)
                    .and().ge("reminderDate", now)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public TodoTask getTask(int position) {
        try {
            return mDao.queryForId(position);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addTask(TodoTask task) {
        try {
            mDao.create(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTask(TodoTask task, int position) {
        try {
            mDao.update(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
