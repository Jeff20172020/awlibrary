package com.example.aw_library.log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AwLogManager {

    AwLogConfig logConfig;
    List<AwLogPrinter> printers =new ArrayList<>();
    private static AwLogManager instance;

    private static AwLogThreadFormatter threadFormatter = new AwLogThreadFormatter();
    private static AwLogStackTraceFormatter stackTraceFormatter = new AwLogStackTraceFormatter();


    private AwLogManager(AwLogConfig config, AwLogPrinter[] printers) {
        this.logConfig = config;
        this.printers = new ArrayList<>(Arrays.asList(printers));
        if ( this.printers.size() == 0) {
            this.printers.add(new AWConsoleLogPrinter());
        }
    }

    public static void init(AwLogConfig config, AwLogPrinter... printers) {
        instance = new AwLogManager(config, printers);
    }


    public static AwLogManager getInstance() {
        return instance;
    }

    public static AwLogThreadFormatter getDefaultThreadFormatter() {
        return threadFormatter;
    }

    public static AwLogStackTraceFormatter getDefaultStackTraceFormatter() {
        return stackTraceFormatter;
    }


    public AwLogConfig getConfig() {
        return logConfig;
    }

    public List<AwLogPrinter> getPrinters() {
        return printers;
    }


}
