package com.tal.generate.pdf.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author zhaiyarong
 * PDF的format的可选项
 */
@Retention(RetentionPolicy.SOURCE)
public @interface FormatOption {

    String LETTER = "Letter";
    String LEGAL = "Legal";
    String TABLOID = "Tabloid";
    String LEDGER = "Ledger";

    String A0 = "A0";
    String A1 = "A1";
    String A2 = "A2";
    String A3 = "A3";
    String A4 = "A4";
    String A5 = "A5";
    String A6 = "A6";

}
