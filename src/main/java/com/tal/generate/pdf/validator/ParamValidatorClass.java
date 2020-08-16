package com.tal.generate.pdf.validator;

import com.tal.generate.pdf.annotation.ParamValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * 验证传参
 *
 * @author zhaiyarong
 */
public class ParamValidatorClass implements ConstraintValidator<ParamValidator, Object> {

    private String[] values;

    @Override
    public void initialize(ParamValidator constraintAnnotation) {
        this.values = constraintAnnotation.values();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.stream(values).anyMatch(v -> value == null || v.equals(value));
    }
}

