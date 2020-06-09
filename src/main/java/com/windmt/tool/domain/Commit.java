package com.windmt.tool.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Commit {

    private String id;
    @JsonProperty("short_id")
    private String shortId;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("parent_ids")
    private String parentIds;
    private String title;
    private String message;
    @JsonProperty("author_name")
    private String authorName;
    @JsonProperty("author_email")
    private String authorEmail;
    @JsonProperty("authored_date")
    private Date authoredDate;
    @JsonProperty("committer_name")
    private String committerName;
    @JsonProperty("committer_email")
    private String committerEmail;
    @JsonProperty("committed_date")
    private Date committedDate;

    public String createdAt(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createdAt);
    }

    public String authoredDate(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(authoredDate);
    }

}