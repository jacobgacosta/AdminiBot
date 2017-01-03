package io.dojogeek.adminibot.presenters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.daos.BankCardDaoImpl;
import io.dojogeek.adminibot.daos.BankDaoImpl;
import io.dojogeek.adminibot.daos.CardDetailDaoImpl;
import io.dojogeek.adminibot.dtos.DtoCreditCardAdapter;
import io.dojogeek.adminibot.factory.ModelsFactory;
import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.models.BankModel;
import io.dojogeek.adminibot.views.MyCreditCards;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MyCreditCardsPresenterImpl.class)
public class MyCreditCardsPresenterTest {

    private ModelsFactory factory = new ModelsFactory();

    @Mock
    private MyCreditCards mMyCreditCards;

    @Mock
    private BankCardDaoImpl mBankCardDao;

    @Mock
    private CardDetailDaoImpl mCardDetailDao;

    @Mock
    private BankDaoImpl mBankDao;

    @InjectMocks
    private MyCreditCardsPresenter mMyCreditCardsPresenter =
            new MyCreditCardsPresenterImpl(mMyCreditCards, mBankCardDao, mCardDetailDao, mBankDao);

    @Test
    public void testObtainMyCreditCards_successfulObtaining() throws Exception {

        List<BankCardModel> bankCardModels = new ArrayList<>();
        bankCardModels.add(factory.createBankCardModel());

        when(mBankCardDao.getBankCards()).thenReturn(bankCardModels);

        DtoCreditCardAdapter dtoCreditCardAdapterMock = mock(DtoCreditCardAdapter.class);
        whenNew(DtoCreditCardAdapter.class).withNoArguments().thenReturn(dtoCreditCardAdapterMock);

        ArrayList dtoCreditCardsList = mock(ArrayList.class);
        whenNew(ArrayList.class).withNoArguments().thenReturn(dtoCreditCardsList);

        BankModel bankModelMock = mock(BankModel.class);
        when(mBankDao.getBankById(anyLong())).thenReturn(bankModelMock);
        String imageName = "ic_santander";
        when(bankModelMock.getImageName()).thenReturn(imageName);

        mMyCreditCardsPresenter.obtainMyCreditCards();

        verify(mBankCardDao).getBankCards();
        verify(dtoCreditCardAdapterMock, times(bankCardModels.size())).
                setCreditCardName(bankCardModels.get(0).getName());
        verify(dtoCreditCardAdapterMock, times(bankCardModels.size())).
                setCreditCardNumber(bankCardModels.get(0).getNumber());
        verify(dtoCreditCardAdapterMock, times(bankCardModels.size())).
                setCreditCardBankImageName(imageName);
        verify(dtoCreditCardAdapterMock, times(bankCardModels.size())).
                setCardId(bankCardModels.get(0).getId());
        verify(dtoCreditCardsList, times(bankCardModels.size())).
                add(dtoCreditCardAdapterMock);
        verify(mMyCreditCards).listMyCreditCards(dtoCreditCardsList);

    }

    @Test
    public void testUnnusedView_closeConnections() {
        mMyCreditCardsPresenter.unnusedView();
        verify(mBankCardDao).closeConnection();
        verify(mCardDetailDao).closeConnection();
        verify(mBankDao).closeConnection();
    }

}
