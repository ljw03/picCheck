package kiwu.android.piccheckstart.model;

import java.util.Objects;

public class TaskModel {
    private String list_id;
    private String title;
    private String picture;
    private String createDateStr;
    private String date;
    private String time;
    private String category;

    public TaskModel(String list_id, String title, String picture, String createDateStr, String date, String time, String category) {
        this.list_id = list_id;
        this.title = title;
        this.picture = picture;
        this.createDateStr = createDateStr;
        this.date = date;
        this.time = time;
        this.category = category;
    }

    public TaskModel(String title, String date, String time, String category) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.category = category;
    }


    public TaskModel(String list_id) {
        this.list_id = list_id;
    }

    public String getList_id() {
        return list_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getDate() { return date; }

    public String getTime() { return time; }

    public String getCategory() { return category; }

    public void setDate(String date) { this.date = date; }

    public void setTime(String time) { this.time = time; }

    public void setCategory(String category) { this.category = category; }

    @Override
    public String toString() {
        return "TodoListModel{" +
                "list_id='" + list_id + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskModel that = (TaskModel) o;
        return list_id == that.list_id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(list_id);
    }
}
