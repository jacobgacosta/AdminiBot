package io.dojogeek.adminibot.presenters;

import android.database.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.dojogeek.adminibot.daos.BankCardDaoImpl;
import io.dojogeek.adminibot.daos.BankDaoImpl;
import io.dojogeek.adminibot.daos.CardDetailDaoImpl;
import io.dojogeek.adminibot.factory.ModelsFactory;
import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.models.BankModel;
import io.dojogeek.adminibot.models.CardDetailModel;
import io.dojogeek.adminibot.models.CreditCardModel;
import io.dojogeek.adminibot.views.CreditCard;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CreditCardPresenterImpl.class})
public class CreditCardPresenterTest {

    private ModelsFactory mFactory = new ModelsFactory();

    @Mock
    private CreditCard mCreditCard;

    @Mock
    private BankCardDaoImpl mBankCardDao;

    @Mock
    private CardDetailDaoImpl mCardDetailDao;

    @Mock
    private BankDaoImpl mBankDao;

    @InjectMocks
    private CreditCardPresenter mCreditCardPresenter =
            new CreditCardPresenterImpl(mCreditCard, mBankCardDao, mCardDetailDao, mBankDao);

    @Test
    public void testCreateCreditCard_successfulCreation() {

        CreditCardModel creditCardModel= mock(CreditCardModel.class);

        long insertedBankCardId = 1;

        BankCardModel bankCardModelMock = mock(BankCardModel.class);

        when(creditCardModel.getBankCardModel()).thenReturn(bankCardModelMock);
        when(mBankCardDao.create(bankCardModelMock)).thenReturn(insertedBankCardId);

        CardDetailModel cardDetailModelMock = mock(CardDetailModel.class);

        long insertedCardDetailId = 1;

        when(creditCardModel.getCardDetailModel()).thenReturn(cardDetailModelMock);
        when(mCardDetailDao.createCardDetail(cardDetailModelMock)).thenReturn(insertedCardDetailId);

        mCreditCardPresenter.createCreditCard(creditCardModel);

        verify(mBankCardDao).openConnection();
        verify(mCardDetailDao).openConnection();
        verify(mBankCardDao).beginTransaction();
//        verify(mCardDetailDao).beginTransaction();
        verify(mBankCardDao).create(creditCardModel.getBankCardModel());
        verify(cardDetailModelMock).setBankCardId(insertedBankCardId);
        verify(mCardDetailDao).createCardDetail(creditCardModel.getCardDetailModel());
        verify(mCreditCard).notifySuccessfulInsertion();
        verify(mCreditCard).returnToMyPaymentsMethods();
//        verify(mBankCardDao).setTransactionSuccessful();
        verify(mCardDetailDao).setTransactionSuccessful();
//        verify(mBankCardDao).endTransaction();
        verify(mCardDetailDao).endTransaction();

    }

    @Test
    public void testCreateExpense_withBankCardInsertionError() {

        CreditCardModel creditCardModel= mock(CreditCardModel.class);

        BankCardModel bankCardModelMock = mock(BankCardModel.class);

        when(creditCardModel.getBankCardModel()).thenReturn(bankCardModelMock);

        when(mBankCardDao.create(bankCardModelMock)).thenThrow(new SQLException());

//        doThrow(SQLException.class).when(mBankCardDao).create(bankCardModelMock);

        mCreditCardPresenter.createCreditCard(creditCardModel);

        verify(mBankCardDao).openConnection();
        verify(mCardDetailDao).openConnection();
        verify(mBankCardDao).beginTransaction();
//        verify(mCardDetailDao).beginTransaction();
        verify(mBankCardDao).create(bankCardModelMock);
        verify(mCardDetailDao, never()).createCardDetail((CardDetailModel) anyObject());
        verify(mBankCardDao, never()).setTransactionSuccessful();
        verify(mCardDetailDao, never()).setTransactionSuccessful();
        verify(mCreditCard, never()).notifySuccessfulInsertion();
        verify(mCreditCard).notifyErrorInsertion();
//        verify(mBankCardDao).endTransaction();
        verify(mCardDetailDao).endTransaction();

    }

    @Test
    public void testCreateExpense_withCardDetailInsertionError() {

        CreditCardModel creditCardModel= mock(CreditCardModel.class);

        long insertedBankCardId = 1;
        BankCardModel bankCardModelMock = mock(BankCardModel.class);
        when(creditCardModel.getBankCardModel()).thenReturn(bankCardModelMock);
        when(mBankCardDao.create(bankCardModelMock)).thenReturn(insertedBankCardId);

        CardDetailModel cardDetailModelMock = mock(CardDetailModel.class);
        when(creditCardModel.getCardDetailModel()).thenReturn(cardDetailModelMock);
        when(mCardDetailDao.createCardDetail(cardDetailModelMock)).thenThrow(SQLException.class);

        mCreditCardPresenter.createCreditCard(creditCardModel);

        verify(mBankCardDao).openConnection();
        verify(mCardDetailDao).openConnection();
        verify(mBankCardDao).beginTransaction();
//        verify(mCardDetailDao).beginTransaction();
        verify(mBankCardDao).create(bankCardModelMock);
        verify(mCardDetailDao).createCardDetail((CardDetailModel) anyObject());
        verify(mBankCardDao, never()).setTransactionSuccessful();
        verify(mCardDetailDao, never()).setTransactionSuccessful();
        verify(mCreditCard, never()).notifySuccessfulInsertion();
        verify(mCreditCard).notifyErrorInsertion();
//        verify(mBankCardDao).endTransaction();
        verify(mCardDetailDao).endTransaction();

    }

    @Test
    public void testLoadBanks_successFlow() throws Exception {

        BankModel bankModel = null;

        List<BankModel> banks = new ArrayList<>();
        banks.add(bankModel);
        when(mBankDao.getBanks()).thenReturn(banks);

        HashMap<Long, Map<String, Integer>> items = mock(HashMap.class);
        HashMap<String, Integer> textAndImageNames = mock(HashMap.class);
        whenNew(HashMap.class).withNoArguments().thenReturn(items).
                thenReturn(textAndImageNames);

        mCreditCardPresenter.loadBanks();

        verify(mBankDao).getBanks();
        verify(textAndImageNames).put(bankModel.getImageName(), bankModel.getName());
        verify(items).put(bankModel.getId(), textAndImageNames);
        verify(mCreditCard).fillBankItemsSpinner(items);
    }

    @Test
    public void testUnnusedView_closeConnections() {

        mCreditCardPresenter.unnusedView();

        verify(mCardDetailDao).closeConnection();
        verify(mBankCardDao).closeConnection();
        verify(mBankDao).closeConnection();

    }

}
