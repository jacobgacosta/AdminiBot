package io.dojogeek.adminibot.presenters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.daos.PaymentMethodDao;
import io.dojogeek.adminibot.dtos.DtoSimpleAdapter;
import io.dojogeek.adminibot.factory.ModelsFactory;
import io.dojogeek.adminibot.models.PaymentMethodModel;
import io.dojogeek.adminibot.views.MyFoodCoupons;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MyFoodCouponsPresenterImpl.class)
public class MyFoodCouponsPresenterTest {

    private ModelsFactory factory = new ModelsFactory();

    @Mock
    private MyFoodCoupons mMyFoodCoupons;

    @Mock
    private PaymentMethodDao mPaymentMethodDao;

    @InjectMocks
    private MyFoodCouponsPresenterImpl mMyFoodCouponsPresenterImpl =
            new MyFoodCouponsPresenterImpl(mMyFoodCoupons, mPaymentMethodDao);


    @Test
    public void testObtainFoodCoupons_populateDtoFromModel() throws Exception {

        List<PaymentMethodModel> otherPaymentMethodModelList = new ArrayList<>();
        //otherPaymentMethodModelList.add(factory.createOtherPaymentMethodModel());


        ArrayList<DtoSimpleAdapter> dtoSimpleAdapterList = mock(ArrayList.class);
        whenNew(ArrayList.class).withNoArguments().thenReturn(dtoSimpleAdapterList);

        DtoSimpleAdapter dtoSimpleAdapterMock = mock(DtoSimpleAdapter.class);
        whenNew(DtoSimpleAdapter.class).withNoArguments().thenReturn(dtoSimpleAdapterMock);

        mMyFoodCouponsPresenterImpl.obtainFoodCoupons();

//        verify(dtoSimpleAdapterMock).setmTitle(otherPaymentMethodModelList.getAll(0).getName() + ": " +
//                otherPaymentMethodModelList.getAll(0).getReferenceNumber());
//        verify(dtoSimpleAdapterMock).setSubtitle(otherPaymentMethodModelList.getAll(0).getAvailableCredit().toString());
        verify(dtoSimpleAdapterMock).setId(otherPaymentMethodModelList.get(0).getId());
        verify(dtoSimpleAdapterList).add(dtoSimpleAdapterMock);
        verify(mMyFoodCoupons).listFoodCoupons(dtoSimpleAdapterList);
    }
}
