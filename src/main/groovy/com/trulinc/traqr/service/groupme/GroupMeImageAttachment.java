package com.trulinc.traqr.service.groupme;

public class GroupMeImageAttachment extends GroupMeAttachment {

    private String url;

    public GroupMeImageAttachment() {
        this.setType("image");
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
