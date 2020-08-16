package com.tal.generate.pdf.exception;

import java.io.IOException;

/**
 * @author zhaiyarong
 */
public class PdfGenerateException extends IOException {

    public PdfGenerateException(String message) {
        super(message);
    }

    public PdfGenerateException(String message, Throwable cause) {
        super(message, cause);
    }


}
