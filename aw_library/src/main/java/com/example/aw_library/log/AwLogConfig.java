package com.example.aw_library.log;

import java.util.List;

public abstract class AwLogConfig {


    public AwLogConfig() {
    }

    public boolean enable() {
        return true;
    }

    public boolean includeThread() {
        return false;
    }

    public int getStackDepth() {
        return Integer.MAX_VALUE;
    }

    public String getGlobalTag() {
        return "AW_LOG";
    }

    public AwLogFormatter<Thread> getThreadFormatter() {
        return null;
    }

    public AwLogFormatter<StackTraceElement[]> getStackTraceFormatter() {
        return null;
    }

    public  JsonParser getJsonParser(){
        return null;
    }

    public List<AwLogPrinter> getPrinters(){
        return  null;
    }


    public int getMaxContentLength(){
        return 512;
    }


}
