package com.eatinghabit.sehyunpark.eatinghabits.provider;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.eatinghabit.sehyunpark.eatinghabits.EatingTimeActivity;
import com.eatinghabit.sehyunpark.eatinghabits.R;

/**
 * Created by sehyunpark on 2015-10-26.
 */
public class WidgetProvider extends AppWidgetProvider{

    @Override
    public void onReceive(Context context, Intent intent) {
      super.onReceive(context, intent);
     }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                            int[] appWidgetIds) {

        super.onUpdate(context, appWidgetManager, appWidgetIds);
        appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
        for (int i = 0; i < appWidgetIds.length; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        }

            @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        }

    public static void updateAppWidget(Context context,
                                       AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews updateViews = new RemoteViews(context.getPackageName(),
                R.layout.widget);
        
        Intent intent = new Intent(context, EatingTimeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intent, 0);
        updateViews.setOnClickPendingIntent(R.id.mLayout, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, updateViews);
        
    }
}
