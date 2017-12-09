package io.dojogeek.adminibot.presenters;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.daos.PaymentMethodDao;
import io.dojogeek.adminibot.dtos.DtoSimpleAdapter;
import io.dojogeek.adminibot.models.PaymentMethodModel;
import io.dojogeek.adminibot.views.MyFoodCoupons;

public class MyFoodCouponsPresenterImpl implements MyFoodCouponsPresenter {

    private MyFoodCoupons mMyFoodCoupons;
    private PaymentMethodDao mPaymentMethodDao;

    public MyFoodCouponsPresenterImpl(MyFoodCoupons myFoodCoupons, PaymentMethodDao paymentMethodDao) {
        mMyFoodCoupons = myFoodCoupons;
        mPaymentMethodDao = paymentMethodDao;
    }

    @Override
    public void obtainFoodCoupons() {

        List<PaymentMethodModel> otherPaymentMethodModelList = null;

        List<DtoSimpleAdapter> dtoSimpleAdapterList = new ArrayList<>();

        for (PaymentMethodModel otherPaymentMethodModel : otherPaymentMethodModelList) {
            DtoSimpleAdapter dtoSimpleAdapter = populateDtoSimpleAdapterFromModel(otherPaymentMethodModel);
            dtoSimpleAdapterList.add(dtoSimpleAdapter);
        }

        mMyFoodCoupons.listFoodCoupons(dtoSimpleAdapterList);
    }

    private DtoSimpleAdapter populateDtoSimpleAdapterFromModel(PaymentMethodModel otherPaymentMethodModel) {

        DtoSimpleAdapter dtoSimpleAdapter = new DtoSimpleAdapter();
        dtoSimpleAdapter.setId(otherPaymentMethodModel.getId());
//        dtoSimpleAdapter.setmTitle(otherPaymentMethodModel.getName() + ": " +
//                otherPaymentMethodModel.getReferenceNumber());
//        dtoSimpleAdapter.setSubtitle(otherPaymentMethodModel.getAvailableCredit().toString());

        return dtoSimpleAdapter;
    }
}
