package com.testleancloud;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVACL;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.base.BaseActivity;
import com.model.Post;

import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_author)
public class AuthorActivity extends BaseActivity {

    @InjectView(R.id.setPostTitle)
    private EditText setPostTitle;

    @InjectView(R.id.createUserPost)
    private Button createUserPost;

    @InjectView(R.id.createUsersPost)
    private Button createUsersPost;

    @InjectView(R.id.createPostByDefaultACL)
    private Button createPostByDefaultACL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
        createUserPost.setOnClickListener(this);
        createUsersPost.setOnClickListener(this);
        createPostByDefaultACL.setOnClickListener(this);
    }

    /**
     * 设置默认的ACL
     */
    public void addPostByDefaultACL() {
        String title = setPostTitle.getText().toString();
        if (TextUtils.isEmpty(title))
            return;
        showProgressDialog();
        //在application里可以设值默认的ACL来统一整个app的ACL方式
        Post post = new Post();
        post.setTitle(title);
        post.saveInBackground(saveCallback);
    }

    /**
     * 指定user的读写权限(setPublicRead/Write为设置全部用户的读写权限)
     */
    public void addUsersReadAndWritePost() {
        final String title = setPostTitle.getText().toString();
        if (TextUtils.isEmpty(title))
            return;
        showProgressDialog();
        AVQuery<AVUser> query = AVUser.getQuery();
        query.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> users, AVException e) {
                AVACL acl = new AVACL();
                for (AVUser user : users) {
                    acl.setReadAccess(user, true);
                    acl.setWriteAccess(user, false);
                }

                Post post = new Post();
                post.setTitle(title);
                post.setACL(acl);
                post.saveInBackground(saveCallback);
            }
        });
    }

    /**
     * 为当前用户增加一个完全私有的post
     */
    public void addUserAuthorPost() {
        String title = setPostTitle.getText().toString();
        AVUser user = AVUser.getCurrentUser();
        if (TextUtils.isEmpty(title) || user == null)
            return;
        showProgressDialog();
        Post post = new Post();
        post.setTitle(title);
        post.setACL(new AVACL(user));//仅有当前用户可以读写的权限
        post.saveInBackground(saveCallback);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createUserPost:
                addUserAuthorPost();
                break;
            case R.id.createUsersPost:
                addUsersReadAndWritePost();
                break;
            case R.id.createPostByDefaultACL:
                addPostByDefaultACL();
                break;
            default:
                break;
        }
    }
}
