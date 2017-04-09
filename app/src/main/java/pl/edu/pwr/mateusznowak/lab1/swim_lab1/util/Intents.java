package pl.edu.pwr.mateusznowak.lab1.swim_lab1.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * Created by Mateusz on 10.04.2017.
 */

public class Intents {

    public static boolean isIntentSafe(Context context, Intent browserIntent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(browserIntent, 0);
        return activities.size() > 0;
    }
}
