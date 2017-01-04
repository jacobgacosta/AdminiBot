package io.dojogeek.adminibot.presenters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.daos.OtherPaymentMethodDao;
import io.dojogeek.adminibot.daos.OtherPaymentMethodDaoImpl;
import io.dojogeek.adminibot.dtos.DtoSimpleAdapter;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.factory.ModelsFactory;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;
import io.dojogeek.adminibot.views.MyCashActivity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MyCashPresenterImpl.class)
public class MyCashPresenterTest {

    private ModelsFactory factory = new ModelsFactory();

    @Mock
    private MyCashActivity mMyCashActivity;

    @Mock
    private OtherPaymentMethodDaoImpl mOtherPaymentMethodDao;

    @InjectMocks
    @Spy
    private MyCashPresenter mMyCashPresenter = new MyCashPresenterImpl(mMyCashActivity, mOtherPaymentMethodDao);

    @Test
    public void testObtainCash_successfulObtaining() throws Exception {

        List<OtherPaymentMethodModel> otherPaymentMethodModelList = new ArrayList<>();
        otherPaymentMethodModelList.add(factory.createOtherPaymentMethodModel());

        ArrayList<DtoSimpleAdapter> dtoSimpleAdapters = mock(ArrayList.class);
        whenNew(ArrayList.class).withNoArguments().thenReturn(dtoSimpleAdapters);

        when(mOtherPaymentMethodDao.getOtherPaymentMethodByType(TypePaymentMethodEnum.CASH)).
                thenReturn(otherPaymentMethodModelList);

        DtoSimpleAdapter dtoSimpleAdapterMock = mock(DtoSimpleAdapter.class);
        whenNew(DtoSimpleAdapter.class).withNoArguments().thenReturn(dtoSimpleAdapterMock);

        mMyCashPresenter.obtainCash();

        verify(dtoSimpleAdapterMock).setIconName(MyCashPresenterImpl.CASH_NAME_ICON);
        verify(dtoSimpleAdapterMock).setId(otherPaymentMethodModelList.get(0).getId());
        verify(dtoSimpleAdapterMock).setmTitle(otherPaymentMethodModelList.get(0).getName());
        verify(dtoSimpleAdapterMock).setSubtitle(otherPaymentMethodModelList.get(0).getAvailableCredit().toString());
        verify(dtoSimpleAdapters, times(otherPaymentMethodModelList.size())).add(dtoSimpleAdapterMock);
    }

    @Test
    public void testUnnusedView_closeConnections() {

        mMyCashPresenter.unnusedView();
        verify(mOtherPaymentMethodDao).closeConnection();

    }

}
