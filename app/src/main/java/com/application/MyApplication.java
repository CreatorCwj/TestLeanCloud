package com.application;

import android.app.Application;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;
import com.imageLoader.ImageLoader;
import com.model.City;
import com.model.Comment;
import com.model.GameScore;
import com.model.Place;
import com.model.Post;
import com.model.Student;
import com.model.TeamWork;
import com.model.User;
import com.testleancloud.MainActivity;
import com.testleancloud.R;
import com.util.Utils;
import com.volley.Network;

/**
 * Created by cwj on 15/11/25.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        Parse.enableLocalDatastore(this);//是否启动本地数据存储库,存储临时数据（不需要上传的数据和稍后可以进行同步的数据）
        //注册子类后不能再使用new,只能使用子类(因为表已经关联到子类了)
        initSubClass();//注册各个子类(要在init之前)
//        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
        AVOSCloud.initialize(this, getResources().getString(R.string.app_id), getResources().getString(R.string.app_key));
        initPush();//初始化推送
//        setDefaultACL();//可以设置默认的ACL，第二个参数是当前用户是否可以读写

        //Volley
        Network.initNetwork(this);
        //ImageLoader
        ImageLoader.initConfig(this, new ColorDrawable(Color.GRAY));
    }

    private void initPush() {
        //默认启动界面(也可以用subscribe/unsubscribe来订阅/退订某个频道(名字只能由26字母和数字构成)对应打开的界面,订阅要在保存installation前,退订后也要重新save一下installation)
        PushService.setDefaultPushCallback(getApplicationContext(), MainActivity.class);
        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Utils.showToast(getApplicationContext(), "推送初始化成功");
                    //获取到唯一的注册ID，卸载后id也删除(可以理解为存储在app包的一个数据,不卸载就一直用一个)
                    Log.i("InstallationId", AVInstallation.getCurrentInstallation().getInstallationId());
//                    String installationId = AVInstallation.getCurrentInstallation().getInstallationId();
                } else {
                    Utils.showToast(getApplicationContext(), "推送初始化失败");
                }
            }
        });
    }

    /*private void setDefaultACL() {
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(false);
        ParseACL.setDefaultACL(acl, true);
    }*/

    private void initSubClass() {
        AVUser.alwaysUseSubUserClass(User.class);
        AVObject.registerSubclass(Comment.class);
        AVObject.registerSubclass(Post.class);
        AVObject.registerSubclass(GameScore.class);
        AVObject.registerSubclass(City.class);
        AVObject.registerSubclass(Student.class);
        AVObject.registerSubclass(TeamWork.class);
        AVObject.registerSubclass(Place.class);
    }
}
