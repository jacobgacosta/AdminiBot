package io.dojogeek.adminibot.dtos;

public class DtoCreditCardDetail {

    private String mCreditCardName;
    private String mCurrentBalance;
    private String mCreditCardNumber;
    private int mBankName;
    private String mTrademarkImageName;
    private String mAvailableCredit;
    private String mCreditLimit;
    private String mCutoffDate;
    private String mPayDayLimit;

    public String getCreditCardName() {
        return mCreditCardName;
    }

    public void setCreditCardName(String creditCardName) {
        mCreditCardName = creditCardName;
    }

    public String getCurrentBalance() {
        return mCurrentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        mCurrentBalance = currentBalance;
    }

    public String getCreditCardNumber() {
        return mCreditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        mCreditCardNumber = creditCardNumber;
    }

    public int getBankName() {
        return mBankName;
    }

    public void setBankName(int bankName) {
        mBankName = bankName;
    }

    public String getTrademarkImageName() {
        return mTrademarkImageName;
    }

    public void setTrademarkImageName(String trademarkImageName) {
        mTrademarkImageName = trademarkImageName;
    }

    public String getAvailableCredit() {
        return mAvailableCredit;
    }

    public void setAvailableCredit(String availableCredit) {
        mAvailableCredit = availableCredit;
    }

    public String getCreditLimit() {
        return mCreditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        mCreditLimit = creditLimit;
    }

    public String getCutoffDate() {
        return mCutoffDate;
    }

    public void setCutoffDate(String cutoffDate) {
        mCutoffDate = cutoffDate;
    }

    public String getPayDayLimit() {
        return mPayDayLimit;
    }

    public void setPayDayLimit(String payDayLimit) {
        mPayDayLimit = payDayLimit;
    }
}
