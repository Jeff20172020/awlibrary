package com.example.aw_library.log;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aw_library.util.AWDisplayUtil;

import org.jetbrains.annotations.NotNull;

public class AwViewPrinterProvider {
    private RecyclerView recyclerView;
    private FrameLayout rootView;
    private FrameLayout logView;
    private View floatingView;
    private boolean isOpen;

    public static final String TAG_FLOATING_VIEW = "TAG_FLOATING_VIEW";
    public static final String TAG_LOG_VIEW = "TAG_LOG_VIEW";

    public AwViewPrinterProvider(@NotNull RecyclerView recyclerView, @NotNull FrameLayout rootView) {
        this.recyclerView = recyclerView;
        this.rootView = rootView;
    }

    public void showFloatingView() {
        if (rootView.findViewWithTag(TAG_FLOATING_VIEW) != null) {
            return;
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutParams.gravity = Gravity.BOTTOM | Gravity.END;
        layoutParams.bottomMargin = AWDisplayUtil.dp2px(100, rootView.getResources());
        View floatingView = generateFloatingView();

        floatingView.setTag(TAG_FLOATING_VIEW);
        floatingView.setBackgroundColor(Color.BLACK);
        floatingView.setAlpha(0.8f);

        rootView.addView(floatingView, layoutParams);


    }

    public void closeFloatingView() {
        rootView.removeView(generateFloatingView());
    }

    private View generateFloatingView() {
        if (floatingView != null) {
            return floatingView;
        }

        TextView textView = new TextView(rootView.getContext());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOpen) {
                    showLogView();
                }
            }
        });

        textView.setText("AWLog");

        return floatingView = textView;
    }

    private void showLogView() {
        if (rootView.findViewWithTag(TAG_LOG_VIEW) != null) {
            return;
        }

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AWDisplayUtil.dp2px(160, rootView.getResources()));

        params.gravity = Gravity.BOTTOM;
        View logView = generateLogView();
        logView.setTag(TAG_LOG_VIEW);

        rootView.addView(logView, params);
        isOpen = true;


    }

    private View generateLogView() {
        if (logView != null) {
            return logView;
        }

        FrameLayout.LayoutParams logLayoutParams =new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        FrameLayout logView = new FrameLayout(rootView.getContext());
        logView.setBackgroundColor(Color.BLACK);

        logView.addView(recyclerView,logLayoutParams);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.END;
        TextView closeView = new TextView(rootView.getContext());
        closeView.setText("close");
        closeView.setTextColor(Color.WHITE);
        closeView.setLayoutParams(params);
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeLogView();
            }
        });

        logView.addView(closeView);


        return this.logView = logView;
    }

    public void closeLogView() {
        isOpen = false;
        rootView.removeView(generateLogView());
    }

}
