package com.example.aw_library.log;

import androidx.annotation.NonNull;

import java.util.List;

public class AwLog {
    private static final String AW_LOG_PACKAGE;

    static {
        String className = AwLog.class.getName();
        AW_LOG_PACKAGE = className.substring(0, className.lastIndexOf(".") + 1);
    }

    public static void v(Object... content) {

        log(LogLevel.V, content);
    }

    public static void vt(String tag, Object... content) {
        log(LogLevel.V, tag, content);
    }

    public static void d(Object... content) {

        log(LogLevel.D, content);
    }

    public static void dt(String tag, Object... content) {
        log(LogLevel.D, tag, content);
    }

    public static void i(Object... content) {

        log(LogLevel.I, content);
    }

    public static void it(String tag, Object... content) {
        log(LogLevel.I, tag, content);
    }

    public static void w(Object... content) {

        log(LogLevel.W, content);
    }

    public static void wt(String tag, Object... content) {
        log(LogLevel.W, tag, content);
    }

    public static void e(Object... content) {

        log(LogLevel.E, content);
    }

    public static void et(String tag, Object... content) {
        log(LogLevel.E, tag, content);
    }

    public static void a(Object... content) {

        log(LogLevel.A, content);
    }

    public static void at(String tag, Object... content) {
        log(LogLevel.A, tag, content);
    }


    public static void log(@LogLevel.LEVEL int level, Object... content) {
        log(level, AwLogManager.getInstance().getConfig().getGlobalTag(), content);
    }

    public static void log(@LogLevel.LEVEL int level, String tag, Object... content) {
        log(AwLogManager.getInstance().getConfig(), level, tag, content);
    }

    public static void log(@NonNull AwLogConfig config, @LogLevel.LEVEL int level, String tag, Object... content) {
        if (!config.enable()) {
            return;
        }

        StringBuilder builder = new StringBuilder();
        //需要线程信息
        if (config.includeThread()) {
            AwLogFormatter<Thread> threadFormatter = config.getThreadFormatter();
            if (threadFormatter == null) {
                threadFormatter = AwLogManager.getDefaultThreadFormatter();
            }
            builder.append(threadFormatter.format(Thread.currentThread()));
            builder.append("\n");
        }
        //需要堆栈信息
        if (config.getStackDepth() > 0) {

            AwLogFormatter<StackTraceElement[]> stackTraceFormatter = config.getStackTraceFormatter();
            if (stackTraceFormatter == null) {
                stackTraceFormatter = AwLogManager.getDefaultStackTraceFormatter();
            }

            builder.append(stackTraceFormatter.format(AwStackTraceUtil.getCroppedRealStackTrace(new Throwable().getStackTrace(),
                    AW_LOG_PACKAGE, config.getStackDepth())));
            builder.append("\n");

        }

        String body = parseBody(config, content);
        builder.append(body).append("\n");

        List<AwLogPrinter> printers = config.getPrinters() == null ? AwLogManager.getInstance().getPrinters() : config.getPrinters();

        if (printers != null && printers.size() > 0) {
            for (AwLogPrinter printer : printers) {
                printer.print(config, level, tag, builder.toString());
            }
        }

    }

    private static String parseBody(AwLogConfig config, Object[] content) {
        if (config.getJsonParser() != null) {
            return config.getJsonParser().toJson(content);
        }
        StringBuilder builder = new StringBuilder();

        for (Object con : content) {
            builder.append(con).append(",");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(0);
        }

        return builder.toString();
    }


}
