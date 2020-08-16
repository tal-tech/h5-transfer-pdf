package com.tal.generate.pdf.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import static com.tal.generate.pdf.constant.Constants.PDF_CONFIG_PREFIX;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = PDF_CONFIG_PREFIX)
@Configuration
public class PdfConfig {
    /**
     * Process执行wait for超时时间
     */
    private Long waitForTimeout;
    /**
     * Process读取流数据超时时间
     */
    private Long readStreamTimeout;
    /**
     * 脚本路径
     */
    private String scriptPath;
    /**
     * node路径
     */
    private String nodePath;

    /**
     * 默认waitForTimeout时间
     */
    public static final Long DEFAULT_WAIT_FOR_TIMEOUT = 100000L;
    /**
     * 默认readStreamTimeout时间
     */
    public static final Long DEFAULT_READ_STREAM_TIMEOUT = 1000000L;
    /**
     * 默认PDF脚本执行路径
     */
    public static final String DEFAULT_PDF_SCRIPT_PATH = "classpath:static/render-pdf.js";
    /**
     * 默认Node环境路径
     */
    public static final String DEFAULT_PDF_NODE_PATH = "node";

    {
        waitForTimeout = DEFAULT_WAIT_FOR_TIMEOUT;
        readStreamTimeout = DEFAULT_READ_STREAM_TIMEOUT;
        scriptPath = DEFAULT_PDF_SCRIPT_PATH;
        nodePath = DEFAULT_PDF_NODE_PATH;
    }

}
