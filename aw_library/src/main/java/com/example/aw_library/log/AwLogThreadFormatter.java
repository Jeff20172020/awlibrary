package com.example.aw_library.log;

public class AwLogThreadFormatter implements AwLogFormatter<Thread> {


    @Override
    public String format(Thread data) {
        return "Thread----" + data.getName();
    }
}
