package com.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.testleancloud.R;
import com.widget.dialog.base.BaseDialog;

/**
 * Created by cwj on 16/2/6.
 * 自定义的加载对话框,可以设置显示文字
 */
public class LoadingDialog extends BaseDialog {

    private static final String DEFAULT_TEXT = "加载中...";

    private TextView textView;

    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected View onCreateView() {
        return LayoutInflater.from(context).inflate(R.layout.loading_dialog, null);
    }

    @Override
    protected void setProps() {
        super.setProps();
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onViewCreated(View view) {
        textView = (TextView) view.findViewById(R.id.loadingTextView);
    }

    /**
     * 自定义show方法
     */
    @Override
    public void show() {
        show(DEFAULT_TEXT);
    }

    public void show(String text) {
        textView.setText(text);
        dialog.show();
    }

}
