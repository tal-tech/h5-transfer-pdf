package com.tal.generate.pdf.util;

import com.google.common.io.CharStreams;
import com.google.common.io.LineProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.*;

/**
 * @author zhaiyarong
 * Process执行结果流数据读取
 */
public final class ReadProcessUtils {

    static final Logger LOG = LoggerFactory.getLogger(ReadProcessUtils.class);

    private static final int CORE_SIZE = Runtime.getRuntime().availableProcessors();

    private static final int MAX_POOL_SIZE = CORE_SIZE * 2;

    private static ExecutorService threadPoolExecutor = new ThreadPoolExecutor(CORE_SIZE, MAX_POOL_SIZE,
            10L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100), new NamedThreadFactory("read-file-pool"));

    private static class ReadProcessThread implements Callable {

        Readable readable;
        boolean finished = false;

        LineProcessor processor;

        Object result = null;

        private ReadProcessThread(Readable readable, LineProcessor processor) {
            this.readable = readable;
            this.processor = processor;
        }

        @Override
        public Object call() {
            try {
                CharStreams.readLines(readable, processor);
                result = processor.getResult();
                finished = true;
            } catch (IOException e) {
                LOG.error("{}", e);
                Thread.currentThread().interrupt();
            }
            return result;
        }

        public boolean hasFinished() {
            return finished;
        }

        public Object getResult() {
            return result;
        }
    }


    /**
     * 读取Process中流的信息，超时异常退出
     *
     * @param readable
     * @param processor
     * @param timeout
     * @param unit
     * @return
     * @throws TimeoutException
     * @throws IOException
     */
    public static Object readProcessWithTimeout(final Reader readable, final LineProcessor processor, long timeout,
                                                TimeUnit unit) throws TimeoutException, IOException {
        ReadProcessThread thread = new ReadProcessThread(readable, processor);
        try {
            Future future = threadPoolExecutor.submit(thread);
            future.get(timeout, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw new IOException("read process result error");
        }
        if (!thread.hasFinished()) {
            throw new TimeoutException("Read process result did not finish within timeout");
        }

        return thread.getResult();
    }
}
