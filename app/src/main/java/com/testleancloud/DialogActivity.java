package com.testleancloud;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;

import com.base.BaseActivity;
import com.util.Utils;
import com.widget.dialog.InputDialog;
import com.widget.dialog.MessageDialog;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_dialog)
public class DialogActivity extends BaseActivity {

    @InjectView(R.id.msg_dialog_btn)
    private Button msgDialogBtn;

    @InjectView(R.id.input_dialog_btn)
    private Button inputDialogBtn;

    private MessageDialog messageDialog;
    private InputDialog inputDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageDialog = new MessageDialog(this);
        inputDialog = new InputDialog(this);
    }

    @Override
    protected void setListener() {
        msgDialogBtn.setOnClickListener(this);
        inputDialogBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.msg_dialog_btn:
                msgDialog();
                break;
            case R.id.input_dialog_btn:
                inputDialog();
                break;
        }
    }

    private void inputDialog() {
        inputDialog.setTitle("手机号");
        inputDialog.setHint("请输入手机号");
        inputDialog.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputDialog.setMaxLength(11);
        inputDialog.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = inputDialog.getText();
                Utils.showToast(DialogActivity.this, text);
            }
        });
        inputDialog.setCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Utils.showToast(DialogActivity.this, "取消dialog");
            }
        });
        inputDialog.show();
    }

    private void msgDialog() {
        messageDialog.setTitle("测试Message");
        messageDialog.setMessage("Android是一种基于Linux的自由及开放源代码的操作系统，主要使用于移动设备，如智能手机和平板电脑，由Google公司和开放手机联盟领导及开发。");
        messageDialog.setPositiveText("点我");
        messageDialog.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showToast(DialogActivity.this, "我被点了!");
            }
        });
        messageDialog.show();
    }
}
