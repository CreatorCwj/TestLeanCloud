package com.testleancloud;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.base.BaseActivity;
import com.model.User;
import com.util.Utils;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_user)
public class UserActivity extends BaseActivity {

    @InjectView(R.id.setUsername)
    private EditText setUsername;

    @InjectView(R.id.setPassword)
    private EditText setPassword;

    @InjectView(R.id.setEmail)
    private EditText setEmail;

    @InjectView(R.id.setDisplayname)
    private EditText setDisplayname;

    @InjectView(R.id.setMobilePhone)
    private EditText setMobilePhone;

    @InjectView(R.id.setCode)
    private EditText setCode;

    @InjectView(R.id.signUpUser)
    private Button signUpUser;

    @InjectView(R.id.loginUser)
    private Button loginUser;

    @InjectView(R.id.logoutUser)
    private Button logoutUser;

    @InjectView(R.id.updUsername)
    private Button updUsername;

    @InjectView(R.id.updPassword)
    private Button updPassword;

    @InjectView(R.id.resetPwd)
    private Button resetPwd;

    @InjectView(R.id.updEmail)
    private Button updEmail;

    @InjectView(R.id.setMobile)
    private Button setMobile;

    @InjectView(R.id.signMobile)
    private Button signMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 设置手机号,等待验证码(注册时可用,重置手机号往往应该先验证一下当前的(主动发送一次验证码到当前手机号))
     */
    public void setMobile() {
        final String mobile = setMobilePhone.getText().toString();
        User user = AVUser.getCurrentUser(User.class);
        if (TextUtils.isEmpty(mobile) || mobile.length() != 11 || user == null || !user.isAuthenticated())
            return;
        showProgressDialog("设置手机号...");
        user.setMobilePhoneNumber(mobile);//先设置手机号
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    AVUser.requestMobilePhoneVerifyInBackground(mobile, new RequestMobileCodeCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                Utils.showToast(UserActivity.this, "已发送验证码短信,请验证!");
                            } else {
                                textView.setText(e.getMessage());
                            }
                            dismissProgressDialog();
                        }
                    });
                } else {
                    textView.setText(e.getMessage());
                    dismissProgressDialog();
                }
            }
        });
    }

    /**
     * 验证手机号
     */
    public void signMobile() {
        String code = setCode.getText().toString();
        AVUser user = AVUser.getCurrentUser();
        if (TextUtils.isEmpty(code) || user == null || !user.isAuthenticated())
            return;
        showProgressDialog("正在验证手机号");
        AVUser.verifyMobilePhoneInBackground(code, new AVMobilePhoneVerifyCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Utils.showToast(UserActivity.this, "手机号验证成功!");
                } else {
                    textView.setText(e.getMessage());
                }
                dismissProgressDialog();
            }
        });
    }

    /**
     * 修改email(API上说的开启邮箱验证不知道怎么开？？？)
     */
    public void updEmail() {
        String email = setEmail.getText().toString();
        AVUser currentUser = AVUser.getCurrentUser();
        if (TextUtils.isEmpty(email) || currentUser == null || !currentUser.isAuthenticated())
            return;
        showProgressDialog("修改邮箱...");

        currentUser.setEmail(email);
        currentUser.saveInBackground(saveCallback);
    }

    /**
     * 重置密码(通过邮箱验证,会先检验邮箱正确与否),往往在忘记密码时候使用,所以无需注销
     */
    public void resetPwd() {
        final String email = setEmail.getText().toString();
        AVUser currentUser = AVUser.getCurrentUser();
        if (TextUtils.isEmpty(email) || currentUser == null || !currentUser.isAuthenticated())
            return;
        showProgressDialog("重置密码中...");
        AVUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Utils.showToast(UserActivity.this, "重置密码成功,请去" + email + "邮箱验证");
                } else {
                    textView.setText(e.getMessage());
                }
                dismissProgressDialog();
            }
        });
    }

    /**
     * 手动更改密码,更改后自动重新登陆即可同步session-token
     */
    public void updPassword() {
        final String password = setPassword.getText().toString();
        final AVUser currentUser = AVUser.getCurrentUser();
        if (TextUtils.isEmpty(password) || currentUser == null || !currentUser.isAuthenticated())
            return;
        showProgressDialog("正在修改密码...");
        currentUser.setPassword(password);//修改完密码后session-token会失效(再去修改用户信息时异常),重新登陆(重置了session-token)
        currentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {//修改成功
                    dismissProgressDialog();
                    showProgressDialog("正在重新登陆");
                    AVUser.logInInBackground(currentUser.getUsername(), password, new LogInCallback<User>() {
                        @Override
                        public void done(User newUser, AVException e) {
                            if (e == null) {
                                Utils.showToast(UserActivity.this, "重新登陆成功");
                                textView.setText("Username:" + newUser.getUsername() + "\n" + "DisplayName:" + newUser.getDisplayName() + "\n");
                            } else {
                                textView.setText(e.getMessage());
                            }
                            dismissProgressDialog();
                        }
                    }, User.class);
                } else {
                    textView.setText(e.getMessage());
                    dismissProgressDialog();
                }
            }
        });
    }

    /**
     * 更新用户名(login和signUp得到的user是授权的)
     */
    public void updUsername() {
        AVUser user = AVUser.getCurrentUser();
        String usernmae = setUsername.getText().toString();
        if (TextUtils.isEmpty(usernmae))
            return;
        if (user == null) {
            Utils.showToast(this, "当前无登陆用户");
            return;
        }
        if (!user.isAuthenticated()) {
            Utils.showToast(this, "当前登陆用户无授权,请重新登录");
            return;
        }
        showProgressDialog("正在更改username");
        user.setUsername(usernmae);//自己的用户信息登陆后可以修改
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    textView.setText("保存成功");
                } else {
                    textView.setText(e.getMessage());
                }
                AVUser user1 = AVUser.getCurrentUser();
                if (user1 != null) {
                    Log.i("Session-token", user1.getUsername() + user1.getSessionToken());
                }
                dismissProgressDialog();
            }
        });

        //查询得到的user都是没有授权的
        /*ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.getInBackground("WXIMPuEaUZ", new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser object, ParseException e) {
                if (object != null) {
                    object.setUsername("李某某");
                    object.saveInBackground(saveCallback);
                }
            }
        });*/
    }

    /**
     * 注册
     */
    public void signUp() {
        String username = setUsername.getText().toString();
        String password = setPassword.getText().toString();
        String email = setEmail.getText().toString();
        final String displayName = setDisplayname.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(email) || TextUtils.isEmpty(displayName))
            return;
        showProgressDialog("正在注册用户...");
        User user = new User();
        user.setUsername(username);//默认字段有getset方法
        user.setPassword(password);
        user.setEmail(email);
        user.setDisplayName(displayName);
        user.setMobilePhoneNumber(setMobilePhone.getText().toString());
        user.signUpInBackground(new SignUpCallback() {//注册
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Utils.showToast(UserActivity.this, "注册成功");
                    User user = AVUser.getCurrentUser(User.class);//注册或登陆后会自动存入用户信息直到注销,否则下回自动登录
                    if (user != null) {
                        textView.setText("Username:" + user.getUsername() + "\n" + "DisplayName:" + user.getDisplayName() + "\n");
                    }
                } else {//根据exception可以判断哪错了
                    textView.setText(e.getMessage());
                }
                dismissProgressDialog();
            }
        });
    }

    /**
     * 登陆
     */
    public void login() {
        String username = setUsername.getText().toString();
        String password = setPassword.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password))
            return;
        if (AVUser.getCurrentUser() != null) {
            showProgressDialog("当前用户为:" + AVUser.getCurrentUser().getUsername() + "\n正在切换用户...");
        } else {
            showProgressDialog("正在登陆...");
        }
        AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser u, AVException e) {
                if (e == null) {
                    Utils.showToast(UserActivity.this, "登陆成功");
                    User user = AVUser.getCurrentUser(User.class);
                    textView.setText("Username:" + user.getUsername() + "\n" + "DisplayName:" + user.getDisplayName() + "\n");
                    Log.i("Session-token", user.getUsername() + user.getSessionToken());
                } else {
                    textView.setText(e.getMessage());
                }
                dismissProgressDialog();
            }
        });
    }

    /**
     * 注销
     */
    public void logout() {
        if (AVUser.getCurrentUser() != null) {
            showProgressDialog("当前用户为:" + AVUser.getCurrentUser().getUsername() + "\n正在注销用户...");
        } else {
            Utils.showToast(UserActivity.this, "当前无用户登陆");//无用户登陆时注销也不会错
            return;
        }
        AVUser.logOut();//直接注销,清除缓存
        Utils.showToast(UserActivity.this, "注销成功");
//        AVUser.logOutInBackground(new LogOutCallback() {
//            @Override
//            public void done(AVException e) {
//                if (e == null) {//注销成功后currentUser为null
//                    Utils.showToast(UserActivity.this, "注销成功");
//                } else {
//                    textView.setText(e.getMessage());
//                }
//                dismissProgressDialog();
//            }
//        });
    }

    @Override
    protected void setListener() {
        signUpUser.setOnClickListener(this);
        loginUser.setOnClickListener(this);
        logoutUser.setOnClickListener(this);
        updUsername.setOnClickListener(this);
        updPassword.setOnClickListener(this);
        resetPwd.setOnClickListener(this);
        updEmail.setOnClickListener(this);
        setMobile.setOnClickListener(this);
        signMobile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpUser:
                signUp();
                break;
            case R.id.loginUser:
                login();
                break;
            case R.id.logoutUser:
                logout();
                break;
            case R.id.updUsername:
                updUsername();
                break;
            case R.id.updPassword:
                updPassword();
                break;
            case R.id.resetPwd:
                resetPwd();
                break;
            case R.id.updEmail:
                updEmail();
                break;
            case R.id.setMobile:
                setMobile();
                break;
            case R.id.signMobile:
                signMobile();
                break;
            default:
                break;
        }
    }

}
