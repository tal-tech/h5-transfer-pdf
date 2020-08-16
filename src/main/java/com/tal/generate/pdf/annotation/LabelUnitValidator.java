package com.tal.generate.pdf.annotation;

import com.tal.generate.pdf.validator.LabelUnitValidatorClass;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author zhaiyarong
 * Label Unit参数验证注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Constraint(validatedBy = LabelUnitValidatorClass.class)
public @interface LabelUnitValidator {

    String value() default "";

    String message() default "The values must meet the format requirements";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
