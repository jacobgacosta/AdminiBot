package io.dojogeek.adminibot.presenters;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.daos.OtherPaymentMethodDao;
import io.dojogeek.adminibot.daos.OtherPaymentMethodDaoImpl;
import io.dojogeek.adminibot.dtos.DtoSimpleAdapter;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;
import io.dojogeek.adminibot.views.MyCash;

public class MyCashPresenterImpl implements MyCashPresenter {

    public static String CASH_NAME_ICON = "ic_cash";
    private MyCash mMyCash;
    private OtherPaymentMethodDao mOtherPaymentMethodDao;

    public MyCashPresenterImpl(MyCash myCash, OtherPaymentMethodDao otherPaymentMethodDao) {
        mOtherPaymentMethodDao = otherPaymentMethodDao;
        mMyCash = myCash;
    }


    @Override
    public void obtainCash() {

        List<OtherPaymentMethodModel> cashList =
                mOtherPaymentMethodDao.getOtherPaymentMethodByType(TypePaymentMethodEnum.CASH);

        List<DtoSimpleAdapter> dtoSimpleAdapters = new ArrayList<>();

        for (OtherPaymentMethodModel otherPaymentMethodModel : cashList) {

            DtoSimpleAdapter dtoSimpleAdapter = populateDtoSimpleAdapter(otherPaymentMethodModel);

            dtoSimpleAdapters.add(dtoSimpleAdapter);
        }

        mMyCash.listMyCash(dtoSimpleAdapters);

    }

    @Override
    public void unnusedView() {
        ((OtherPaymentMethodDaoImpl) mOtherPaymentMethodDao).closeConnection();
    }

    private DtoSimpleAdapter populateDtoSimpleAdapter(OtherPaymentMethodModel otherPaymentMethodModel) {

        DtoSimpleAdapter dtoSimpleAdapter = new DtoSimpleAdapter();
        dtoSimpleAdapter.setId(otherPaymentMethodModel.getId());
        dtoSimpleAdapter.setmTitle(otherPaymentMethodModel.getName());
        dtoSimpleAdapter.setSubtitle(String.valueOf(otherPaymentMethodModel.getAvailableCredit()));
        dtoSimpleAdapter.setIconName(CASH_NAME_ICON);

        return dtoSimpleAdapter;
    }
}
