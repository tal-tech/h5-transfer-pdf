package com.tal.generate.pdf.util;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author zhaiyarong
 */
public class FileUtil {

    public static final String CLASSPATH_FILE_FLAG = "classpath:";

    private FileUtil() {

    }

    /**
     * 获取文件的绝对路径地址
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String getAbsoluteFilePath(String fileName) throws IOException {
        Preconditions.checkArgument(StringUtils.isNotBlank(fileName), "file name can't be null or empty");
        if (absolutePathStart(fileName)) {
            Preconditions.checkArgument(isFileExist(fileName), fileName + " dose not exist");
            return fileName;
        } else if (fileName.startsWith(CLASSPATH_FILE_FLAG)) {
            fileName = fileName.substring(CLASSPATH_FILE_FLAG.length()).trim();
            Preconditions.checkArgument(StringUtils.isNotBlank(fileName), "file name can't be null or empty");
            URL base = getClassLoader().getResource("");
            return new File(base.getFile(), fileName).getCanonicalPath();
        } else {
            String userDir = System.getProperty("user.dir");
            return new File(addSeparator(userDir) + fileName).getCanonicalPath();
        }
    }

    public static String addSeparator(String dir) {
        if (!dir.endsWith(File.separator)) {
            dir += File.separator;
        }
        return dir;
    }

    private static boolean absolutePathStart(String path) {
        File[] files = File.listRoots();
        for (File file : files) {
            if (path.startsWith(file.getPath())) {
                return true;
            }
        }
        return false;
    }

    private static ClassLoader getClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = FileUtil.class.getClassLoader();
        }
        return classLoader;
    }

    /**
     * 查看文件是否存在
     *
     * @param fileName
     * @return
     */
    public static boolean isFileExist(String fileName) {
        return new File(fileName).isFile();
    }

    /**
     * 创建目录
     *
     * @param file
     * @return
     */
    public static boolean makeDirectory(File file) {
        File parent = file.getParentFile();
        if (parent.exists()) {
            return true;
        }
        if (parent != null) {
            return parent.mkdirs();
        }

        return false;
    }

    public static boolean makeDirectory(String fileName) {
        File file = new File(fileName);
        return makeDirectory(file);
    }

}
