package io.dojogeek.adminibot.presenters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.daos.PaymentMethodDaoImpl;
import io.dojogeek.adminibot.dtos.DtoSimpleAdapter;
import io.dojogeek.adminibot.factory.ModelsFactory;
import io.dojogeek.adminibot.models.PaymentMethodModel;
import io.dojogeek.adminibot.views.MyCashActivity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MyCashPresenterImpl.class)
public class MyCashPresenterTest {

    private ModelsFactory factory = new ModelsFactory();

    @Mock
    private MyCashActivity mMyCashActivity;

    @Mock
    private PaymentMethodDaoImpl mOtherPaymentMethodDaoImpl;

    @InjectMocks
    @Spy
    private MyCashPresenter mMyCashPresenter = new MyCashPresenterImpl(mMyCashActivity, mOtherPaymentMethodDaoImpl);

    @Test
    public void testObtainCash_populateDtoSimpleAdapterFromModel() throws Exception {

        List<PaymentMethodModel> otherPaymentMethodModelList = new ArrayList<>();
        otherPaymentMethodModelList.add(factory.createOtherPaymentMethodModel());

        ArrayList<DtoSimpleAdapter> dtoSimpleAdapters = mock(ArrayList.class);
        whenNew(ArrayList.class).withNoArguments().thenReturn(dtoSimpleAdapters);


        DtoSimpleAdapter dtoSimpleAdapterMock = mock(DtoSimpleAdapter.class);
        whenNew(DtoSimpleAdapter.class).withNoArguments().thenReturn(dtoSimpleAdapterMock);

        mMyCashPresenter.obtainCash();

        verify(dtoSimpleAdapterMock).setIconName(MyCashPresenterImpl.CASH_NAME_ICON);
        verify(dtoSimpleAdapterMock).setId(otherPaymentMethodModelList.get(0).getId());
//        verify(dtoSimpleAdapterMock).setmTitle(otherPaymentMethodModelList.getAll(0).getName());
//        verify(dtoSimpleAdapterMock).setSubtitle(otherPaymentMethodModelList.getAll(0).getAvailableCredit().toString());
        verify(dtoSimpleAdapters, times(otherPaymentMethodModelList.size())).add(dtoSimpleAdapterMock);
    }

    @Test
    public void testObtainCash_invokeListMyCash() throws Exception {

        ArrayList<DtoSimpleAdapter> dtoSimpleAdapters = mock(ArrayList.class);
        whenNew(ArrayList.class).withNoArguments().thenReturn(dtoSimpleAdapters);

        mMyCashPresenter.obtainCash();

        verify(mMyCashActivity).listMyCash(dtoSimpleAdapters);
    }

    @Test
    public void testObtainCash_calculateTotal() {

        List<PaymentMethodModel> otherPaymentMethodModelList = new ArrayList<>();
        otherPaymentMethodModelList.add(factory.createOtherPaymentMethodModel());
        otherPaymentMethodModelList.add(factory.createOtherPaymentMethodModel());

        mMyCashPresenter.obtainCash();

        BigDecimal totalCash = new BigDecimal(0);

        for (PaymentMethodModel otherPaymentMethodModel : otherPaymentMethodModelList) {
//            totalCash = totalCash.add(otherPaymentMethodModel.getAvailableCredit());
        }

        verify(mMyCashActivity).showTotalCash(totalCash);
    }

    @Test
    public void testUnnusedView_closeConnections() {

        mMyCashPresenter.unnusedView();
        verify(mOtherPaymentMethodDaoImpl).closeConnection();

    }

}
