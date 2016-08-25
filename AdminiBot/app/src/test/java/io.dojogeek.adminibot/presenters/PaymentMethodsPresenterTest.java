package io.dojogeek.adminibot.presenters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.daos.BankCardDao;
import io.dojogeek.adminibot.daos.BankCardDaoImpl;
import io.dojogeek.adminibot.daos.OtherPaymentMethodDao;
import io.dojogeek.adminibot.daos.OtherPaymentMethodDaoImpl;
import io.dojogeek.adminibot.daos.OtherPaymentMethodDao;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.factory.ModelsFactory;
import io.dojogeek.adminibot.matchers.IsAList;
import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;
import io.dojogeek.adminibot.views.PaymentMethods;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PaymentMethodsPresenterTest {

    @Mock
    private PaymentMethods mPaymentMethodsActivity;

    @Mock
    private OtherPaymentMethodDaoImpl mOtherPaymentMethodDao;

    @Mock
    private BankCardDaoImpl mBankCardDao;

    @InjectMocks
    private PaymentMethodsPresenter mPaymentMethodsPresenter =
            new PaymentMethodsPresenterImpl(mPaymentMethodsActivity, mOtherPaymentMethodDao, mBankCardDao);

    @Test
    public void testLoadPaymentMethods_successfulListPaymentMethods() {

        OtherPaymentMethodModel otherPaymentMethodModelOne = ModelsFactory.createOtherPaymentMethodModel();
        otherPaymentMethodModelOne.setTypePaymentMethod(TypePaymentMethodEnum.CASH);
        otherPaymentMethodModelOne.setTypePaymentMethod(TypePaymentMethodEnum.CASH);

        OtherPaymentMethodModel otherPaymentMethodModelTwo = ModelsFactory.createOtherPaymentMethodModel();
        otherPaymentMethodModelTwo.setTypePaymentMethod(TypePaymentMethodEnum.CHEQUE);
        otherPaymentMethodModelTwo.setTypePaymentMethod(TypePaymentMethodEnum.CHEQUE);

        List<OtherPaymentMethodModel> otherPaymentMethodModelList = new ArrayList<>();
        otherPaymentMethodModelList.add(otherPaymentMethodModelOne);
        otherPaymentMethodModelList.add(otherPaymentMethodModelTwo);

        List<BankCardModel> bankCardModelList = new ArrayList<>();
        bankCardModelList.add(ModelsFactory.createBankCardModel());
        bankCardModelList.add(ModelsFactory.createBankCardModel());

        when(mOtherPaymentMethodDao.getOtherPaymentMethods()).thenReturn(otherPaymentMethodModelList);
        when(mBankCardDao.getBankCards()).thenReturn(bankCardModelList);

        mPaymentMethodsPresenter.loadPaymentMethods();

        verify(mOtherPaymentMethodDao).getOtherPaymentMethods();
        verify(mBankCardDao).getBankCards();
        verify(mPaymentMethodsActivity).
                prepareView(argThat(new IsAList<TypePaymentMethodEnum>().size(3)));

    }

    @Test
    public void testLoadPaymentMethods_withEmptyPaymentMethods() {

        when(mOtherPaymentMethodDao.getOtherPaymentMethods()).thenReturn(new ArrayList<OtherPaymentMethodModel>());
        when(mBankCardDao.getBankCards()).thenReturn(new ArrayList<BankCardModel>());

        mPaymentMethodsPresenter.loadPaymentMethods();

        verify(mOtherPaymentMethodDao).getOtherPaymentMethods();
        verify(mBankCardDao).getBankCards();
        verify(mPaymentMethodsActivity).
                prepareView(argThat(new IsAList<TypePaymentMethodEnum>().size(0)));
    }

    @Test
    public void testUnnusedView_closeConnections() {

        mPaymentMethodsPresenter.unnusedView();

        verify(mOtherPaymentMethodDao).closeConnection();
        verify(mBankCardDao).closeConnection();

    }

}
