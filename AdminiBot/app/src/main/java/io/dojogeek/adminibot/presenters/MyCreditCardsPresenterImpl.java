package io.dojogeek.adminibot.presenters;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.daos.BankCardDao;
import io.dojogeek.adminibot.daos.BankCardDaoImpl;
import io.dojogeek.adminibot.daos.BankDao;
import io.dojogeek.adminibot.daos.BankDaoImpl;
import io.dojogeek.adminibot.daos.CardDetailDao;
import io.dojogeek.adminibot.daos.CardDetailDaoImpl;
import io.dojogeek.adminibot.dtos.DtoCreditCardAdapter;
import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.models.BankModel;
import io.dojogeek.adminibot.views.MyCreditCards;

public class MyCreditCardsPresenterImpl implements MyCreditCardsPresenter {

    public static String CREDIT_CARD_ICON_NAME = "ic_ccard";
    private MyCreditCards mMyCreditCards;
    private BankCardDao mBankCardDao;
    private CardDetailDao mCardDetailDao;
    private BankDao mBankDao;

    public MyCreditCardsPresenterImpl(MyCreditCards myCreditCards,
                                      BankCardDao bankCardDao, CardDetailDao cardDetailDao,
                                      BankDao bankDao) {
        mMyCreditCards = myCreditCards;
        mBankCardDao = bankCardDao;
        mCardDetailDao = cardDetailDao;
        mBankDao = bankDao;
    }

    @Override
    public void obtainMyCreditCards() {

        List<BankCardModel> bankCardModels = mBankCardDao.getAll();

        List<DtoCreditCardAdapter> dtoCreditCardAdapterList = new ArrayList<>();

        for (BankCardModel bankCardModel : bankCardModels) {

            DtoCreditCardAdapter dtoCreditCardAdapter = populateDtoCreditCardAdapter(bankCardModel);

            dtoCreditCardAdapterList.add(dtoCreditCardAdapter);
        }

        mMyCreditCards.listMyCreditCards(dtoCreditCardAdapterList);
    }

    @Override
    public void unnusedView() {
        ((BankCardDaoImpl) mBankCardDao).closeConnection();
        ((CardDetailDaoImpl) mCardDetailDao).closeConnection();
        ((BankDaoImpl) mBankDao).closeConnection();
    }

    private DtoCreditCardAdapter populateDtoCreditCardAdapter(BankCardModel bankCardModel) {
        DtoCreditCardAdapter dtoCreditCardAdapter = new DtoCreditCardAdapter();
        dtoCreditCardAdapter.setCreditCardNumber(bankCardModel.getNumber());
        dtoCreditCardAdapter.setCreditCardName(bankCardModel.getName());
        String bankCardImage = getBankImageByCardId(bankCardModel.getBankId());
        dtoCreditCardAdapter.setCreditCardBankImageName(CREDIT_CARD_ICON_NAME);
        dtoCreditCardAdapter.setCardId(bankCardModel.getId());

        return dtoCreditCardAdapter;
    }

    private String getBankImageByCardId(long cardId) {
        BankModel bankModel = mBankDao.getBankById(cardId);
        return bankModel.getImageName();
    }

}
