package io.dojogeek.adminibot.models;

public class CreditCardModel {

    private BankCardModel mBankCardModel;
    private CardDetailModel mCardDetailModel;

    public BankCardModel getBankCardModel() {
        return mBankCardModel;
    }

    public void setBankCardModel(BankCardModel bankCardModel) {
        this.mBankCardModel = bankCardModel;
    }

    public CardDetailModel getCardDetailModel() {
        return mCardDetailModel;
    }

    public void setCardDetailModel(CardDetailModel cardDetailModel) {
        this.mCardDetailModel = cardDetailModel;
    }
}
