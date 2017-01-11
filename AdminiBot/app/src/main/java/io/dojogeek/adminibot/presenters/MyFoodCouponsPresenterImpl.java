package io.dojogeek.adminibot.presenters;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.daos.OtherPaymentMethodDao;
import io.dojogeek.adminibot.dtos.DtoSimpleAdapter;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;
import io.dojogeek.adminibot.views.FoodCoupons;
import io.dojogeek.adminibot.views.MyFoodCoupons;

public class MyFoodCouponsPresenterImpl implements MyFoodCouponsPresenter {

    private MyFoodCoupons mMyFoodCoupons;
    private OtherPaymentMethodDao mOtherPaymentMethodDao;

    public MyFoodCouponsPresenterImpl(MyFoodCoupons myFoodCoupons, OtherPaymentMethodDao otherPaymentMethodDao) {
        mMyFoodCoupons = myFoodCoupons;
        mOtherPaymentMethodDao = otherPaymentMethodDao;
    }

    @Override
    public void obtainFoodCoupons() {

        List<OtherPaymentMethodModel> otherPaymentMethodModelList =
                mOtherPaymentMethodDao.getOtherPaymentMethodByType(TypePaymentMethodEnum.FOOD_COUPONS);

        List<DtoSimpleAdapter> dtoSimpleAdapterList = new ArrayList<>();

        for (OtherPaymentMethodModel otherPaymentMethodModel : otherPaymentMethodModelList) {
            DtoSimpleAdapter dtoSimpleAdapter = populateDtoSimpleAdapterFromModel(otherPaymentMethodModel);
            dtoSimpleAdapterList.add(dtoSimpleAdapter);
        }

        mMyFoodCoupons.listFoodCoupons(dtoSimpleAdapterList);
    }

    private DtoSimpleAdapter populateDtoSimpleAdapterFromModel(OtherPaymentMethodModel otherPaymentMethodModel) {

        DtoSimpleAdapter dtoSimpleAdapter = new DtoSimpleAdapter();
        dtoSimpleAdapter.setId(otherPaymentMethodModel.getId());
        dtoSimpleAdapter.setmTitle(otherPaymentMethodModel.getName() + ": " +
                otherPaymentMethodModel.getReferenceNumber());
        dtoSimpleAdapter.setSubtitle(otherPaymentMethodModel.getAvailableCredit().toString());

        return dtoSimpleAdapter;
    }
}
