package com.example.aw_library.log.model;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class AWLogModel {
    public String tag;
    public String log;
    public int level;
    public long milliTime;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);


    public AWLogModel(String tag, String log, int level, long milliTime) {
        this.tag = tag;
        this.log = log;
        this.level = level;
        this.milliTime = milliTime;
    }


    public String getFlattenedLog() {
        return getFlattened() + "\n" + log;
    }

    public String getFlattened() {
        return format(milliTime) + "|" + level + "|" + tag + "|:";
    }

    private String format(long milliTime) {
        return sdf.format(milliTime);
    }
}
