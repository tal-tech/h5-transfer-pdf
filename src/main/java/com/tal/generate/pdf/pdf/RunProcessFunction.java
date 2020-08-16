package com.tal.generate.pdf.pdf;

import com.google.common.base.Preconditions;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author zhaiyarong, liujiangtao
 */
public class RunProcessFunction implements ProcessFunction {

    File workingDirectory;

    /**
     * 获取Process
     *
     * @param args
     * @return
     * @throws IOException
     */
    @Override
    public Process run(List<String> args) throws IOException {
        Preconditions.checkNotNull(args, "Arguments must not be null");
        Preconditions.checkArgument(!args.isEmpty(), "No arguments specified");

        ProcessBuilder builder = new ProcessBuilder(args);
        if (workingDirectory != null) {
            builder.directory(workingDirectory);
        }
        builder.redirectErrorStream(true);

        return builder.start();
    }

}
