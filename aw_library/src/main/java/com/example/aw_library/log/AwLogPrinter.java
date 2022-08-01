package com.example.aw_library.log;

import androidx.annotation.NonNull;

public interface AwLogPrinter {

    void print(@NonNull AwLogConfig config, @LogLevel.LEVEL int level,String tag, String content);
}
