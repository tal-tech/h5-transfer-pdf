package com.tal.generate.pdf.model;

import com.tal.generate.pdf.annotation.LabelUnitValidator;
import com.tal.generate.pdf.annotation.ParamValidator;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import static com.tal.generate.pdf.annotation.FormatOption.*;

/**
 * @author zhaiyarong
 */
@Getter
@Setter
public class Pdf {
    /**
     * Scale of the webpage rendering. Defaults to 1. Scale amount must be between 0.1 and 2.
     */
    @DecimalMin(value = "0.1", message = "Scale amount must be between 0.1 and 2")
    @DecimalMax(value = "2", message = "Scale amount must be between 0.1 and 2")
    private Double scale;
    /**
     * Print background graphics. Defaults to false
     */
    private boolean printBackground;
    /**
     * Display header and footer. Defaults to false
     */
    private boolean displayHeaderFooter;
    /**
     * HTML template for the print header. Should be valid HTML markup with following classes used to inject printing values into them:
     * date formatted print date
     * title document title
     * url document location
     * pageNumber current page number
     * totalPages total pages in the document
     */
    private String headerTemplate;
    /**
     * HTML template for the print footer. Should use the same format as the headerTemplate
     */
    private String footerTemplate;
    /**
     * Paper orientation. Defaults to false.
     */
    private boolean landscape;
    /**
     * Paper ranges to print, e.g., '1-5, 8, 11-13'. Defaults to the empty string, which means print all pages.
     */
    private String pageRanges;
    /**
     * Paper format. If set, takes priority over width or height options. Defaults to 'Letter'.
     */
    @ParamValidator(values = {LETTER, LEGAL, TABLOID, LEDGER, A0, A1, A2, A3, A4, A5, A6}, message = "The format must be one of Letter, Tabloid, Ledger, A0, A1, A2, A3, A4, and A5")
    private String format;
    /**
     * Paper width, accepts values labeled with units
     */
    @LabelUnitValidator(message = "The unit must be one of px, in, cm and mm")
    private String width;
    /**
     * Paper height, accepts values labeled with units
     */
    @LabelUnitValidator(message = "The unit must be one of px, in, cm and mm")
    private String height;
    /**
     * Create PDF in a single page
     */
//    private boolean fullPage;
    /**
     * Paper margins, defaults to none
     */
    @Valid
    private Margin margin;

    /**
     * Give any CSS @page size declared in the page priority over what is declared in width and height or format options. Defaults to false, which will scale the content to fit the paper size.
     */
    private boolean preferCSSPageSize;
    /**
     * default scale value
     */
    private static final double DEFAULT_SCALE = 1;
    /**
     * default printBackground value
     */
    private static final boolean DEFAULT_PRINT_BACKGROUND = true;
    /**
     * default displayHeaderFooter value
     */
    private static final boolean DEFAULT_DISPLAY_HEADER_FOOTER = false;
    /**
     * default format value
     */
    private static final String DEFAULT_FORMAT = LEDGER;
    /**
     * default preferCSSPageSize value
     */
    private static final boolean DEFAULT_PREFER_CSS_PAGE_SIZE = false;
    /**
     * default landscape value
     */
    private static final boolean DEFAULT_LANDSCAPE = false;
    /**
     * default gs value
     */
    private static final boolean DEFAULT_GS = false;

    {
        this.scale = DEFAULT_SCALE;
        this.printBackground = DEFAULT_PRINT_BACKGROUND;
        this.displayHeaderFooter = DEFAULT_DISPLAY_HEADER_FOOTER;
        this.format = DEFAULT_FORMAT;
        this.preferCSSPageSize = DEFAULT_PREFER_CSS_PAGE_SIZE;
        this.landscape = DEFAULT_LANDSCAPE;
    }

}
