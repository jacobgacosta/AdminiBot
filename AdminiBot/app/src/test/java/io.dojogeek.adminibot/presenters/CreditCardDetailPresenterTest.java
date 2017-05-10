package io.dojogeek.adminibot.presenters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import io.dojogeek.adminibot.daos.BankCardDao;
import io.dojogeek.adminibot.daos.BankDao;
import io.dojogeek.adminibot.daos.CardDetailDao;
import io.dojogeek.adminibot.dtos.DtoCreditCardDetail;
import io.dojogeek.adminibot.enums.TrademarkEnum;
import io.dojogeek.adminibot.factory.ModelsFactory;
import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.models.BankModel;
import io.dojogeek.adminibot.models.CardDetailModel;
import io.dojogeek.adminibot.views.CreditCardDetail;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CreditCardDetailPresenterImpl.class, TrademarkEnum.class})
public class CreditCardDetailPresenterTest {

    private ModelsFactory factory = new ModelsFactory();
    @Mock
    private CreditCardDetail mCreditCardDetail;
    @Mock
    private BankCardDao mBankCardDao;
    @Mock
    private CardDetailDao mCardDetailDao;
    @Mock
    private BankDao mBankDao;

    @InjectMocks
    private CreditCardDetailPresenter mCreditCardDetailPresenter =
            new CreditCardDetailPresenterImpl(mCreditCardDetail, mBankCardDao, mCardDetailDao,
                    mBankDao);

    @Test
    public void testLoadCreditCardDetailById_correctFlow() throws Exception {

        long creditCardIdtest = 1;

        BankCardModel bankCardModel = factory.createBankCardModel();
        when(mBankCardDao.getById(creditCardIdtest)).thenReturn(bankCardModel);

        CardDetailModel cardDetailModel = factory.createCardDetailModel();
        when(mCardDetailDao.getCardDetailByBankCardId(bankCardModel.getId())).thenReturn(cardDetailModel);

        BankModel bankModel = factory.createBankModel();
        when(mBankDao.getBankById(bankCardModel.getBankId())).thenReturn(bankModel);

        DtoCreditCardDetail creditCardDetailMock = mock(DtoCreditCardDetail.class);
        whenNew(DtoCreditCardDetail.class).withNoArguments().thenReturn(creditCardDetailMock);

        mockStatic(TrademarkEnum.class);
//        TrademarkEnum.getTrademarkImageNameFromEnum(bankCardModel.getBrand());
        String trademarkImageName = "ic_amex";
        given(TrademarkEnum.getTrademarkImageNameFromEnum(bankCardModel.getBrand())).
                willReturn(trademarkImageName);

        mCreditCardDetailPresenter.loadCreditCardDetailById(creditCardIdtest);

        verify(mBankCardDao).getById(creditCardIdtest);
        verify(mCardDetailDao).getCardDetailByBankCardId(bankCardModel.getId());
        verify(mBankDao).getBankById(bankCardModel.getBankId());
        verify(creditCardDetailMock).setCreditCardName(bankCardModel.getName());
        verify(creditCardDetailMock).setCurrentBalance(String.valueOf(cardDetailModel.getCurrentBalance()));
        verify(creditCardDetailMock).setCreditCardNumber(bankCardModel.getNumber());
        verify(creditCardDetailMock).setBankName(bankModel.getName());
        verify(creditCardDetailMock).setTrademarkImageName(trademarkImageName);
        verify(creditCardDetailMock).setAvailableCredit(String.valueOf(bankCardModel.getAvailableCredit()));
        verify(creditCardDetailMock).setCreditLimit(String.valueOf(cardDetailModel.getCreditLimit()));
        verify(creditCardDetailMock).setCutoffDate(cardDetailModel.getCuttoffDate());
        verify(creditCardDetailMock).setPayDayLimit(cardDetailModel.getPayDayLimit());
        verify(mCreditCardDetail).showCreditCardDetail(creditCardDetailMock);
        verifyStatic();
        TrademarkEnum.getTrademarkImageNameFromEnum(bankCardModel.getBrand());
    }

}
