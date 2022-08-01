package com.example.aw_library.log;

public class AwStackTraceUtil {

    public static StackTraceElement[] getCroppedRealStackTrace(StackTraceElement[] stackTraceElements, String ignorePackage,
                                                               int maxDepth) {


        return cropStackTrace(filterStackTrace(stackTraceElements, ignorePackage), maxDepth);
    }

    private static StackTraceElement[] filterStackTrace(StackTraceElement[] stackTraceElements, String ignorePackage) {

        int ignoreDepth = 0;
        int allDepth = stackTraceElements.length;
        String className;

        for (int depth = allDepth-1; depth >= 0; depth--) {
            className = stackTraceElements[depth].getClassName();
            if (ignorePackage != null && className.startsWith(ignorePackage)) {
                ignoreDepth += 1;

            }

        }

        int realDepth = allDepth - ignoreDepth;
        StackTraceElement[] destStackElements = new StackTraceElement[realDepth];
        System.arraycopy(stackTraceElements, ignoreDepth, destStackElements, 0, realDepth);
        return destStackElements;


    }


    private static StackTraceElement[] cropStackTrace(StackTraceElement[] rawElementArray, int maxDepth) {
        int realDepth = rawElementArray.length;

        if (maxDepth > 0) {
            realDepth = Math.min(realDepth, maxDepth);

        }

        StackTraceElement[] destElements = new StackTraceElement[realDepth];

        System.arraycopy(rawElementArray, 0, destElements, 0, realDepth);

        return destElements;


    }
}
