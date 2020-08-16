package com.tal.generate.pdf.validator;

import com.tal.generate.pdf.annotation.LabelUnitValidator;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 验证LabelUnit
 *
 * @author zhaiyarong
 */
public class LabelUnitValidatorClass implements ConstraintValidator<LabelUnitValidator, String> {

    @Override
    public void initialize(LabelUnitValidator constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(value)) {
            return false;
        }
        String regex = "^(([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])|(0{1}))(cm|px|in|cm|mm)$";
        boolean result = value.matches(regex);
        return result;
    }
}
