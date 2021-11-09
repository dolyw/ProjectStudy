package com.example.exception;

import cn.hutool.core.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RejectedExecutionException;

/**
 * 全局异常控制处理器
 * 未找到指定异常处理方法exception，则以全局异常方法globalException兜底
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/10/12 15:46
 */
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @Autowired
    private HttpServletRequest request;

    /**
     * 捕捉RejectedExecutionException-线程拒绝策略异常
     *
     * @param e
     * @return java.lang.Object
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/11/9 13:49
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(RejectedExecutionException.class)
    public Object exception(RejectedExecutionException e) {
        // logger.error("{}接口线程服务异常:{}", request.getRequestURI(), ExceptionUtil.stacktraceToOneLineString(e.getCause(), 10000000));
        logger.error("{}接口线程服务异常:{}", request.getRequestURI(), e);
        return e.getMessage();
    }

    /**
     * 捕捉ExecutionException-FutureTask异步线程异常
     *
     * @param e
     * @return java.lang.Object
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/11/9 13:46
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ExecutionException.class)
    public Object exception(ExecutionException e) {
        // logger.error("{}接口异步线程服务异常:{}", request.getRequestURI(), ExceptionUtil.stacktraceToOneLineString(e.getCause(), 10000000));
        logger.error("{}接口异步线程服务异常:{}", request.getRequestURI(), e.getCause());
        if (ObjectUtil.isNotEmpty(e.getCause().getMessage())) {
            return e.getCause().getMessage();
        } else {
            return e.getMessage();
        }
    }

    /**
     * 捕捉404未找到路径异常
     *
     * @param e
     * @return java.lang.Object
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/10/12 16:08
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NoHandlerFoundException.class)
    public Object exception(NoHandlerFoundException e) {
        return "404";
    }

    /**
     * 捕捉其他所有Exception异常
     *
     * @param e
     * @return com.pcic.app.common.dto.ResponseMessage
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/10/12 16:09
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public Object globalException(Exception e) {
        // logger.error("{}接口服务异常:{}", request.getRequestURI(), ExceptionUtil.stacktraceToOneLineString(e, 10000000));
        logger.error("{}接口服务异常:{}", request.getRequestURI(), e);
        return e.getMessage();
    }
}
