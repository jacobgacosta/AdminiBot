package io.dojogeek.adminibot.presenters;

import android.database.SQLException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.dojogeek.adminibot.daos.BankCardDao;
import io.dojogeek.adminibot.daos.BankCardDaoImpl;
import io.dojogeek.adminibot.daos.BankDao;
import io.dojogeek.adminibot.daos.BankDaoImpl;
import io.dojogeek.adminibot.daos.CardDetailDao;
import io.dojogeek.adminibot.daos.CardDetailDaoImpl;
import io.dojogeek.adminibot.daos.SQLiteGlobalDao;
import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.models.BankModel;
import io.dojogeek.adminibot.models.CardDetailModel;
import io.dojogeek.adminibot.models.CreditCardModel;
import io.dojogeek.adminibot.views.CreditCard;

public class CreditCardPresenterImpl implements CreditCardPresenter {

    private CreditCard mCreditCard;
    private BankCardDao mBankCardDao;
    private CardDetailDao mCardDetailDao;
    private BankDao mBankDao;

    public CreditCardPresenterImpl(CreditCard creditCard, BankCardDao bankCardDao,
                                   CardDetailDao cardDetailDao, BankDao bankDao) {

        mCreditCard = creditCard;
        mBankCardDao = bankCardDao;
        mCardDetailDao = cardDetailDao;
        mBankDao = bankDao;
    }

    @Override
    public void createCreditCard(CreditCardModel creditCardModel) {


        BankCardModel bankCardModel = creditCardModel.getBankCardModel();

        try {

            openConnections();

            beginTransactions();

            long insertedBankCardId = mBankCardDao.create(bankCardModel);

            CardDetailModel cardDetailModel = creditCardModel.getCardDetailModel();
            cardDetailModel.setBankCardId(insertedBankCardId);
            mCardDetailDao.createCardDetail(cardDetailModel);

            ((CardDetailDaoImpl) mCardDetailDao).setTransactionSuccessful();

            mCreditCard.notifySuccessfulInsertion();
            mCreditCard.returnToMyPaymentsMethods();

        } catch (SQLException exception) {

            exception.printStackTrace();

            mCreditCard.notifyErrorInsertion();
        } finally {
            endTransaction((CardDetailDaoImpl) mCardDetailDao);
        }
    }

    @Override
    public void loadBanks() {

        List<BankModel> bankModelList =  mBankDao.getBanks();

        Map<Long, Map<String, Integer>> items = new HashMap<>();

        for (BankModel bankModel : bankModelList) {
            Map<String, Integer> textAndImageNames = new HashMap<>();
            textAndImageNames.put(bankModel.getImageName(), bankModel.getName());
            items.put(bankModel.getId(), textAndImageNames);
        }

        mCreditCard.fillBankItemsSpinner(items);
    }

    @Override
    public void unnusedView() {
        ((BankCardDaoImpl) mBankCardDao).closeConnection();
        ((CardDetailDaoImpl) mCardDetailDao).closeConnection();
        ((BankDaoImpl) mBankDao).closeConnection();
    }

    private void beginTransactions() {
        beginTransaction((BankCardDaoImpl) mBankCardDao);
//        beginTransaction((CardDetailDaoImpl) mCardDetailDao);
    }

    private void beginTransaction(SQLiteGlobalDao sqLiteGlobalDao) {
        sqLiteGlobalDao.beginTransaction();
    }

    private void setTransactionSuccessful() {
//        setTransactionSuccessful((BankCardDaoImpl) mBankCardDao);
//        setTransactionSuccessful((CardDetailDaoImpl) mCardDetailDao);
    }

    private void setTransactionSuccessful(SQLiteGlobalDao sqLiteGlobalDao) {
        sqLiteGlobalDao.setTransactionSuccessful();
    }

    private void endTransactions() {
//        endTransaction((BankCardDaoImpl) mBankCardDao);
        endTransaction((CardDetailDaoImpl) mCardDetailDao);
    }

    private void endTransaction(SQLiteGlobalDao sqLiteGlobalDao) {
        sqLiteGlobalDao.endTransaction();
    }

    private void openConnections() {
        ((BankCardDaoImpl) mBankCardDao).openConnection();
        ((CardDetailDaoImpl) mCardDetailDao).openConnection();
    }
}
