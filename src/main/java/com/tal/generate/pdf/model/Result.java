package com.tal.generate.pdf.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @param <T>
 * @author zhaiyarong
 */
@Data
public class Result<T> {

    /**
     * 返回内容
     */
    private T content;

    /**
     * 状态值  true->succ  false->fail
     */
    private boolean status = true;

    /**
     * 返回消息
     */
    private String message = "操作成功";

    /**
     * @throws
     * @description:获取success result
     * @param: [content]
     * @return: Result<T>
     * @author:
     * @date:
     */
    public static <T> Result<T> buildSuccessResult(T content) {
        Result<T> result = new Result<>();

        result.success(content);

        return result;

    }

    /**
     * @throws
     * @description:获取success result
     * @param: [content]
     * @return: Result<T>
     * @author:
     * @date:
     */
    public static <T> Result<T> buildSuccessResult(T content, String message) {
        Result<T> result = buildSuccessResult(content);

        result.setMessage(message);

        return result;

    }

    /**
     * @throws
     * @description: 获取fail result
     * @param: [message]
     * @return: Result<T>
     * @author:
     * @date:
     */
    public static <T> Result<T> buildFailResult(String message) {
        Result<T> result = new Result<>();

        result.fail(StringUtils.isBlank(message) ? "操作失败." : message);

        return result;
    }

    /**
     * @throws
     * @description: 返回失败result
     * @param: [content, message]
     * @author:
     * @date:
     */
    public static <T> Result<T> buildFailResult(T content, String message) {
        Result<T> result = buildFailResult(message);

        result.setContent(content);

        return result;
    }


    public void success(T content) {
        this.status = true;
        this.content = content;
    }


    public void success(T content, String message) {
        this.status = true;
        this.content = content;
        this.message = message;
    }


    public void fail(String message) {
        this.setStatus(false);
        this.setMessage(message);
    }

}
