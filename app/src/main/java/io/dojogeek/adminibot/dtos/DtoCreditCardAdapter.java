package io.dojogeek.adminibot.dtos;

public class DtoCreditCardAdapter {

    private String mCreditCardName;
    private String mCreditCardNumber;
    private String mCreditCardBankImageName;
    private long mCardId;

    public String getCreditCardName() {
        return mCreditCardName;
    }

    public void setCreditCardName(String creditCardName) {
        mCreditCardName = creditCardName;
    }

    public String getCreditCardNumber() {
        return mCreditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        mCreditCardNumber = creditCardNumber;
    }

    public String getCreditCardBankImageName() {
        return mCreditCardBankImageName;
    }

    public void setCreditCardBankImageName(String creditCardBankImageName) {
        mCreditCardBankImageName = creditCardBankImageName;
    }

    public long getCardId() {
        return mCardId;
    }

    public void setCardId(long cardId) {
        mCardId = cardId;
    }
}
