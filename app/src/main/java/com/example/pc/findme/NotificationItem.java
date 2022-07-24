package com.example.pc.findme;

public class NotificationItem {

    private String id;
    private String notifyType;
    private String notifyMessage;
    private String notifyTime;





    public NotificationItem(String id, String notifyType, String notifyMessage, String notifyTime)
    {
        this.id = id;
        this.notifyType = notifyType;
        this.notifyMessage = notifyMessage;
        this.notifyTime = notifyTime;
    }

    public String getId() {
        return id;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public String getNotifyMessage() {
        return notifyMessage;
    }

    public String getNotifyTime() {
        return notifyTime;
    }
}
