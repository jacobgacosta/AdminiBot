package io.dojogeek.adminibot.validators;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.dtos.DebitCardDto;
import io.dojogeek.validator.DataValidator;
import io.dojogeek.validator.RequiredValidator;

public class DebitCardValidator extends io.dojogeek.validator.Validator {

    private DebitCardDto mDebitCardDto;
    private Context mContext;

    public DebitCardValidator(Context context, DebitCardDto debitCard) {
        mDebitCardDto = debitCard;
        mContext = context;
    }

    @Override
    protected Map<String, DataValidator> getValidations() {
        Map<String, DataValidator> validations = new HashMap<>();
        validations.put("name", new RequiredValidator.RequiredValidatorBuilder()
                .valueToValidate(mDebitCardDto.getName()).errorMessage(mContext.getResources().getString(R.string.msg_error_empty_name)).build());
        validations.put("number", new RequiredValidator.RequiredValidatorBuilder()
                .valueToValidate(mDebitCardDto.getNumber()).errorMessage(mContext.getResources().getString(R.string.msg_error_empty_card_number)).build());
        validations.put("amount", new RequiredValidator.RequiredValidatorBuilder()
                .valueToValidate(mDebitCardDto.getAmount()).errorMessage(mContext.getResources().getString(R.string.msg_error_empty_card_amount)).build());

        return validations;
    }
}
