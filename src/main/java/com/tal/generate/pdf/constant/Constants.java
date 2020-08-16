package com.tal.generate.pdf.constant;

/**
 * @author zhaiyarong
 */
public interface Constants {

    /**
     * PDF 后缀
     */
    String PDF_SUFFIX = ".pdf";
    /**
     * PDF配置前缀
     */
    String PDF_CONFIG_PREFIX = "pdf.process";

    /**
     * 输出结果（PDF、IMAGE、HTML），目前只支持PDF
     */
    class OutputType {

        public static final String PDF = "PDF";

        public static final String IMAGE = "IMAGE";

        public static final String HTML = "HTML";
    }
}
