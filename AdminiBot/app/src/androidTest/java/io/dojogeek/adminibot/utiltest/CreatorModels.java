package io.dojogeek.adminibot.utiltest;

import java.util.Date;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.components.PaymentMethod;
import io.dojogeek.adminibot.enums.CardTypeEnum;
import io.dojogeek.adminibot.enums.ExpenseTypeEnum;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.models.CardDetailModel;
import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.models.ExpenseOtherPaymentMethodModel;
import io.dojogeek.adminibot.models.ExpenseTypeModel;
import io.dojogeek.adminibot.models.IncomeModel;
import io.dojogeek.adminibot.models.IncomeOtherPaymentMethodModel;
import io.dojogeek.adminibot.models.MovementExpenseBankCardModel;
import io.dojogeek.adminibot.models.MovementIncomeBankCardModel;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;
import io.dojogeek.adminibot.models.PaymentMethodModel;
import io.dojogeek.adminibot.models.TypePaymentMethodModel;
import io.dojogeek.adminibot.models.UserModel;
import io.dojogeek.adminibot.utils.DateUtils;

public class CreatorModels {

    public static UserModel createUserModel() {

        UserModel userModel = new UserModel();
        userModel.name = "Jacob";
        userModel.lastName = "Guzman";
        userModel.email = "jgacosta@dojogeek.io";

        return userModel;
    }

    public static TypePaymentMethodModel createTypePaymentMethodModel() {

        TypePaymentMethodModel typePaymentMethodModel = createTypePaymentMethodModel(R.string.payment_methods_food_coupons,
                R.string.payment_methods_food_coupons_description);

        return typePaymentMethodModel;
    }

    public static TypePaymentMethodModel createTypePaymentMethodModel(int name, int description) {
        TypePaymentMethodModel typePaymentMethodModel = new TypePaymentMethodModel();
        typePaymentMethodModel.name = name;
        typePaymentMethodModel.description = description;

        return typePaymentMethodModel;
    }

    public static TypePaymentMethodModel[] createTypesPaymentMethods() {

        TypePaymentMethodModel[] typesPaymentMethods = {createTypePaymentMethodModel(R.string.payment_methods_food_coupons,
                R.string.payment_methods_food_coupons_description), createTypePaymentMethodModel(R.string.payment_methods_cash,
                R.string.payment_methods_cash_description), createTypePaymentMethodModel(R.string.payment_methods_cheque,
                R.string.payment_methods_cheque_description)};

        return typesPaymentMethods;
    }

    public static ExpenseTypeModel createExpenseTypeModel() {
        ExpenseTypeModel expenseTypeModel = createExpenseTypeModel(ExpenseTypeEnum.ALCOHOL,
                ExpenseTypeEnum.ALCOHOL.getDescription());

        return expenseTypeModel;
    }

    public static ExpenseTypeModel createExpenseTypeModel(ExpenseTypeEnum expenseTypeEnum, String description) {

        ExpenseTypeModel expenseTypeModel = new ExpenseTypeModel();
        expenseTypeModel.name = expenseTypeEnum;
        expenseTypeModel.description = description;

        return expenseTypeModel;
    }

    public static PaymentMethodModel createPaymentMethodModel() {

        PaymentMethodModel paymentMethodModel = createPaymentMethodModel("Debt payment", "AD7689832D", 1, 1650, 4);

        return paymentMethodModel;
    }

    public static PaymentMethodModel createPaymentMethodModel(String name, String reference,
                                                              long typePaymentMethodId,
                                                              double availableCredit, int userId) {

        PaymentMethodModel paymentMethod = new PaymentMethodModel();
        paymentMethod.name = name;
        paymentMethod.reference = reference;
        paymentMethod.typePaymentMethod = typePaymentMethodId;
        paymentMethod.availabreCredit = availableCredit;
        paymentMethod.userId = userId;

        return paymentMethod;
    }

    public static ExpenseModel createExpenseModel() {

        return createExpenseModel("Expense type test", 567.90, DateUtils.getCurrentData(), DateUtils.getCurrentData(),
                1, 20);
    }

    public static ExpenseModel createExpenseModel(String description, double amount, String dateExpediture,
                                           String nextExit, long expenseTypeId, long userId) {

        ExpenseModel expenseModel = new ExpenseModel();
        expenseModel.description = description;
        expenseModel.amount = amount;
        expenseModel.dateExpediture = dateExpediture;
        expenseModel.nextExit = nextExit;
        expenseModel.expenseTypeId = expenseTypeId;
        expenseModel. userId = userId;

        return expenseModel;
    }

    public static IncomeModel createIncomeModel() {

        IncomeModel incomeModel = createIncomeModel("Test description", 24506.90, DateUtils.getCurrentData(), DateUtils.getCurrentData(),
                1);

        return incomeModel;
    }

    public static IncomeModel createIncomeModel(String description, double amount, String date, String nextDate,
                                                long userId) {

        IncomeModel incomeModel = new IncomeModel();
        incomeModel.description = description;
        incomeModel.amount = amount;
        incomeModel.date = date;
        incomeModel.nextDate = nextDate;
        incomeModel.userId = userId;

        return incomeModel;
    }

    public static BankCardModel createBankCardModel() {
        BankCardModel bankCardModel = createBankCardModel("Bancomer", "123456789012345678", 2, 2,
                24000.00, CardTypeEnum.CREDIT_CARD, 1);

        return bankCardModel;
    }

    public static BankCardModel createBankCardModel(String name, String number, long bankId, long trademarkId,
                                                    double availableCredit, CardTypeEnum cardType, long userId) {

        BankCardModel bankCardModel = new BankCardModel();
        bankCardModel.setName(name);
        bankCardModel.setNumber(number);
        bankCardModel.setBankId(bankId);
        bankCardModel.setTrademarkId(trademarkId);
        bankCardModel.setAvailableCredit(availableCredit);
        bankCardModel.setCardType(cardType);
        bankCardModel.setUserId(userId);

        return bankCardModel;
    }

