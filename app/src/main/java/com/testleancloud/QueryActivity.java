package com.testleancloud;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CountCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.base.BaseActivity;
import com.model.Comment;
import com.model.GameScore;
import com.model.Post;
import com.model.Student;
import com.model.TeamWork;

import java.util.Arrays;
import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_query)
public class QueryActivity extends BaseActivity {

    @InjectView(R.id.inputPlayerName)
    private EditText inputPlayerName;

    @InjectView(R.id.queryByPlayerName)
    private Button queryByPlayerName;

    @InjectView(R.id.queryContainedIn)
    private Button queryContainedIn;

//    @InjectView(R.id.inputStudentId)
//    private EditText inputStudentId;

    @InjectView(R.id.queryMatchesKey)
    private Button queryMatchesKey;

    @InjectView(R.id.querySelectKeys)
    private Button querySelectKeys;

    @InjectView(R.id.queryArrayAndString)
    private Button queryArrayAndString;

    @InjectView(R.id.queryRelative)
    private Button queryRelative;

    @InjectView(R.id.queryCount)
    private Button queryCount;

    @InjectView(R.id.queryOr)
    private Button queryOr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
        queryByPlayerName.setOnClickListener(this);
        queryContainedIn.setOnClickListener(this);
        queryMatchesKey.setOnClickListener(this);
        querySelectKeys.setOnClickListener(this);
        queryArrayAndString.setOnClickListener(this);
        queryRelative.setOnClickListener(this);
        queryCount.setOnClickListener(this);
        queryOr.setOnClickListener(this);
    }

    /**
     * 混合查询
     */
    public void queryOr() {
        showProgressDialog();
        AVQuery<GameScore> queryHighScore = AVQuery.getQuery(GameScore.class);
        queryHighScore.whereGreaterThanOrEqualTo(GameScore.SCORE, 90);
        AVQuery<GameScore> queryLowScore = AVQuery.getQuery(GameScore.class);
        queryLowScore.whereLessThan(GameScore.SCORE, 80);
        List<AVQuery<GameScore>> list = Arrays.asList(queryHighScore, queryLowScore);
        AVQuery<GameScore> result = AVQuery.or(list);
        result.findInBackground(new FindCallback<GameScore>() {
            @Override
            public void done(List<GameScore> objects, AVException e) {
                StringBuilder sb = new StringBuilder("");
                for (GameScore gameScore : objects)
                    sb.append(gameScore.text());
                textView.setText(sb.toString());
                dismissProgressDialog();
            }
        });
    }

    /**
     * 计数查询
     */
    public void queryCount() {
        showProgressDialog();
        AVQuery<Comment> query = AVQuery.getQuery(Comment.class);
        try {
            query.whereEqualTo(Comment.COMMENT_POST, AVObject.createWithoutData(Post.class, "yMKvhJ2Oc0"));
        } catch (AVException e) {
            e.printStackTrace();
            return;
        }
        query.countInBackground(new CountCallback() {
            @Override
            public void done(int count, AVException e) {
                textView.setText("Count:" + count);
                dismissProgressDialog();
            }
        });
    }

    /**
     * 关系型查询
     */
    public void queryRelative() {
        showProgressDialog();
        AVQuery<Post> postQuery = AVQuery.getQuery(Post.class);
        postQuery.whereStartsWith(Post.POST_TITLE, "Do");
        AVQuery<Comment> commentQuery = AVQuery.getQuery(Comment.class);
        //直接比较关联的对象是否匹配,DoseNotMatches(不匹配)/equalTo(比较单个)
        commentQuery.whereMatchesQuery(Comment.COMMENT_POST, postQuery);
        //取回的数据包含指定关联对象(该对象不用再fetch了),可用路径来进行深层次include关联对象(post.xx.xx...)
        commentQuery.include(Comment.COMMENT_POST);
//        commentQuery.include(Comment.COMMENT_LIKES);//relation型数据不可以直接include，还是要获取后手动抓取
        commentQuery.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> objects, AVException e) {
                StringBuilder sb = new StringBuilder("");
                for (Comment comment : objects) {
                    sb.append(comment.text() + comment.getPost().text() + "\n");
                }
                textView.setText(sb.toString());
                dismissProgressDialog();
            }
        });
    }

    /**
     * 查询数组值和字符串值
     */
    public void queryArrayAndString() {
        showProgressDialog();
        AVQuery<GameScore> query = AVQuery.getQuery(GameScore.class);
        query.whereContainsAll(GameScore.SKILLS, Arrays.asList("s0", "s4"));//查到的数组值中包含这些
        query.whereStartsWith(GameScore.PLAYER_NAME, "L");//查到的字符串值是以其开头的
        query.findInBackground(new FindCallback<GameScore>() {
            @Override
            public void done(List<GameScore> objects, AVException e) {
                StringBuilder sb = new StringBuilder("");
                for (GameScore gameScore : objects) {
                    sb.append(gameScore.text());
                }
                textView.setText(sb.toString());
                dismissProgressDialog();
            }
        });
    }

    /**
     * 查询指定列
     */
    public void querySelectKeys() {
        showProgressDialog();
        AVQuery<GameScore> query = AVQuery.getQuery(GameScore.class);
        query.selectKeys(Arrays.asList(GameScore.PLAYER_NAME, GameScore.SCORE));
        query.findInBackground(new FindCallback<GameScore>() {//获取多个
            @Override
            public void done(List<GameScore> objects, AVException e) {
                StringBuilder sb = new StringBuilder("");
                for (GameScore gameScore : objects) {
                    sb.append(gameScore.getPlayerName() + ":" + gameScore.getScore() + "\n");
                }
                textView.setText(sb.toString());
                //再获取其他字段
                AVObject.fetchAllIfNeededInBackground((List) objects, new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> objects, AVException e) {
                        StringBuilder sb = new StringBuilder("");
                        for (AVObject gameScore : objects) {
                            sb.append(((GameScore) gameScore).text());
                        }
                        textView.setText(sb.toString());
                        dismissProgressDialog();
                    }
                });
            }
        });
        /*query.getInBackground("TK5sPLCtGO", new GetCallback<GameScore>() {//获取单个
            @Override
            public void done(GameScore object, AVException e) {
                textView.setText(object.getPlayerName() + ":" + object.getScore());
                //再获取其他字段
                object.fetchIfNeededInBackground(new GetCallback<GameScore>() {
                    @Override
                    public void done(GameScore object, AVException e) {
                        textView.setText(object.text());
                        dismissProgressDialog();
                    }
                });
            }
        });*/
    }

    /**
     * 多表连接查询
     */
    public void queryMatchesKey() {
        showProgressDialog();
        AVQuery<TeamWork> teamQuery = AVQuery.getQuery(TeamWork.class);
        teamQuery.whereGreaterThanOrEqualTo(TeamWork.TEAMWORK_SCORE, 80);
        AVQuery<Student> studentQuery = AVQuery.getQuery(Student.class);
        //某一字段与目标一组查询结果里的某一字段相等就选上(相当于多表连接查询),DoseNotMatches...是不相等的
        studentQuery.whereMatchesKeyInQuery(Student.STUDENT_HOMETOWN, TeamWork.TEAMWORK_CITY, teamQuery);
        studentQuery.findInBackground(new FindCallback<Student>() {
            @Override
            public void done(List<Student> objects, AVException e) {
                if (e == null) {
                    StringBuilder sb = new StringBuilder("");
                    for (Student student : objects) {
                        sb.append(student.text());
                    }
                    textView.setText(sb.toString());
                } else {
                    textView.setText(e.getMessage());
                }
                dismissProgressDialog();
            }
        });
    }

    /**
     * 查询包含在多值的
     */
    public void queryContainedIn() {
        String playerName = inputPlayerName.getText().toString();
        if (TextUtils.isEmpty(playerName))
            return;
        showProgressDialog();
        AVQuery<GameScore> query = AVQuery.getQuery(GameScore.class);
        //某一字段值等于指定集合里的值的(还有notContained等),Where(NOT)Exists是(不)包含某一字段的数据
        query.whereContainedIn(GameScore.PLAYER_NAME, Arrays.asList(playerName.split(",")));
        query.findInBackground(new FindCallback<GameScore>() {
            @Override
            public void done(List<GameScore> objects, AVException e) {
                if (e == null) {
                    StringBuilder sb = new StringBuilder("");
                    for (GameScore gameScore : objects) {
                        sb.append(gameScore.text());
                    }
                    textView.setText(sb.toString());
                    dismissProgressDialog();
                } else {
                    textView.setText(e.getMessage());
                    dismissProgressDialog();
                }
            }
        });
    }

    /**
     * 基本查询语句
     */
    public void queryByPlayerName() {
        String playerName = inputPlayerName.getText().toString();
        if (TextUtils.isEmpty(playerName))
            return;
        showProgressDialog();
        AVQuery<GameScore> query = AVQuery.getQuery(GameScore.class);
        //可设置多个筛选条件来筛选(相当于多个And)
        query.whereNotEqualTo(GameScore.PLAYER_NAME, playerName);//(not)equal以某一字段是否等于某一值来筛选
        query.whereGreaterThanOrEqualTo(GameScore.SCORE, 80);//大于小于(等于)
        query.setLimit(5);//设置查询数据个数上限
        query.setSkip(1);//跳过前两个查找(分页可用)
        query.orderByDescending(GameScore.SCORE);//asc/des为升序降序,也可addAsc或Des添加多级排序
        query.findInBackground(new FindCallback<GameScore>() {//使用getFirst或getFirstInBackground为返回第一个数据
            @Override
            public void done(List<GameScore> objects, AVException e) {
                if (e == null) {
                    StringBuilder sb = new StringBuilder("");
                    for (GameScore gameScore : objects) {
                        sb.append(gameScore.text());
                    }
                    textView.setText(sb.toString());
                    dismissProgressDialog();
                } else {
                    textView.setText(e.getMessage());
                    dismissProgressDialog();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.queryByPlayerName:
                queryByPlayerName();
                break;
            case R.id.queryContainedIn:
                queryContainedIn();
                break;
            case R.id.queryMatchesKey:
                queryMatchesKey();
                break;
            case R.id.querySelectKeys:
                querySelectKeys();
                break;
            case R.id.queryArrayAndString:
                queryArrayAndString();
                break;
            case R.id.queryRelative:
                queryRelative();
                break;
            case R.id.queryCount:
                queryCount();
                break;
            case R.id.queryOr:
                queryOr();
                break;
            default:
                break;
        }
    }

    /**
     * 保存回调
     */
    private SaveCallback saveCallback = new SaveCallback() {
        @Override
        public void done(AVException e) {
            if (e == null) {
                textView.setText("保存成功");
            } else {
                textView.setText(e.getMessage());
            }
            dismissProgressDialog();
        }
    };
}
