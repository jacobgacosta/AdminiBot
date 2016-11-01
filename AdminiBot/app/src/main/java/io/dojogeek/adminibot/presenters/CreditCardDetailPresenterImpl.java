package io.dojogeek.adminibot.presenters;

import io.dojogeek.adminibot.daos.BankCardDao;
import io.dojogeek.adminibot.daos.BankDao;
import io.dojogeek.adminibot.daos.BankDaoImpl;
import io.dojogeek.adminibot.daos.CardDetailDao;
import io.dojogeek.adminibot.dtos.DtoCreditCardDetail;
import io.dojogeek.adminibot.enums.TrademarkEnum;
import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.models.BankModel;
import io.dojogeek.adminibot.models.CardDetailModel;
import io.dojogeek.adminibot.models.CreditCardModel;
import io.dojogeek.adminibot.views.CreditCardDetail;

public class CreditCardDetailPresenterImpl implements CreditCardDetailPresenter {

    private CreditCardDetail mCreditCardDetail;
    private BankCardDao mBankCardDao;
    private CardDetailDao mCardDetailDao;
    private BankDao mBankDao;

    public CreditCardDetailPresenterImpl(CreditCardDetail creditCardDetail, BankCardDao bankCardDao,
                                         CardDetailDao cardDetailDao, BankDao bankDao) {
        mCreditCardDetail = creditCardDetail;
        mBankCardDao = bankCardDao;
        mCardDetailDao = cardDetailDao;
        mBankDao = bankDao;
    }

    @Override
    public void loadCreditCardDetailById(long creditCardId) {

        try {

            BankCardModel bankCardModel = mBankCardDao.getBankCardById(creditCardId);

            CardDetailModel cardDetailModel = mCardDetailDao.getCardDetailByBankCardId(bankCardModel.getId());

            BankModel bankModel = mBankDao.getBankById(bankCardModel.getBankId());

            DtoCreditCardDetail dtoCreditCardDetail =
                    populateDtoCreditCardDetail(bankCardModel, cardDetailModel, bankModel);

            mCreditCardDetail.showCreditCardDetail(dtoCreditCardDetail);

        } catch (DataException e) {
            e.printStackTrace();
        }
    }

    private DtoCreditCardDetail populateDtoCreditCardDetail(BankCardModel bankCardModel,
                                                            CardDetailModel cardDetailModel,
                                                            BankModel bankModel) {

        DtoCreditCardDetail dtoCreditCardDetail = new DtoCreditCardDetail();
        dtoCreditCardDetail.setCreditCardName(bankCardModel.getName());
        dtoCreditCardDetail.setCurrentBalance(String.valueOf(cardDetailModel.getCurrentBalance()));
        dtoCreditCardDetail.setCreditCardNumber(bankCardModel.getNumber());
        dtoCreditCardDetail.setBankName(bankModel.getName());
        dtoCreditCardDetail.setTrademarkImageName(TrademarkEnum.getTrademarkImageNameFromEnum(bankCardModel.getBrand()));
        dtoCreditCardDetail.setAvailableCredit(String.valueOf(bankCardModel.getAvailableCredit()));
        dtoCreditCardDetail.setCreditLimit(String.valueOf(cardDetailModel.getCreditLimit()));
        dtoCreditCardDetail.setCutoffDate(cardDetailModel.getCuttoffDate());
        dtoCreditCardDetail.setPayDayLimit(cardDetailModel.getPayDayLimit());

        return dtoCreditCardDetail;
    }

}
