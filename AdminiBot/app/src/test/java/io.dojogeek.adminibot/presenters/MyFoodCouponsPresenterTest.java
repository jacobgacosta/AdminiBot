package io.dojogeek.adminibot.presenters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.daos.OtherPaymentMethodDao;
import io.dojogeek.adminibot.dtos.DtoSimpleAdapter;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.factory.ModelsFactory;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;
import io.dojogeek.adminibot.views.MyFoodCoupons;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MyFoodCouponsPresenterImpl.class)
public class MyFoodCouponsPresenterTest {

    private ModelsFactory factory = new ModelsFactory();

    @Mock
    private MyFoodCoupons mMyFoodCoupons;

    @Mock
    private OtherPaymentMethodDao mOtherPaymentMethodDao;

    @InjectMocks
    private MyFoodCouponsPresenterImpl mMyFoodCouponsPresenterImpl =
            new MyFoodCouponsPresenterImpl(mMyFoodCoupons, mOtherPaymentMethodDao);


    @Test
    public void testObtainFoodCoupons_populateDtoFromModel() throws Exception {

        List<OtherPaymentMethodModel> otherPaymentMethodModelList = new ArrayList<>();
        otherPaymentMethodModelList.add(factory.createOtherPaymentMethodModel());

        when(mOtherPaymentMethodDao.getOtherPaymentMethodByType(TypePaymentMethodEnum.FOOD_COUPONS)).
                thenReturn(otherPaymentMethodModelList);

        ArrayList<DtoSimpleAdapter> dtoSimpleAdapterList = mock(ArrayList.class);
        whenNew(ArrayList.class).withNoArguments().thenReturn(dtoSimpleAdapterList);

        DtoSimpleAdapter dtoSimpleAdapterMock = mock(DtoSimpleAdapter.class);
        whenNew(DtoSimpleAdapter.class).withNoArguments().thenReturn(dtoSimpleAdapterMock);

        mMyFoodCouponsPresenterImpl.obtainFoodCoupons();

        verify(mOtherPaymentMethodDao).getOtherPaymentMethodByType(TypePaymentMethodEnum.FOOD_COUPONS);
        verify(dtoSimpleAdapterMock).setmTitle(otherPaymentMethodModelList.get(0).getName() + ": " +
                otherPaymentMethodModelList.get(0).getReferenceNumber());
        verify(dtoSimpleAdapterMock).setSubtitle(otherPaymentMethodModelList.get(0).getAvailableCredit().toString());
        verify(dtoSimpleAdapterMock).setId(otherPaymentMethodModelList.get(0).getId());
        verify(dtoSimpleAdapterList).add(dtoSimpleAdapterMock);
        verify(mMyFoodCoupons).listFoodCoupons(dtoSimpleAdapterList);
    }
}
