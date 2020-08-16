package com.tal.generate.pdf.annotation;

import com.tal.generate.pdf.validator.ParamValidatorClass;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author zhaiyarong
 * 参数验证注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Constraint(validatedBy = ParamValidatorClass.class)
public @interface ParamValidator {

    String[] values();

    String message() default "The parameter must be several preset values";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
