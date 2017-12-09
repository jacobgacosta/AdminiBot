package io.dojogeek.adminibot.models;

public class BankModel {

    private long mId;
    private int mName;
    private String mImageName;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public int getName() {
        return mName;
    }

    public void setName(int name) {
        this.mName = name;
    }

    public String getImageName() {
        return mImageName;
    }

    public void setImageName(String imageName) {
        this.mImageName = imageName;
    }
}
