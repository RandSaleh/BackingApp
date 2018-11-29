package widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.actc.myapplication.MainActivity;
import com.example.actc.myapplication.Model.Ingredients;

import java.io.Serializable;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;

public class WidgetUpdateService extends IntentService implements Serializable {
    public static final String WIDGET_UPDATE_ACTION = "com.example.actc.myapplication.update_widget";
    private ArrayList<Ingredients> mIngrediants;

    public WidgetUpdateService() {
        super("WidgetServiceUpdate");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null && intent.getAction().equals(WIDGET_UPDATE_ACTION)) {
            Bundle bundle = intent.getBundleExtra(MainActivity.BUNDLE);
            mIngrediants = (ArrayList<Ingredients>) bundle.getSerializable("Ingradiant");
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, NewAppWidgetPrivider.class));
            NewAppWidgetPrivider.updateAppWidget(this, appWidgetManager, appWidgetIds, mIngrediants);
        }
    }
}
