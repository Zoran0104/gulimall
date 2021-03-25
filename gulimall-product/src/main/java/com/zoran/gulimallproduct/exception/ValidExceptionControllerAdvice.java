package com.zoran.gulimallproduct.exception;

import com.zoran.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ValidExceptionControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e) {
        log.error("数据校验出现问题{}，异常类型为{}", e.getMessage(), e.getClass());
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach(fieldError -> errorMap.put(fieldError.getField(),
                fieldError.getDefaultMessage()));
        return R.error(ConstantErrorCode.VALID_ERROR.getCode(), ConstantErrorCode.VALID_ERROR.getMessage())
                .put("msg", errorMap);
    }

    @ExceptionHandler(Throwable.class)
    public R handleCommonException(Throwable throwable) {
        log.error("未知异常{}，异常类型为{}", throwable.getMessage(), throwable.getClass());
        return R.error(ConstantErrorCode.NOT_KNOWN_ERROR.getCode(), throwable.getMessage());
    }
}
