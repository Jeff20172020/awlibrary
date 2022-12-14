package com.example.aw_library.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import org.jetbrains.annotations.NotNull;

public class AWDisplayUtil {

    public static int dp2px(float dp, Resources resources) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }

    public static float getDisplayWidthInPx(@NotNull Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            Display display = wm.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            return point.x;
        }


        return 0;
    }

    public static float getDisplayHeightInPx(@NotNull Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            Display display = wm.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            return point.y;
        }


        return 0;
    }
}
