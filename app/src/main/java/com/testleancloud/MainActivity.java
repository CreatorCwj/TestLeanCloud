package com.testleancloud;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.base.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    private final String CLASS_NAME = "GameScore";

    private final String SCORE = "score";
    private final String PLAYER_NAME = "playerName";
    private final String IS_KP = "isKP";
    private final String SKILLS = "skills";

    @InjectView(R.id.addBtn)
    private Button addBtn;

    @InjectView(R.id.getBtn)
    private Button getBtn;

    @InjectView(R.id.getDataEditText)
    private EditText getDataEditText;

    @InjectView(R.id.setSkillsEditText)
    private EditText setSkillsEditText;

    @InjectView(R.id.setScoreEditText)
    private EditText setScoreEditText;

    @InjectView(R.id.setPlayerNameEditText)
    private EditText setPlayerNameEditText;

    @InjectView(R.id.setIsKPEditText)
    private EditText setIsKPEditText;

    @InjectView(R.id.updBtn)
    private Button updBtn;

    @InjectView(R.id.addArrayBtn)
    private Button addArrayBtn;

    @InjectView(R.id.delBtn)
    private Button delBtn;

    @InjectView(R.id.startRelation)
    private Button startRelation;

    @InjectView(R.id.startQuery)
    private Button startQuery;

    @InjectView(R.id.startUser)
    private Button startUser;

    @InjectView(R.id.startAuthor)
    private Button startAuthor;

    @InjectView(R.id.startFile)
    private Button startFile;

    @InjectView(R.id.startLocation)
    private Button startLocation;

    @InjectView(R.id.startVolley)
    private Button startVolley;

    @InjectView(R.id.startImageLoader)
    private Button startImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
        addBtn.setOnClickListener(this);
        getBtn.setOnClickListener(this);
        updBtn.setOnClickListener(this);
        addArrayBtn.setOnClickListener(this);
        delBtn.setOnClickListener(this);
        startRelation.setOnClickListener(this);
        startQuery.setOnClickListener(this);
        startUser.setOnClickListener(this);
        startAuthor.setOnClickListener(this);
        startFile.setOnClickListener(this);
        startLocation.setOnClickListener(this);
        startVolley.setOnClickListener(this);
        startImageLoader.setOnClickListener(this);
    }

    /**
     * 删除数据
     */
    private void deleteData() {
        String objId = getDataEditText.getText().toString();
        if (TextUtils.isEmpty(objId))
            return;
        showLoadingDialog();
        AVQuery<AVObject> query = AVQuery.getQuery(CLASS_NAME);//根据表名查询
        query.getInBackground(objId, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject object, AVException e) {//主线程进行
                if (e == null) {
                    object.deleteInBackground();//delete()不单独起线程,remove("")清空单个字段
                    object.saveInBackground(saveCallback);
                } else {
                    textView.setText(e.getMessage());
                }
                cancelLoadingDialog();
            }
        });
    }

    /**
     * 根据4个输入框内容增加表数据,4个输入框不能为空值
     */
    private void addData() {
        String score = setScoreEditText.getText().toString();
        String playerName = setPlayerNameEditText.getText().toString();
        String isKP = setIsKPEditText.getText().toString();
        String skills = setSkillsEditText.getText().toString();
        if (TextUtils.isEmpty(score) || TextUtils.isEmpty(playerName) || TextUtils.isEmpty(isKP) || TextUtils.isEmpty(skills))
            return;
        showLoadingDialog();
        AVObject po = new AVObject(CLASS_NAME);//放入指定表，第一次则创建表
        po.put(SCORE, Integer.parseInt(score));//属性自己定义类型并放入值,和Map一样
        po.put(PLAYER_NAME, playerName);
        po.put(IS_KP, Integer.parseInt(isKP) == 1);
        po.put(SKILLS, Arrays.asList(skills.split(",")));
        po.saveInBackground(saveCallback);
    }

    /**
     * 增加数组类型数据
     */
    private void addArrayData() {
        String objId = getDataEditText.getText().toString();
        if (TextUtils.isEmpty(objId))
            return;
        showLoadingDialog();
        AVQuery<AVObject> query = AVQuery.getQuery(CLASS_NAME);//根据表名查询
        query.getInBackground(objId, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject object, AVException e) {//主线程进行
                if (e == null) {
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < 5; ++i)
                        list.add("s" + i);
                    object.addAllUnique(SKILLS, list);
                    object.saveInBackground(saveCallback);//操作完一定要save
                } else {
                    textView.setText(e.getMessage());
                }
                cancelLoadingDialog();
            }
        });
    }

    /**
     * 根据objId输入框查找数据,objId不能为空值
     */
    private void getData() {
        String objId = getDataEditText.getText().toString();
        if (TextUtils.isEmpty(objId))
            return;
        showLoadingDialog();
        AVQuery<AVObject> query = AVQuery.getQuery(CLASS_NAME);//根据表名查询
        query.getInBackground(objId, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject object, AVException e) {//主线程进行
                if (e == null) {
                    textView.setText(getContent(object));
                } else {
                    textView.setText(e.getMessage());
                }
                cancelLoadingDialog();
            }
        });
    }

    /**
     * 获取数据后直接更新(用increment来增加计数器型的字段)
     */
    private void updData() {
        String objId = getDataEditText.getText().toString();
        if (TextUtils.isEmpty(objId))
            return;
        showLoadingDialog();
        AVQuery<AVObject> query = AVQuery.getQuery(CLASS_NAME);//根据表名查询
        query.getInBackground(objId, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject object, AVException e) {//主线程进行
                if (e == null) {
                    object.increment(SCORE, 3);
                    object.saveInBackground(saveCallback);
                } else {
                    textView.setText(e.getMessage());
                }
                cancelLoadingDialog();
            }
        });
    }

    /**
     * 按一定格式输出数据对象
     *
     * @param object
     * @return
     */
    private String getContent(AVObject object) {
        if (object == null)
            return "";

        //固定字段获取
        String objectId = object.getObjectId();
        Date updatedAt = object.getUpdatedAt();
        Date createdAt = object.getCreatedAt();

        //自定义字段获取
        int score = object.getInt(SCORE);
        String playerName = object.getString(PLAYER_NAME);
        if (playerName == null)
            playerName = "";
        boolean isKP = object.getBoolean(IS_KP);
        List skills = object.getList(SKILLS);
        if (skills == null)
            skills = new ArrayList();

        String content = "objectId:" + objectId + "\n"
                + "updatedAt:" + updatedAt + "\n"
                + "createdAt:" + createdAt + "\n"
                + "score:" + score + "\n"
                + "playerName:" + playerName + "\n"
                + "isKP:" + isKP + "\n"
                + "skills:" + skills.toString() + "\n";
        return content;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addBtn:
                addData();
                break;
            case R.id.getBtn:
                getData();
                break;
            case R.id.updBtn:
                updData();
                break;
            case R.id.addArrayBtn:
                addArrayData();
                break;
            case R.id.delBtn:
                deleteData();
                break;
            case R.id.startRelation:
                startActivity(new Intent(MainActivity.this, RelationActivity.class));
                break;
            case R.id.startQuery:
                startActivity(new Intent(MainActivity.this, QueryActivity.class));
                break;
            case R.id.startUser:
                startActivity(new Intent(MainActivity.this, UserActivity.class));
                break;
            case R.id.startAuthor:
                startActivity(new Intent(MainActivity.this, AuthorActivity.class));
                break;
            case R.id.startFile:
                startActivity(new Intent(MainActivity.this, FileActivity.class));
                break;
            case R.id.startLocation:
                startActivity(new Intent(MainActivity.this, LocationActivity.class));
                break;
            case R.id.startVolley:
                startActivity(new Intent(MainActivity.this, VolleyActivity.class));
                break;
            case R.id.startImageLoader:
                startActivity(new Intent(MainActivity.this, ImageLoaderActivity.class));
                break;
            default:
                break;
        }
    }
}
