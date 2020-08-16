package com.tal.generate.pdf.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhaiyarong
 */
@Getter
@Setter
public class Launch {

    /**
     * Whether to ignore HTTPS errors during navigation
     */
    private boolean ignoreTTPSErrors;
    /**
     * Whether to run browser in headless mode. Defaults to true unless the devtools option is true
     */
    private boolean headless;
    /**
     * Sets a consistent viewport for each page
     */
    private Viewport defaultViewport;
    /**
     * Maximum time in milliseconds to wait for the browser instance to start.
     */
    private long timeout;
    /**
     * default ignoreTTPSErrors value
     */
    private static final boolean DEFAULT_IGNORE_HTTPS_ERRORS = true;
    /**
     * default headless value
     */
    private static final boolean DEFAULT_HEADLESS = true;
    /**
     * default timeout value
     */
    private static final long DEFAULT_TIMEOUT = 30000;

    {
        headless = DEFAULT_HEADLESS;
        ignoreTTPSErrors = DEFAULT_IGNORE_HTTPS_ERRORS;
        headless = DEFAULT_HEADLESS;
        defaultViewport = new Viewport();
        timeout = DEFAULT_TIMEOUT;
    }

    @Getter
    @Setter
    private class Viewport {
        /**
         * page width in pixels
         */
        private int width;
        /**
         * page height in pixels.
         */
        private int height;
        /**
         * Specify device scale factor (can be thought of as dpr)
         */
        private int deviceScaleFactor;
        /**
         * Specifies if viewport is in landscape mode.
         */
        private boolean isLandscape;
        /**
         * default width value
         */
        private static final int DEFAULT_WIDTH = 1600;
        /**
         * default height value
         */
        private static final int DEFAULT_HEIGHT = 1200;
        /**
         * default deviceScaleFactor value
         */
        private static final int DEFAULT_DEVICE_SCALE_FACTOR = 1;
        /**
         * default isLandscape value
         */
        private static final boolean DEFAULT_IS_LANDSCAPE = false;

        {
            width = DEFAULT_WIDTH;

            height = DEFAULT_HEIGHT;

            deviceScaleFactor = DEFAULT_DEVICE_SCALE_FACTOR;

            isLandscape = DEFAULT_IS_LANDSCAPE;
        }
    }
}
