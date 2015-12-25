package com.trulinc.traqr.service.groupme;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

public class GroupMePost {

    private String text;
    private String botId;

    private List<GroupMeAttachment> attachments = new LinkedList<GroupMeAttachment>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBotId() {
        return botId;
    }

    @JsonProperty("bot_id")
    public void setBotId(String botId) {
        this.botId = botId;
    }

    public List<GroupMeAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<GroupMeAttachment> attachments) {
        this.attachments = attachments;
    }
}
