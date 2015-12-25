package com.trulinc.traqr.service.groupme;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupMeCallBack {

    private String id;
    private String sourceGuid;
    private DateTime createdAt;
    private String userId;
    private String groupId;
    private String name;
    private String avatarUrl;
    private String text;
    private Boolean system;
    private List<GroupMeAttachment> attachments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceGuid() {
        return sourceGuid;
    }

    @JsonProperty("source_guid")
    public void setSourceGuid(String sourceGuid) {
        this.sourceGuid = sourceGuid;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    @JsonProperty("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    @JsonProperty("group_id")
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    @JsonProperty("avatar_url")
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getSystem() {
        return system;
    }

    public void setSystem(Boolean system) {
        this.system = system;
    }

    public List<GroupMeAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<GroupMeAttachment> attachments) {
        this.attachments = attachments;
    }
}
