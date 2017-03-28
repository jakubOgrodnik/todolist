package pl.kuba.todolist2.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import pl.kuba.todolist2.NotificationsPlanner;
import pl.kuba.todolist2.database.ITaskDatabase;
import pl.kuba.todolist2.database.SqliteTaskDatabase;

public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ITaskDatabase taskDatabase = new SqliteTaskDatabase(context);

        new NotificationsPlanner(taskDatabase, context).planNotifications();
    }
}
