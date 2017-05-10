package io.dojogeek.adminibot.presenters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.daos.PaymentMethodDaoImpl;
import io.dojogeek.adminibot.daos.PaymentMethodDao;
import io.dojogeek.adminibot.dtos.DtoSimpleAdapter;
import io.dojogeek.adminibot.models.PaymentMethodModel;
import io.dojogeek.adminibot.views.MyCash;

public class MyCashPresenterImpl implements MyCashPresenter {

    public static String CASH_NAME_ICON = "ic_cash";
    private MyCash mMyCash;
    private PaymentMethodDao mPaymentMethodDao;

    public MyCashPresenterImpl(MyCash myCash, PaymentMethodDao paymentMethodDao) {
        mPaymentMethodDao = paymentMethodDao;
        mMyCash = myCash;
    }


    @Override
    public void obtainCash() {

        List<PaymentMethodModel> cashList = null;

        List<DtoSimpleAdapter> dtoSimpleAdapters = new ArrayList<>();

        BigDecimal totalCash = new BigDecimal(0);

        for (PaymentMethodModel otherPaymentMethodModel : cashList) {

            DtoSimpleAdapter dtoSimpleAdapter = populateDtoSimpleAdapter(otherPaymentMethodModel);

            dtoSimpleAdapters.add(dtoSimpleAdapter);

//            totalCash = totalCash.add(otherPaymentMethodModel.getAvailableCredit());
        }

        mMyCash.listMyCash(dtoSimpleAdapters);

        mMyCash.showTotalCash(totalCash);

    }

    @Override
    public void unnusedView() {
        ((PaymentMethodDaoImpl) mPaymentMethodDao).closeConnection();
    }

    private DtoSimpleAdapter populateDtoSimpleAdapter(PaymentMethodModel otherPaymentMethodModel) {

        DtoSimpleAdapter dtoSimpleAdapter = new DtoSimpleAdapter();
        dtoSimpleAdapter.setId(otherPaymentMethodModel.getId());
//        dtoSimpleAdapter.setmTitle(otherPaymentMethodModel.getName());
//        dtoSimpleAdapter.setSubtitle(String.valueOf(otherPaymentMethodModel.getAvailableCredit()));
        dtoSimpleAdapter.setIconName(CASH_NAME_ICON);

        return dtoSimpleAdapter;
    }
}
