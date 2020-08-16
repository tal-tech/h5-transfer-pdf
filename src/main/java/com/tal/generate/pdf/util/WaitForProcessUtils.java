package com.tal.generate.pdf.util;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * @author zhaiyarong
 * Process执行结果exit值获取
 */
public final class WaitForProcessUtils {

    private static final int CORE_SIZE = Runtime.getRuntime().availableProcessors();

    private static final int MAX_POOL_SIZE = CORE_SIZE * 2;

    private static ExecutorService threadPoolExecutor = new ThreadPoolExecutor(CORE_SIZE, MAX_POOL_SIZE,
            10L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100), new NamedThreadFactory("wait-pool"));

    private static class WaitForProcessThread implements Callable {
        final Process process;
        boolean finished = false;
        int exitValue = -1;

        private WaitForProcessThread(Process process) {
            this.process = process;
        }

        @Override
        public Object call() {
            try {
                exitValue = process.waitFor();
                finished = true;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return exitValue;
        }

        public boolean hasFinished() {
            return finished;
        }

        public int exitValue() {
            return exitValue;
        }
    }

    /**
     * 获取Process执行的exit结果，超时异常退出
     *
     * @param p
     * @param timeout
     * @param unit
     * @return
     * @throws TimeoutException
     * @throws IOException
     */
    public static int waitForWithTimeout(final Process p, long timeout, TimeUnit unit) throws TimeoutException, IOException {
        WaitForProcessThread thread = new WaitForProcessThread(p);
        Future future = null;
        try {
            future = threadPoolExecutor.submit(thread);
            future.get(timeout, unit);
        } catch (InterruptedException e) {
            if (future != null) {
                future.cancel(true);
            }
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw new IOException("wait for process result error");
        }

        if (!thread.hasFinished()) {
            throw new TimeoutException("Process did not finish within timeout");
        }

        return thread.exitValue();
    }
}
