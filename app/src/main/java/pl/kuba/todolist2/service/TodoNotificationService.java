package pl.kuba.todolist2.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import pl.kuba.todolist2.R;
import pl.kuba.todolist2.database.ITaskDatabase;
import pl.kuba.todolist2.database.SqliteTaskDatabase;
import pl.kuba.todolist2.model.TodoTask;

public class TodoNotificationService extends IntentService {
    private ITaskDatabase mTaskDatabase;

    public TodoNotificationService() {
        super("TodoNotificationService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mTaskDatabase = new SqliteTaskDatabase(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int taskId  = intent.getIntExtra("id", -1);
        TodoTask task = mTaskDatabase.getTask(taskId);

        if (task == null) {
            return;
        }

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(task.getName())
                .setContentText("Przypominacz")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setTicker(task.getName())
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(taskId, notification);
    }
}
