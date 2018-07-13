
package com.torchhound.thorus.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Thread {

    @SerializedName("threadId")
    @Expose
    private Integer threadId;
    @SerializedName("threadTitle")
    @Expose
    private String threadTitle;
    @SerializedName("posts")
    @Expose
    private List<Object> posts = null;

    public Integer getThreadId() {
        return threadId;
    }

    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    public String getThreadTitle() {
        return threadTitle;
    }

    public void setThreadTitle(String threadTitle) {
        this.threadTitle = threadTitle;
    }

    public List<Object> getPosts() {
        return posts;
    }

    public void setPosts(List<Object> posts) {
        this.posts = posts;
    }

}
