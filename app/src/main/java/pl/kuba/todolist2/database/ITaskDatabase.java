package pl.kuba.todolist2.database;

import java.util.Date;
import java.util.List;

import pl.kuba.todolist2.model.TodoTask;

public interface ITaskDatabase {
    List<TodoTask> getTasks();

    List <TodoTask> getFutureTasksWithReminder (Date now);

    TodoTask getTask(int position);

    void addTask(TodoTask task);

    void updateTask(TodoTask task, int position);
}
