package com.testleancloud;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.base.BaseActivity;
import com.model.Comment;
import com.model.Post;

import java.util.Date;
import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_relation)
public class RelationActivity extends BaseActivity {

    private final String POST_CLASS_NAME = "Post";
    private final String COMMENT_CLASS_NAME = "Comment";

    private final String POST_TITLE = "title";
    private final String COMMENT_CONTENT = "content";
    private final String COMMENT_POST = "post";
    private final String COMMENT_LIKES = "likes";

    @InjectView(R.id.addPostBtn)
    private Button addPostBtn;

    @InjectView(R.id.addCommentBtn)
    private Button addCommentBtn;

    @InjectView(R.id.setPostTitle)
    private EditText setPostTitle;

    @InjectView(R.id.setPostId)
    private EditText setPostId;

    @InjectView(R.id.setCommentContent)
    private EditText setCommentContent;

    @InjectView(R.id.setCommentId)
    private EditText setCommentId;

    @InjectView(R.id.getCommentBtn)
    private Button getCommentBtn;

    @InjectView(R.id.addRelationsBtn)
    private Button addRelationsBtn;

    @InjectView(R.id.getRelationsBtn)
    private Button getRelationsBtn;

    @InjectView(R.id.getCommentBySubClassBtn)
    private Button getCommentBySubClassBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
        addPostBtn.setOnClickListener(this);
        addCommentBtn.setOnClickListener(this);
        getCommentBtn.setOnClickListener(this);
        addRelationsBtn.setOnClickListener(this);
        getRelationsBtn.setOnClickListener(this);
        getCommentBySubClassBtn.setOnClickListener(this);
    }

    /**
     * 通过subclass拿到comment，展示其关联数据内容
     */
    private void getCommentBySubClass() {
        final String commentId = setCommentId.getText().toString();
        if (TextUtils.isEmpty(commentId))
            return;
        showProgressDialog();
        AVQuery<Comment> query = AVQuery.getQuery(Comment.class);
        query.getInBackground(commentId, new GetCallback<Comment>() {
            @Override
            public void done(final Comment commentObject, AVException e) {
                if (e == null) {
                    AVRelation<Post> postRelation = commentObject.getLikes();
                    //得到query后也不会有数据，需要调用findInBackground方法去获取
                    AVQuery<Post> postQuery = postRelation.getQuery();
                    postQuery.findInBackground(new FindCallback<Post>() {
                        @Override
                        public void done(List<Post> objects, AVException e) {
                            if (e == null) {
                                StringBuilder sb = new StringBuilder("");
                                for (Post post : objects) {
                                    sb.append(post.text());
                                }
                                textView.setText(sb.toString());
                                dismissProgressDialog();
                            } else {
                                textView.setText(e.getMessage());
                                dismissProgressDialog();
                            }
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
     * 通过id拿到comment，展示其关联数据内容
     */
    private void getComment() {
        final String commentId = setCommentId.getText().toString();
        if (TextUtils.isEmpty(commentId))
            return;
        showProgressDialog();
        AVQuery<AVObject> query = AVQuery.getQuery(COMMENT_CLASS_NAME);
        query.getInBackground(commentId, new GetCallback<AVObject>() {
            @Override
            public void done(final AVObject commentObject, AVException e) {
                if (e == null) {
                    AVObject commentPost = commentObject.getAVObject(COMMENT_POST);
                    //拿到关联对象时默认是不获取对象内数据的，要调用fetch(IfNeed)来拿到真实数据
                    commentPost.fetchIfNeededInBackground(new GetCallback<AVObject>() {
                        @Override
                        public void done(AVObject postObject, AVException e) {
                            if (e == null) {
                                String postContent = getPostContent(postObject);
                                textView.setText(getCommentContent(commentObject, postContent));
                                dismissProgressDialog();
                            } else {
                                textView.setText(e.getMessage());
                                dismissProgressDialog();
                            }
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
     * 添加一个comment(关联一个post,一对多)
     */
    private void addComment() {
        String postId = setPostId.getText().toString();
        String commentCotent = setCommentContent.getText().toString();
        if (TextUtils.isEmpty(postId) || TextUtils.isEmpty(commentCotent))
            return;
        showProgressDialog();
        final AVObject comPo = new AVObject(COMMENT_CLASS_NAME);
        comPo.put(COMMENT_CONTENT, commentCotent);
        AVQuery<AVObject> postPo = AVQuery.getQuery(POST_CLASS_NAME);
        postPo.getInBackground(postId, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject object, AVException e) {
                if (e == null) {
//                    comPo.put(COMMENT_POST, object);//放入object,comment与一个post关联起来
                    comPo.put(COMMENT_POST, AVObject.createWithoutData(POST_CLASS_NAME, object.getObjectId()));//放入objectId(得是已存在的),comment与一个post关联
                    comPo.saveInBackground(saveCallback);
                } else {
                    textView.setText(e.getMessage());
                    dismissProgressDialog();
                }
            }
        });
    }

    /**
     * 添加一个post
     */
    private void addPost() {
        String title = setPostTitle.getText().toString();
        if (TextUtils.isEmpty(title))
            return;
        showProgressDialog();
        AVObject po = new AVObject(POST_CLASS_NAME);
        po.put(POST_TITLE, title);
        po.saveInBackground(saveCallback);
    }

    /**
     * 获得relations
     */
    private void getRelations() {
        String commentId = setCommentId.getText().toString();
        if (TextUtils.isEmpty(commentId))
            return;
        showProgressDialog();
        AVQuery<AVObject> query = new AVQuery<>(COMMENT_CLASS_NAME);
        query.getInBackground(commentId, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject commentObject, AVException e) {
                if (e == null) {
                    AVRelation<AVObject> relation = commentObject.getRelation(COMMENT_LIKES);
                    AVQuery<AVObject> likesQuery = relation.getQuery();
                    //得到query后也不会有数据，需要调用findInBackground方法去获取
                    likesQuery.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> objects, AVException e) {
                            if (e == null) {
                                StringBuilder sb = new StringBuilder("");
                                for (AVObject postObject : objects) {
                                    sb.append(getPostContent(postObject) + "\n");
                                }
                                textView.setText(sb.toString());
                                dismissProgressDialog();
                            } else {
                                textView.setText(e.getMessage());
                                dismissProgressDialog();
                            }
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
     * relations,多对多
     */
    private void addRelations() {
        String commentId = setCommentId.getText().toString();
        final String postId = setPostId.getText().toString();
        if (TextUtils.isEmpty(commentId) || TextUtils.isEmpty(postId))
            return;
        showProgressDialog();
        AVQuery<AVObject> query = new AVQuery<>(COMMENT_CLASS_NAME);
        query.getInBackground(commentId, new GetCallback<AVObject>() {
            @Override
            public void done(final AVObject commentObject, AVException e) {
                if (e == null) {
                    AVQuery<AVObject> query1 = new AVQuery<>(POST_CLASS_NAME);
                    query1.getInBackground(postId, new GetCallback<AVObject>() {
                        @Override
                        public void done(AVObject object, AVException e) {
                            if (e == null) {
                                //relations会映射成一张表，可存多个obj(objId不能重复)
                                AVRelation<AVObject> relation = commentObject.getRelation(COMMENT_LIKES);//得到relations
                                relation.add(object);//可add多个,remove(post)删除
                                commentObject.saveInBackground(saveCallback);
                            } else {
                                textView.setText(e.getMessage());
                                dismissProgressDialog();
                            }
                        }
                    });
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
            case R.id.addPostBtn:
                addPost();
                break;
            case R.id.addCommentBtn:
                addComment();
                break;
            case R.id.getCommentBtn:
                getComment();
                break;
            case R.id.addRelationsBtn:
                addRelations();
                break;
            case R.id.getRelationsBtn:
                getRelations();
                break;
            case R.id.getCommentBySubClassBtn:
                getCommentBySubClass();
                break;
            default:
                break;
        }
    }

    /**
     * 按一定格式输出post数据对象
     *
     * @param object
     * @return
     */
    private String getPostContent(AVObject object) {
        if (object == null)
            return "";

        //固定字段获取
        String objectId = object.getObjectId();
        Date updatedAt = object.getUpdatedAt();
        Date createdAt = object.getCreatedAt();

        //自定义字段获取
        String title = object.getString(POST_TITLE);

        String content = "objectId:" + objectId + "\n"
                + "updatedAt:" + updatedAt + "\n"
                + "createdAt:" + createdAt + "\n"
                + "title:" + title + "\n";
        return content;
    }

    /**
     * 按一定格式输出数据对象
     *
     * @param object
     * @return
     */
    private String getCommentContent(AVObject object, String postContent) {
        if (object == null)
            return "";

        //固定字段获取
        String objectId = object.getObjectId();
        Date updatedAt = object.getUpdatedAt();
        Date createdAt = object.getCreatedAt();

        //自定义字段获取
        String commentCcontent = object.getString(COMMENT_CONTENT);

        String content = "objectId:" + objectId + "\n"
                + "updatedAt:" + updatedAt + "\n"
                + "createdAt:" + createdAt + "\n"
                + "content:" + commentCcontent + "\n"
                + "comment_post:" + postContent + "\n";
        return content;
    }

}
