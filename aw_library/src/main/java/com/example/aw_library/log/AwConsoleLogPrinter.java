package com.example.aw_library.log;

import android.util.Log;

import androidx.annotation.NonNull;

public class AwConsoleLogPrinter implements AwLogPrinter {
    @Override
    public void print(@NonNull AwLogConfig config, int level, String tag, String content) {
        if (content != null) {
            int maxContentLength = config.getMaxContentLength();
            int rowCount = content.length() / maxContentLength;

            if (rowCount > 0) {

                int index = 0;
                for (int i = 0; i < rowCount; i++) {

                    Log.println(level, tag, content.substring(index, index + maxContentLength));

                    index += maxContentLength;

                }
                if (index != content.length()) {
                    Log.println(level, tag, content.substring(index));
                }

            } else {
                Log.println(level, tag, content);

            }


        }


    }
}
