package com.mud.game.config.errorConfig;

import org.apache.http.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理
 */
@ControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * 处理未知异常
     *
     * @param req
     * @param ex
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public String handleHttpException(HttpServletRequest req, Exception ex) {
        String uri = req.getRequestURI();
        String method = req.getMethod();
        System.out.println(ex.getMessage());
        //new Msg(9999, "服务器错误")
        return "服务器错误";
    }

    /**
     * 处理已知异常
     *
     * @param req
     * @param ex
     */
    @ExceptionHandler(value = HttpException.class)
    public void handleHttpException(HttpServletRequest req, HttpException ex) {

        System.out.println("发生了 HttpException");
    }
}
