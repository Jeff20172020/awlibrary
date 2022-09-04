package com.example.aw_library.log;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aw_library.R;
import com.example.aw_library.log.model.AWLogModel;

import java.util.ArrayList;
import java.util.List;

public class AwViewPrinter implements AwLogPrinter {
    private RecyclerView recyclerView;
    private LogAdapter logAdapter;
    private AwViewPrinterProvider viewPrinterProvider;

    public AwViewPrinter(Activity activity) {
        FrameLayout rootView = activity.findViewById(android.R.id.content);
        recyclerView = new RecyclerView(activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        logAdapter = new LogAdapter(LayoutInflater.from(recyclerView.getContext()));
        recyclerView.setAdapter(logAdapter);
        recyclerView.setBackgroundColor(Color.BLUE);
        viewPrinterProvider = new AwViewPrinterProvider(recyclerView, rootView);

    }

    public AwViewPrinterProvider getViewPrinterProvider() {
        return viewPrinterProvider;

    }

    @Override
    public void print(@NonNull AwLogConfig config, int level, String tag, String content) {
        logAdapter.addLog(new AWLogModel(
                tag,content,level,System.currentTimeMillis()
        ));
        recyclerView.smoothScrollToPosition(logAdapter.getItemCount()-1);
    }

    public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {
        LayoutInflater inflater;
        private List<AWLogModel> logList = new ArrayList<>();

        public LogAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        public void addLog(AWLogModel model) {
            logList.add(model);
           notifyDataSetChanged();
        }

        @NonNull
        @Override
        public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.log_item, parent, false);
            return new LogViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
            AWLogModel logModel = logList.get(position);
            int logColor = getHighlightColor(logModel.level);

            holder.tagView.setTextColor(logColor);
            holder.messageView.setTextColor(logColor);
            holder.tagView.setText(logModel.getFlattened());
            holder.messageView.setText(logModel.log);
        }

        public int getHighlightColor(@LogLevel.LEVEL int level) {
            int highlightColor;
            switch (level) {

                case LogLevel.E:
                    highlightColor = 0xffff6b68;
                    break;
                case LogLevel.D:
                    highlightColor = 0xffffffff;
                    break;
                case LogLevel.W:
                    highlightColor = 0xffbbb529;
                    break;
                case LogLevel.I:
                    highlightColor = 0xff6a8759;
                    break;
                case LogLevel.V:
                    highlightColor = 0xffbbbbbb;
                    break;
                default:
                    highlightColor = 0xffffff00;
                    break;

            }

            return highlightColor;
        }

        @Override
        public int getItemCount() {
            return logList.size();
        }

        class LogViewHolder extends RecyclerView.ViewHolder {

            TextView tagView;
            TextView messageView;

            public LogViewHolder(@NonNull View itemView) {
                super(itemView);
                tagView = itemView.findViewById(R.id.tv_tag);
                messageView = itemView.findViewById(R.id.tv_message);
            }
        }
    }
}
