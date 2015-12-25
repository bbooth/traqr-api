package com.trulinc.traqr.service.groupme;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = GroupMeImageAttachment.class, name = "image"),
        @JsonSubTypes.Type(value = GroupMeLocationAttachment.class, name = "location")
})
public abstract class GroupMeAttachment {

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
