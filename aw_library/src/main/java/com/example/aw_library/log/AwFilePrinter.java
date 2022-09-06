package com.example.aw_library.log;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.aw_library.log.model.AWLogModel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class AwFilePrinter implements AwLogPrinter {


    private static AwFilePrinter instance;
    private PrintWorker printWorker;
    private String logPath;
    private long retentionTime;
    private LogWriter logWriter;
    private static final ExecutorService THREAD_EXECUTOR = Executors.newSingleThreadExecutor();

    public static synchronized AwFilePrinter getInstance(String logPath, long retentionTime) {

        if (instance == null) {
            instance = new AwFilePrinter(logPath, retentionTime);
        }
        return instance;
    }

    public AwFilePrinter(String logPath, long retentionTime) {
        this.logPath = logPath;
        this.retentionTime = retentionTime;
        this.logWriter = new LogWriter();
        this.printWorker = new PrintWorker();
        clearExpiredFile();


    }

    @Override
    public void print(@NonNull AwLogConfig config, int level, String tag, String content) {
        Log.d("FilePrinter", "filePrint----" + content);

        if (!printWorker.isRunning()) {
            printWorker.start();
        }

        if (printWorker != null) {
            printWorker.put(new AWLogModel(tag, content, level, System.currentTimeMillis()));
        }


    }


    private void clearExpiredFile() {
        if (retentionTime <= 0) {
            return;
        }
        long currentTime = System.currentTimeMillis();
        File logDir = new File(logPath);
        File[] files = logDir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.lastModified() + retentionTime < currentTime) {
                file.delete();
            }
        }


    }

    class PrintWorker implements Runnable {

        private BlockingQueue<AWLogModel> block = new LinkedBlockingDeque<>();
        private volatile boolean running;

        public boolean isRunning() {
            synchronized (this) {
                return running;
            }
        }


        public void start() {
            synchronized (this) {
                THREAD_EXECUTOR.execute(this);
                running = true;
            }
        }

        public void put(AWLogModel model) {

                try {
                    block.put(model);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


        }

        @Override
        public void run() {
            AWLogModel model;
            while (true) {
                try {
                    model = block.take();
                    doPrint(model);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        private void doPrint(AWLogModel model) {
            if (logWriter != null) {
                String lastFileName = logWriter.preFileName;
                if (lastFileName == null) {
                    lastFileName = generateFileName();
                }
                if (logWriter.isReady()) {
                    logWriter.close();
                }
                if (!logWriter.ready(lastFileName)) {
                    return;
                }
                logWriter.append(model.getFlattenedLog());
            }
        }

        private String generateFileName() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            dateFormat.setTimeZone(TimeZone.getDefault());
            return dateFormat.format(new Date(System.currentTimeMillis()));
        }


    }

    class LogWriter {
        private String preFileName;
        private File logFile;
        private BufferedWriter bufferedWriter;

        boolean isReady() {
            return bufferedWriter != null;
        }

        boolean ready(String newFileName) {
            preFileName = newFileName;
            logFile = new File(logPath, preFileName);
            if (!logFile.exists()) {
                try {
                    File parent = logFile.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdir();
                    }

                    logFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    preFileName = null;
                    logFile = null;
                    return false;
                }
            }

            try {
                bufferedWriter = new BufferedWriter(new FileWriter(logFile, true));

            } catch (Exception exception) {
                preFileName = null;
                logFile = null;
                return false;
            }

            return true;
        }

        public boolean close() {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();

                    return false;

                } finally {
                    bufferedWriter = null;
                    preFileName = null;
                    logFile = null;
                }

            }

            return true;
        }

        public void append(String flattenedString) {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.write(flattenedString);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
