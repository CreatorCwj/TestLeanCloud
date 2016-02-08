package com.testleancloud;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.ProgressCallback;
import com.base.BaseActivity;
import com.model.Post;

import java.io.ByteArrayOutputStream;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_file)
public class FileActivity extends BaseActivity {

    @InjectView(R.id.imageView)
    private ImageView imageView;

    @InjectView(R.id.setPostId)
    private EditText setPostId;

    @InjectView(R.id.addFile)
    private Button addFile;

    @InjectView(R.id.getFile)
    private Button getFile;

    @InjectView(R.id.addImageFile)
    private Button addImageFile;

    @InjectView(R.id.getImageFile)
    private Button getImageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void addImageFile() {
        String postId = setPostId.getText().toString();
        if (TextUtils.isEmpty(postId))
            return;
        showLoadingDialog("上传图片...");

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_contact);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, baos);
        AVFile file = new AVFile("image.png", baos.toByteArray());

        Post post = null;
        try {
            post = Post.createWithoutData(Post.class, postId);
        } catch (AVException e) {
            e.printStackTrace();
            return;
        }
        post.setFile(file);
        post.saveInBackground(saveCallback);
    }

    public void getImageFile() {
        String postId = setPostId.getText().toString();
        if (TextUtils.isEmpty(postId))
            return;
        showLoadingDialog("获取图片...");
        AVQuery<Post> query = AVQuery.getQuery(Post.class);
        query.getInBackground(postId, new GetCallback<Post>() {
            @Override
            public void done(Post object, AVException e) {
                if (e == null) {
                    AVFile file = object.getFile();
                    if (file != null) {
                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, AVException e) {
                                if (e == null) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    imageView.setImageBitmap(bitmap);
                                } else {
                                    textView.setText(e.getMessage());
                                }
                                cancelLoadingDialog();
                            }
                        }, new ProgressCallback() {
                            @Override
                            public void done(Integer percentDone) {
                                textView.setText(percentDone + "%");
                            }
                        });
                    } else {
                        cancelLoadingDialog();
                    }
                } else {
                    textView.setText(e.getMessage());
                    cancelLoadingDialog();
                }
            }
        });
    }

    /**
     * 获取file展示
     */
    public void getFile() {
        String postId = setPostId.getText().toString();
        if (TextUtils.isEmpty(postId))
            return;
        showLoadingDialog("获取文件...");
        AVQuery<Post> query = AVQuery.getQuery(Post.class);
        query.getInBackground(postId, new GetCallback<Post>() {
            @Override
            public void done(Post object, AVException e) {
                if (e == null) {
                    AVFile file = object.getFile();
                    if (file != null) {
                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, AVException e) {
                                if (e == null) {
                                    textView.setText(new String(data));
                                } else {
                                    textView.setText(e.getMessage());
                                }
                                cancelLoadingDialog();
                            }
                        }, new ProgressCallback() {//有时会从cache里取,cache里取时不会调用此方法
                            @Override
                            public void done(Integer percentDone) {
                                Log.i("file-progress", "" + percentDone);
                                textView.setText(percentDone.intValue() + "%");
                            }
                        });
                    } else {
                        cancelLoadingDialog();
                    }
                } else {
                    textView.setText(e.getMessage());
                    cancelLoadingDialog();
                }
            }
        });
    }

    /**
     * 增加一个file到指定post
     */
    public void addFile() {
        String postId = setPostId.getText().toString();
        if (TextUtils.isEmpty(postId))
            return;
        showLoadingDialog("上传文件...");

        String fileData = "I am a file data in the cloud!!!";
        AVFile file = new AVFile("file.txt", fileData.getBytes());
        //貌似不save也一样。。。
        /*file.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Utils.showToast(FileActivity.this, "文件上传到云端!");
                } else {
                    textView.setText(e.getMessage());
                }
                dismissLoadingDialog();
            }
        }, new ProgressCallback() {
            @Override
            public void done(Integer percentDone) {
                Log.i("file-progress", "" + percentDone);
            }
        });*/

        Post post = null;
        try {
            post = Post.createWithoutData(Post.class, postId);
        } catch (AVException e) {
            e.printStackTrace();
            return;
        }
        post.setFile(file);
        post.saveInBackground(saveCallback);
    }

    @Override
    protected void setListener() {
        addFile.setOnClickListener(this);
        getFile.setOnClickListener(this);
        getImageFile.setOnClickListener(this);
        addImageFile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addFile:
                addFile();
                break;
            case R.id.getFile:
                getFile();
                break;
            case R.id.addImageFile:
                addImageFile();
                break;
            case R.id.getImageFile:
                getImageFile();
                break;
            default:
                break;
        }
    }
}
