package com.tal.generate.pdf.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhaiyarong
 */
@Getter
@Setter
public class Clip {
    /**
     * x-coordinate of top-left corner of clip area
     */
    private int x;
    /**
     * y-coordinate of top-left corner of clip area
     */
    private int y;
    /**
     * width of clipping area
     */
    private int width;
    /**
     * height of clipping area
     */
    private int height;
    /**
     * Hides default white background and allows capturing screenshots with transparency. Defaults to false.
     */
    private boolean omitBackground;
    /**
     * The encoding of the image, can be either base64 or binary. Defaults to binary
     */
    private String encoding;
    /**
     * default omitBackground
     */
    private static final boolean DEFAULT_OMIT_BACKGROUND = false;
    /**
     * default encoding
     */
    private static final String DEFAULT_ENCODING = "binary";

    public Clip() {
        this.omitBackground = DEFAULT_OMIT_BACKGROUND;
        this.encoding = DEFAULT_ENCODING;
    }
}
