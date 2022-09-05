package com.example.aw_library.log;

public class AwLogStackTraceFormatter implements AwLogFormatter<StackTraceElement[]> {
    @Override
    public String format(StackTraceElement[] data) {
        if (data != null && data.length > 0) {

            if (data.length == 1) {

                return "\t-" + data[0].toString();

            } else {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < data.length; i++) {

                    if (i == 0) {
                        builder.append("\nstackTrace--")
                                .append("\n")
                                .append("\t-")
                                .append(data[i].toString())
                                .append("\n");
                    } else if (i != data.length - 1) {
                        builder.append("\t-")
                                .append(data[i].toString())
                                .append("\n");
                    } else {

                        builder.append(data[i].toString())
                                .append("\n");

                    }

                }

                return builder.toString();


            }


        } else {
            return null;
        }
    }
}
