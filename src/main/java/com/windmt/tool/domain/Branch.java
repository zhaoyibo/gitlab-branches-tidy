package com.windmt.tool.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Branch {

    private String name;
    private Commit commit;
    private boolean merged;
    @JsonProperty("protected")
    private boolean _protected;
    @JsonProperty("developers_can_push")
    private boolean developersCanPush;
    @JsonProperty("developers_can_merge")
    private boolean developersCanMerge;
    @JsonProperty("can_push")
    private boolean canPush;
    @JsonProperty("default")
    private boolean _default;


}