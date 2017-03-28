package pl.kuba.todolist2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobScheduler;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Date;
import java.util.List;

import pl.kuba.todolist2.database.ITaskDatabase;
import pl.kuba.todolist2.model.TodoTask;
import pl.kuba.todolist2.service.TodoNotificationService;

public class NotificationsPlanner {
    private ITaskDatabase mTaskDatabase;
    private Context mContext;

    public NotificationsPlanner(ITaskDatabase mTaskDatabase, Context mContext) {
        this.mTaskDatabase = mTaskDatabase;
        this.mContext = mContext;
    }

    public void planNotifications (){
        // pobieranie zadan z przypomnieniami
        List<TodoTask> tasks = mTaskDatabase.getFutureTasksWithReminder(new Date());
        //uzycie alarmmenagera
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        for (TodoTask task: tasks) {
            Intent serviceIntent = new Intent(mContext, TodoNotificationService.class);
            serviceIntent.putExtra("id", task.getId());

            PendingIntent pendingIntent =
                    PendingIntent.getService(mContext,task.getId(), serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            if (Build.VERSION.SDK_INT <Build.VERSION_CODES.KITKAT) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, task.getReminderDate().getTime(), pendingIntent);
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, task.getReminderDate().getTime(), pendingIntent);
            }
        }
    }
}
