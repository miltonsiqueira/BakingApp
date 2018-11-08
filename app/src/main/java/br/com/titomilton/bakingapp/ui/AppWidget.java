package br.com.titomilton.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {

    public static final String SHARED_PREFERENCE_INGREDIENTS = "SP_INGREDIENTS";

    public static final String EXTRA_INGREDIENTS = "SP_INGREDIENTS";
    private static final String EXTRA_WIDGET_IDS = "WIDGET_IDS";


    public static void setNewIngredients(Context context, String ingredients) {
        SharedPreferences prefs = context.getSharedPreferences(context.getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
        prefs.edit()
                .putString(SHARED_PREFERENCE_INGREDIENTS, ingredients)
                .apply();

        AppWidgetManager man = AppWidgetManager.getInstance(context);
        int[] ids = man.getAppWidgetIds(
                new ComponentName(context,AppWidget.class));
        Intent updateIntent = new Intent();
        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updateIntent.putExtra(AppWidget.EXTRA_WIDGET_IDS, ids);
        updateIntent.putExtra(AppWidget.EXTRA_INGREDIENTS, ingredients);
        context.sendBroadcast(updateIntent);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        SharedPreferences prefs = context.getSharedPreferences(context.getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
        String widgetText = prefs.getString(SHARED_PREFERENCE_INGREDIENTS, "");
        updateAppWidget(context, appWidgetManager, appWidgetIds, widgetText);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.hasExtra(EXTRA_INGREDIENTS) && intent.hasExtra(EXTRA_WIDGET_IDS)) {
            String ingredients = intent.getStringExtra(EXTRA_INGREDIENTS);
            int[] ids = intent.getIntArrayExtra(EXTRA_INGREDIENTS);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            updateAppWidget(context, appWidgetManager, ids, ingredients);
        }

        super.onReceive(context, intent);
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                 int[] appWidgetIds, String ingredients) {

        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
            views.setTextViewText(R.id.appwidget_text, ingredients);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }

}

