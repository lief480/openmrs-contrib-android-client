package org.openmrs.mobile.utilities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import org.openmrs.mobile.R;
import org.openmrs.mobile.application.OpenMRS;
import org.openmrs.mobile.application.OpenMRSLogger;


public class NotificationUtil {
    private static OpenMRSLogger logger = OpenMRS.getInstance().getOpenMRSLogger();

    private static int mId = 0;
    private NotificationUtil() {

    }

    public static void showNotification(Context context, String title, int textId, Class parentClass) {
        showNotification(context, title, context.getResources().getString(textId), parentClass);
    }

    private static void showNotification(Context context, String title, String text, Class parentClass){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle(title)
                        .setContentText(text);

        Intent resultIntent = new Intent(context, parentClass);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(parentClass);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(++mId, mBuilder.build());
    }
}
