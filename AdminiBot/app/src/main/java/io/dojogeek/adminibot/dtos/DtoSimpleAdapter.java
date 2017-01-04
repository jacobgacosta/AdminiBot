package io.dojogeek.adminibot.dtos;

public class DtoSimpleAdapter {

    private long mId;
    private String mTitle;
    private String mSubtitle;
    private String mIconName;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setmTitle(String title) {
        this.mTitle = title;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public void setSubtitle(String subtitle) {
        this.mSubtitle = subtitle;
    }

    public String getIconName() {
        return mIconName;
    }

    public void setIconName(String iconName) {
        this.mIconName = iconName;
    }
}
