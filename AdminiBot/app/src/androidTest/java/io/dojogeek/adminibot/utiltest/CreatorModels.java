package io.dojogeek.adminibot.utiltest;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.R;
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
import io.dojogeek.adminibot.models.ExpenseBankCardModel;
import io.dojogeek.adminibot.models.IncomeBankCardModel;
import io.dojogeek.adminibot.models.OtherPaymentMethodModel;
import io.dojogeek.adminibot.models.PaymentMethodModel;
import io.dojogeek.adminibot.models.TypePaymentMethodModel;
import io.dojogeek.adminibot.models.UserModel;
import io.dojogeek.adminibot.utils.DateUtils;

public class CreatorModels {

    public static UserModel createUserModel() {

        UserModel userModel = new UserModel();
        userModel.setName("Jacob");
        userModel.setLastName("Guzman");
        userModel.setEmail("jgacosta@dojogeek.io");

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
        expenseTypeModel.setName(expenseTypeEnum);
        expenseTypeModel.setDescription(description);

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

        List<ExpenseOtherPaymentMethodModel> otherPaymentMethodModels = createExpenseOtherPaymentMethodModelList();

        List<ExpenseBankCardModel> expenseBankCardModels = createExpenseBankCardModelList();

        return createExpenseModel("Expense type test", 567.90, DateUtils.getCurrentData(), DateUtils.getCurrentData(),
                1, 20, otherPaymentMethodModels, expenseBankCardModels);
    }

    public static ExpenseModel createExpenseModel(String description, double amount, String dateExpediture,
                                           String nextExit, long expenseTypeId, long userId,
                                                  List<ExpenseOtherPaymentMethodModel> otherPaymentMethodModels,
                                                  List<ExpenseBankCardModel> expenseBankCardModels) {

        ExpenseModel expenseModel = new ExpenseModel();
        expenseModel.setDescription(description);
        expenseModel.setAmount(amount);
        expenseModel.setDateExpediture(dateExpediture);
        expenseModel.setNextExit(nextExit);
        expenseModel.setExpenseTypeId(expenseTypeId);
        expenseModel.setUserId(userId);
        expenseModel.setOtherPaymentMethodModels(otherPaymentMethodModels);
        expenseModel.setExpenseBankCardModels(expenseBankCardModels);

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
        incomeModel.setDescription(description);
        incomeModel.setAmount(amount);
        incomeModel.setDate(date);
        incomeModel.setNextDate(nextDate);
        incomeModel.setUserId(userId);

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
        otherPaymentMethodModel.setAvailableCredit(availableCredit);
        otherPaymentMethodModel.setName(name);
        otherPaymentMethodModel.setReferenceNumber(referenceNumber);
        otherPaymentMethodModel.setTypePaymentMethod(typePaymentMethod);
        otherPaymentMethodModel.setUserId(userId);

        return otherPaymentMethodModel;
    }

    public static ExpenseBankCardModel createMovementExpenseBankCardModel() {

        ExpenseBankCardModel expenseBankCardModel = createMovementExpenseBankCardModel(2340,
                2, DateUtils.getCurrentData(), "test description", 2);

        return expenseBankCardModel;
    }

    public static ExpenseBankCardModel createMovementExpenseBankCardModel(double amount, long bankCardId,
                                                                                  String date, String description,
                                                                                  long expenseId) {

        ExpenseBankCardModel expenseBankCardModel = new ExpenseBankCardModel();
        expenseBankCardModel.setAmount(amount);
        expenseBankCardModel.setBankCardId(bankCardId);
        expenseBankCardModel.setDate(date);
        expenseBankCardModel.setDescription(description);
        expenseBankCardModel.setExpenseId(expenseId);

        return expenseBankCardModel;
    }

    public static IncomeBankCardModel createMovementIncomeBankCardModel(){

        IncomeBankCardModel incomeBankCardModel =
                createMovementIncomeBankCardModel(3409.80, 2, DateUtils.getCurrentData(), "Test description", 2);

        return incomeBankCardModel;
    }

    public static IncomeBankCardModel createMovementIncomeBankCardModel(double amount, long bankCardId,
                                                                                String date, String description,
                                                                                long incomeId) {

        IncomeBankCardModel incomeBankCardModel = new IncomeBankCardModel();
        incomeBankCardModel.setAmount(amount);
        incomeBankCardModel.setBankCardId(bankCardId);
        incomeBankCardModel.setDate(date);
        incomeBankCardModel.setDescription(description);
        incomeBankCardModel.setIncomeId(incomeId);

        return incomeBankCardModel;
    }

    public static ExpenseOtherPaymentMethodModel createExpenseOtherPaymentMethodModel() {

        ExpenseOtherPaymentMethodModel expenseOtherPaymentMethodModel =
                createExpenseOtherPaymentMethodModel(234.90, 2, 2);

        return expenseOtherPaymentMethodModel;
    }

    public static ExpenseOtherPaymentMethodModel createExpenseOtherPaymentMethodModel(double amount, long expenseId,
                                                                               long otherPaymentMethodId) {

        ExpenseOtherPaymentMethodModel expenseOtherPaymentMethodModel = new ExpenseOtherPaymentMethodModel();
        expenseOtherPaymentMethodModel.setAmount(amount);
        expenseOtherPaymentMethodModel.setExpenseId(expenseId);
        expenseOtherPaymentMethodModel.setOtherPaymentMethodId(otherPaymentMethodId);

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
        incomeOtherPaymentMethodModel.setAmount(amount);
        incomeOtherPaymentMethodModel.setIncomeId(incomeId);
        incomeOtherPaymentMethodModel.setOtherPaymentMethodId(otherPaymentMethodId);

        return incomeOtherPaymentMethodModel;
    }

    public static List<ExpenseOtherPaymentMethodModel> createExpenseOtherPaymentMethodModelList() {

        List<ExpenseOtherPaymentMethodModel> otherPaymentMethodModels = new ArrayList<>();
        otherPaymentMethodModels.add(createExpenseOtherPaymentMethodModel());
        otherPaymentMethodModels.add(createExpenseOtherPaymentMethodModel());

        return otherPaymentMethodModels;
    }

    public static List<ExpenseBankCardModel> createExpenseBankCardModelList() {

        List<ExpenseBankCardModel> expenseBankCardModels = new ArrayList<>();
        expenseBankCardModels.add(createMovementExpenseBankCardModel());
        expenseBankCardModels.add(createMovementExpenseBankCardModel());

        return expenseBankCardModels;
    }
}
