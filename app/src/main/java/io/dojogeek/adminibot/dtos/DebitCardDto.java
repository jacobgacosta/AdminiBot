package io.dojogeek.adminibot.dtos;

import android.os.Parcel;
import android.os.Parcelable;

public class DebitCardDto implements Parcelable {

    public static final Creator<DebitCardDto> CREATOR = new Creator<DebitCardDto>() {
        @Override
        public DebitCardDto createFromParcel(Parcel in) {
            return new DebitCardDto(in);
        }

        @Override
        public DebitCardDto[] newArray(int size) {
            return new DebitCardDto[size];
        }
    };

    private String mName;
    private String mNumber;
    private String mAmount;

    public DebitCardDto() {

    }

    public DebitCardDto(Parcel in) {
        mName = in.readString();
        mNumber = in.readString();
        mAmount = in.readString();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        this.mNumber = number;
    }

    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String total) {
        this.mAmount = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mNumber);
        dest.writeString(mAmount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof DebitCardDto)) {
            return false;
        }

        DebitCardDto that = (DebitCardDto) o;

        if (!mName.equals(that.mName)) {
            return false;
        }

        if (!mNumber.equals(that.mNumber)) {
            return false;
        }

        return mAmount.equals(that.mAmount);
    }

    @Override
    public int hashCode() {
        int result = mName.hashCode();
        result = 31 * result + mNumber.hashCode();
        result = 31 * result + mAmount.hashCode();

        return result;
    }

}
