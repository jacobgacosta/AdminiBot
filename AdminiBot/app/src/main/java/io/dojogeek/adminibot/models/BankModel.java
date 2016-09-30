package io.dojogeek.adminibot.models;

public class BankModel {

    private long mId;
    private String mName;
    private Integer mImageName;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public Integer getImageName() {
        return mImageName;
    }

    public void setImageName(Integer imageName) {
        this.mImageName = imageName;
    }
}
