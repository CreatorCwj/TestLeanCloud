package com.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVRelation;
import com.base.BaseModel;

/**
 * Created by cwj on 15/11/29.
 */
@AVClassName("Comment")
public class Comment extends BaseModel {

    public static final String COMMENT_CLASS_NAME = "Comment";

    public static final String COMMENT_CONTENT = "content";
    public static final String COMMENT_POST = "post";
    public static final String COMMENT_LIKES = "likes";

    public void setContent(String content) {
        put(COMMENT_CONTENT, content);
    }

    public String getContent() {
        return getString(COMMENT_CONTENT);
    }

    public void setPost(Post post) {
        put(COMMENT_POST, post);
    }

    public void setPost(String postId) {
        try {
            put(COMMENT_POST, AVObject.createWithoutData(Post.class, postId));
        } catch (AVException e) {
            e.printStackTrace();
        }
    }

    public Post getPost() {
        AVObject object = getAVObject(COMMENT_POST);
        if (object instanceof Post)
            return (Post) object;
        return null;
    }

    public AVRelation<Post> getLikes() {
        return getRelation(COMMENT_LIKES);
    }

    @Override
    public String text() {
        return "Content:" + getContent() + "\n";
    }
}
