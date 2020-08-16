package com.tal.generate.pdf.model;

import com.tal.generate.pdf.annotation.ParamValidator;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static com.tal.generate.pdf.enums.ScreenshotType.PNG;

/**
 * @author zhaiyarong
 */
public class Screenshot {
    /**
     * Specify screenshot type, can be either jpeg or png. Defaults to 'png'.
     */
    @ParamValidator(values = {"PNG", "JPEG"})
    private String type;
    /**
     * The quality of the image, between 0-100. Not applicable to png images.
     */
    @Min(0)
    @Max(100)
    private int quality;
    /**
     * When true, takes a screenshot of the full scrollable page. Defaults to false.
     */
    private boolean fullPage;
    /**
     * default type value
     */
    private static final String DEFAULT_TYPE = PNG.name();
    /**
     * default fullPage value
     */
    private static boolean DEFAULT_FULL_PAGE = false;

    {
        this.type = DEFAULT_TYPE;
        this.fullPage = DEFAULT_FULL_PAGE;
    }
}
