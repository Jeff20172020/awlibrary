package com.example.aw_library.log;

public interface AwLogFormatter<T> {

    String format(T data);
}
