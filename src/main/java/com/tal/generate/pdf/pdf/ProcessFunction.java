package com.tal.generate.pdf.pdf;

import java.io.IOException;
import java.util.List;

/**
 * @author zhaiyarong, liujiangtao
 */
public interface ProcessFunction {

    Process run(List<String> args) throws IOException;
}
