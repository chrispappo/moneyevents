package com.moneyevents2020.app.domain;


import java.util.Objects;

public class MoneyEvent {
    private String boardId;
    private String month;
    private int categoryId;
    private String eventId;
    private String userId;
    private String label;
    private float value;
    private String currency;
    private long date;
    private   boolean isValidated=false;
    private  boolean isDeleted=false;


    public MoneyEvent() { }

    public MoneyEvent(String boardId, String month, int categoryId, String eventId, String userId, String label, float value, String currency, long date) {
        this.boardId = boardId;
        this.month = month;
        this.categoryId = categoryId;
        this.eventId = eventId;
        this.userId = userId;
        this.label = label;
        this.value = value;
        this.currency = currency;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoneyEvent that = (MoneyEvent) o;
        return categoryId == that.categoryId &&
                Float.compare(that.value, value) == 0 &&
                date == that.date &&
                Objects.equals(boardId, that.boardId) &&
                Objects.equals(month, that.month) &&
                Objects.equals(eventId, that.eventId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(label, that.label) &&
                Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardId, month, categoryId, eventId, userId, label, value, currency, date);
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getDate() {
        return date;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setDate(long date) {
        this.date = date;
    }



    public String getCollection(){
        return DataType.EVENTS+"/"+getMonth();
    }


 }
