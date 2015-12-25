package com.trulinc.traqr.service.groupme;

public class GroupMeLocationAttachment extends GroupMeAttachment {

    private String lng;
    private String lat;
    private String name;

    public GroupMeLocationAttachment(double latitude, double longitude, String name) {
        this.setType("location");
        this.lat = String.valueOf(latitude);
        this.lng = String.valueOf(longitude);
        this.name = name;
    }

    public String getLng() {
        return lng;
    }

    public String getLat() {
        return lat;
    }

    public String getName() {
        return name;
    }

}
