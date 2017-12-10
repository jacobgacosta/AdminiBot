package io.dojogeek.adminibot.utiltest;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.enums.ExpenseTypeEnum;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.models.CardDetailModel;
import io.dojogeek.adminibot.models.CashModel;
import io.dojogeek.adminibot.models.ExpenseBankCardModel;
import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.models.ExpenseOtherPaymentMethodModel;
import io.dojogeek.adminibot.models.ExpenseTypeModel;
import io.dojogeek.adminibot.models.IncomeBankCardModel;
import io.dojogeek.adminibot.models.IncomeModel;
import io.dojogeek.adminibot.models.IncomeTypePaymentMethodModel;
import io.dojogeek.adminibot.models.PaymentMethodModel;
import io.dojogeek.adminibot.models.TypePaymentMethodModel;
import io.dojogeek.adminibot.models.UserModel;
import io.dojogeek.adminibot.utils.DateUtils;

public class ModelsFactory {

//    private static PodamFactory factory = new PodamFactoryImpl();

    public static UserModel createUserModel() {

        UserModel userModel = new UserModel();
        userModel.setName("Jacob");
        userModel.setLastName("Guzman");
        userModel.setEmail("jgacosta@dojogeek.io");

        return userModel;
    }

    public static TypePaymentMethodModel createTypePaymentMethodModel() {

//        TypePaymentMethodModel typePaymentMethodModel = createTypePaymentMethodModel(R.string.payment_methods_food_coupons,
//                R.string.payment_methods_food_coupons_description);

        return null;
    }

    public static TypePaymentMethodModel createTypePaymentMethodModel(int name, int description) {
        TypePaymentMethodModel typePaymentMethodModel = new TypePaymentMethodModel();
        typePaymentMethodModel.name = name;
        typePaymentMethodModel.description = description;

        return typePaymentMethodModel;
    }

    public static TypePaymentMethodModel[] createTypesPaymentMethods() {

//        TypePaymentMethodModel[] typesPaymentMethods = {createTypePaymentMethodModel(R.string.payment_methods_food_coupons,
//                R.string.payment_methods_food_coupons_description), createTypePaymentMethodModel(R.string.payment_methods_cash,
//                R.string.payment_methods_cash_description), createTypePaymentMethodModel(R.string.payment_methods_cheque,
//                R.string.payment_methods_cheque_description)};
//
        return null;
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

        PaymentMethodModel paymentMethodModel = new PaymentMethodModel();
        paymentMethodModel.setName("food coupon test");
        paymentMethodModel.setType(TypePaymentMethodEnum.CASH);

        return paymentMethodModel;
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

        IncomeModel incomeModel = new IncomeModel();
        incomeModel.setName("Income name test");
        incomeModel.setTotalAmount(new BigDecimal(45900));
//        incomeModel.setCreatedAt(DateTime.now());
        incomeModel.setNextEntry(DateTime.now());

        return incomeModel;
    }


    public static BankCardModel createBankCardModel() {

        BankCardModel bankCardModel = new BankCardModel();
        bankCardModel.setName("SANTANDER ZERO");
        bankCardModel.setNumber("1234567891234567");
        bankCardModel.setBankId(1);
        bankCardModel.setBrand("VISA");
        bankCardModel.setCardType("CREDIT_CARD");
        bankCardModel.setAvailableCredit(new BigDecimal(20000));

        return bankCardModel;
    }


    public static CardDetailModel createCardDetailModel() {

        CardDetailModel cardDetailModel = createCardDetailModel(7632.90, 8378.00,
                DateUtils.getCurrentData(), DateUtils.getCurrentData(), 4);

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

    public static IncomeTypePaymentMethodModel createIncomePaymentMethod() {

        IncomeTypePaymentMethodModel incomeOtherPaymentMethodModel = new IncomeTypePaymentMethodModel();
        incomeOtherPaymentMethodModel.setIncomeId(1);
        incomeOtherPaymentMethodModel.setPaymentMethodId(2);
        incomeOtherPaymentMethodModel.setAmount(new BigDecimal("76723.90"));

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

    public static CashModel createCashModel() {

        CashModel cashModel = new CashModel();
        cashModel.setAlias("this a test alias");
        cashModel.setAmount(new BigDecimal(98723.89));

        return cashModel;
    }
}
