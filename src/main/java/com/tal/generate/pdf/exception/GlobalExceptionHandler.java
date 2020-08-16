package com.tal.generate.pdf.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.tal.generate.pdf.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author zhaiyarong
 * 异常捕获
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Result messageNotReadable(Exception exception) {
        InvalidFormatException formatException = (InvalidFormatException) exception.getCause();
        List<JsonMappingException.Reference> e = formatException.getPath();
        String fieldName = "";
        for (JsonMappingException.Reference reference : e) {
            fieldName = reference.getFieldName();
        }
        LOG.error("Parameter type mismatch: " + exception);
        return Result.buildFailResult(fieldName + ": parameter type mismatch");
    }

    @ExceptionHandler(PdfGenerateException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Result pdfGenerateException(Exception e) {
        LOG.error("error：", e);
        return Result.buildFailResult(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Result validException(MethodArgumentNotValidException e) {
        LOG.error("error: ", e);
        BindingResult bindingResult = e.getBindingResult();

        return getBindResult(bindingResult);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Result bindException(BindException e) {
        LOG.error("error", e);
        BindingResult bindingResult = e.getBindingResult();

        return getBindResult(bindingResult);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Result handleConstrainViolationException(ConstraintViolationException e) {
        LOG.error("error: ", e);

        return Result.buildFailResult(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Result allException(Exception e) {
        LOG.error("error：", e);
        return Result.buildFailResult("The service is not available. Please try again later");
    }

    private Result getBindResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            String field = fieldErrors.stream().findFirst().get().getField();
            String message = fieldErrors.stream().findFirst().get().getDefaultMessage();
            return Result.buildFailResult("Please check  parameters: " + field + message);
        }
        return Result.buildFailResult("Please check  parameters");
    }

}
