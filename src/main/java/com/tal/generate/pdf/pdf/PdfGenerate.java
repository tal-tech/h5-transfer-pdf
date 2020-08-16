package com.tal.generate.pdf.pdf;

import java.io.IOException;
import java.util.List;

/**
 * @author zhaiyarong
 * PDF生成脚本
 */
public class PdfGenerate extends GenerateCommon {

    public PdfGenerate(String path, ProcessFunction runFunc, Long waitForTimeout, Long readStreamTimeout) {
        super(path, runFunc, waitForTimeout, readStreamTimeout);
    }

    @Override
    public List<String> run(List<String> args) throws IOException {
        return super.run(args);
    }
}