    public static CardDetailModel createCardDetailModel() {

        CardDetailModel cardDetailModel = createCardDetailModel(7632.90, 8378.00, DateUtils.getCurrentData(), DateUtils.getCurrentData(),
                4);

        return cardDetailModel;
    }

    public static CardDetailModel createCardDetailModel(double creditLimit, double currentBalance,
                                                        String cuttoffDate, String payDayLimit, long bankCardId) {

        CardDetailModel cardDetailModel = new CardDetailModel();
        cardDetailModel.setCreditLimit(creditLimit);
        cardDetailModel.setCurrentBalance(currentBalance);
        cardDetailModel.setCuttoffDate(cuttoffDate);
        cardDetailModel.setPayDayLimit(payDayLimit);
        cardDetailModel.setBankCardId(bankCardId);

        return cardDetailModel;
    }

    public static OtherPaymentMethodModel createOtherPaymentMethodModel() {

        OtherPaymentMethodModel otherPaymentMethodModel = createOtherPaymentMethodModel(24000.00,
                "test other payment method", "4567AKI90843", TypePaymentMethodEnum.FOOD_COUPONS, 1);

        return otherPaymentMethodModel;
    }

    public static OtherPaymentMethodModel createOtherPaymentMethodModel(double availableCredit, String name,
                                                                        String referenceNumber,
                                                                        TypePaymentMethodEnum typePaymentMethod,
                                                                        long userId) {

        OtherPaymentMethodModel otherPaymentMethodModel = new OtherPaymentMethodModel();
        otherPaymentMethodModel.availableCredit = availableCredit;
        otherPaymentMethodModel.name = name;
        otherPaymentMethodModel.referenceNumber = referenceNumber;
        otherPaymentMethodModel.typePaymentMethod = typePaymentMethod;
        otherPaymentMethodModel.userId = userId;

        return otherPaymentMethodModel;
    }

    public static MovementExpenseBankCardModel createMovementExpenseBankCardModel() {

        MovementExpenseBankCardModel movementExpenseBankCardModel = createMovementExpenseBankCardModel(2340,
                2, DateUtils.getCurrentData(), "test description", 2);

        return movementExpenseBankCardModel;
    }

    public static MovementExpenseBankCardModel createMovementExpenseBankCardModel(double amount, long bankCardId,
                                                                                  String date, String description,
                                                                                  long expenseId) {

        MovementExpenseBankCardModel movementExpenseBankCardModel = new MovementExpenseBankCardModel();
        movementExpenseBankCardModel.amount = amount;
        movementExpenseBankCardModel.bankCardId = bankCardId;
        movementExpenseBankCardModel.date = date;
        movementExpenseBankCardModel.description = description;
        movementExpenseBankCardModel.expenseId = expenseId;

        return movementExpenseBankCardModel;
    }

    public static MovementIncomeBankCardModel createMovementIncomeBankCardModel(){

        MovementIncomeBankCardModel movementIncomeBankCardModel =
                createMovementIncomeBankCardModel(3409.80, 2, DateUtils.getCurrentData(), "Test description", 2);

        return movementIncomeBankCardModel;
    }

    public static MovementIncomeBankCardModel createMovementIncomeBankCardModel(double amount, long bankCardId,
                                                                                String date, String description,
                                                                                long incomeId) {

        MovementIncomeBankCardModel movementIncomeBankCardModel = new MovementIncomeBankCardModel();
        movementIncomeBankCardModel.amount = amount;
        movementIncomeBankCardModel.bankCardId = bankCardId;
        movementIncomeBankCardModel.date = date;
        movementIncomeBankCardModel.description = description;
        movementIncomeBankCardModel.incomeId = incomeId;

        return movementIncomeBankCardModel;
    }

    public static ExpenseOtherPaymentMethodModel createExpenseOtherPaymentMethodModel() {

        ExpenseOtherPaymentMethodModel expenseOtherPaymentMethodModel =
                createExpenseOtherPaymentMethodModel(234.90, 2, 2);

        return expenseOtherPaymentMethodModel;
    }

    public static ExpenseOtherPaymentMethodModel createExpenseOtherPaymentMethodModel(double amount, long expenseId,
                                                                               long otherPaymentMethodId) {

        ExpenseOtherPaymentMethodModel expenseOtherPaymentMethodModel = new ExpenseOtherPaymentMethodModel();
        expenseOtherPaymentMethodModel.amount = amount;
        expenseOtherPaymentMethodModel.expenseId = expenseId;
        expenseOtherPaymentMethodModel.otherPaymentMethodId = otherPaymentMethodId;

        return expenseOtherPaymentMethodModel;
    }

    public static IncomeOtherPaymentMethodModel createIncomeOtherPaymentMethodModel() {

        IncomeOtherPaymentMethodModel incomeOtherPaymentMethodModel =
                createIncomeOtherPaymentMethodModel(2345.90, 2, 2);

        return incomeOtherPaymentMethodModel;
    }

    public static IncomeOtherPaymentMethodModel createIncomeOtherPaymentMethodModel(double amount, long incomeId,
                                                                             long otherPaymentMethodId) {

        IncomeOtherPaymentMethodModel incomeOtherPaymentMethodModel = new IncomeOtherPaymentMethodModel();
        incomeOtherPaymentMethodModel.amount = amount;
        incomeOtherPaymentMethodModel.incomeId = incomeId;
        incomeOtherPaymentMethodModel.otherPaymentMethodId = otherPaymentMethodId;

        return incomeOtherPaymentMethodModel;
    }
}
