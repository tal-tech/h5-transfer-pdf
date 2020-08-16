package com.tal.generate.pdf.pdf;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.io.LineProcessor;
import com.google.gson.Gson;
import com.tal.generate.pdf.config.PdfConfig;
import com.tal.generate.pdf.util.ReadProcessUtils;
import com.tal.generate.pdf.util.WaitForProcessUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author zhaiyarong, liujiangtao
 */
public abstract class GenerateCommon {

    final String path;

    final ProcessFunction processFunction;

    long waitForTimeout;

    long readStreamTimeout;

    private static final Logger LOG = LoggerFactory.getLogger(GenerateCommon.class);

    public GenerateCommon(@NonNull String path) {
        this(path, new RunProcessFunction());
    }

    protected GenerateCommon(@NonNull String path, @NonNull ProcessFunction runFunc) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(path));
        this.processFunction = checkNotNull(runFunc);
        this.path = path;
        this.waitForTimeout = PdfConfig.DEFAULT_WAIT_FOR_TIMEOUT;
        this.readStreamTimeout = PdfConfig.DEFAULT_READ_STREAM_TIMEOUT;
    }

    protected GenerateCommon(@NonNull String path, @NonNull ProcessFunction runFunc, @NonNull Long waitForTimeout, @NonNull Long readStreamTimeout) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(path));
        this.processFunction = checkNotNull(runFunc);
        this.path = path;
        this.waitForTimeout = waitForTimeout;
        this.readStreamTimeout = readStreamTimeout;
    }

    /**
     * 读取脚本执行退出结果
     *
     * @param p
     * @throws IOException
     */
    protected void waitForOnError(Process p) throws IOException {
        waitForOnError(p, waitForTimeout);
    }

    /**
     * 读取脚本执行的流数据
     *
     * @param p
     * @return
     * @throws IOException
     */
    protected List<String> readProcessOnError(Process p) throws IOException {
        return readProcessOnError(p, readStreamTimeout);
    }

    /**
     * 读取执行的流数据， 超时抛出异常
     *
     * @param p
     * @param timeout
     * @return
     * @throws IOException
     */
    protected List<String> readProcessOnError(Process p, Long timeout) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8))) {
            List<String> result = (List<String>) ReadProcessUtils.readProcessWithTimeout(bufferedReader,
                    new LineProcessor<List<String>>() {
                        List<String> result = new ArrayList<>();

                        @Override
                        public boolean processLine(String s) throws IOException {
                            result.add(s);
                            return true;
                        }

                        @Override
                        public List<String> getResult() {
                            return result;
                        }
                    }, timeout, TimeUnit.MILLISECONDS);
            Gson gson = new Gson();
            LOG.debug("process exec result: {}", gson.toJson(result));

            return result;
        } catch (TimeoutException e) {
            throw new IOException("Timed out read process's input stream to finish");
        } finally {
            if (p.getInputStream() != null) {
                p.getInputStream().close();
            }
            if (p.getErrorStream() != null) {
                p.getErrorStream().close();
            }
            if (p.getOutputStream() != null) {
                p.getOutputStream().close();
            }
        }
    }

    /**
     * 读取脚本执行退出结果， 超时抛出异常
     *
     * @param p
     * @param timeout
     * @throws IOException
     */
    protected void waitForOnError(Process p, Long timeout) throws IOException {
        try {
            if (WaitForProcessUtils.waitForWithTimeout(p, timeout, TimeUnit.MILLISECONDS) != 0) {
                throw new IOException("generate command returned non-zero exit status. please check stdout.");
            }
        } catch (TimeoutException e) {
            throw new IOException("Timed out waiting for command to finish");
        }
    }

    /**
     * 生成执行脚本的全命令（脚本路径+传参）
     *
     * @param args
     * @return
     * @throws IOException
     */
    public ImmutableList<String> path(List<String> args) throws IOException {
        return ImmutableList.<String>builder().add(path).addAll(args).build();
    }

    /**
     * 执行脚本，并获取返回结果
     *
     * @param args
     * @return
     * @throws IOException
     */
    public List<String> run(List<String> args) throws IOException {
        checkNotNull(args);
        Process p = processFunction.run(path(args));
        assert (p != null);
        try {
            List<String> result = readProcessOnError(p);

            waitForOnError(p);

            return result;
        } finally {
            p.destroy();
        }
    }

    /**
     * 获取脚本的路径
     *
     * @return
     */
    public String getPath() {
        return path;
    }
}

