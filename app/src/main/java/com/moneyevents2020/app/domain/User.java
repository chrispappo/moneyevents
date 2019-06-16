package com.moneyevents2020.app.domain;

public class User {
    final private String userId;
    final private String uniqueUserName;
    final private String messagingId;

    public User() {
        userId=null;
        uniqueUserName=null;
        messagingId=null;
    }

    public User(String userId, String uniqueUserName, String messagingId) {
        this.userId = userId;
        this.uniqueUserName = uniqueUserName;
        this.messagingId = messagingId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUniqueUserName() {
        return uniqueUserName;
    }

    public String getMessagingId() {
        return messagingId;
    }
}
