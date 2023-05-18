package com.forum.aspect;

import com.forum.annotation.GlobalInterceptor;
import com.forum.annotation.VerifyParam;
import com.forum.enums.ResponseCodeEnum;
import com.forum.exception.BusinessException;
import com.forum.service.ForumArticleService;
import com.forum.service.ForumCommentService;
import com.forum.service.LikeRecordService;
import com.forum.utils.StringTools;
import com.forum.utils.VeriftyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Scanner;

@Component
@Aspect
public class OperactionAspect {
    public static final Logger logger = LoggerFactory.getLogger(OperactionAspect.class);

    private static final String[] TYPE_BASE = {"java.lang.String", "java.lang.Integer", "java.lang.Long", "java.lang.Boolean"};

    @Resource
    private ForumArticleService forumArticleService;

    @Resource
    private ForumCommentService forumCommentService;

    @Resource
    private LikeRecordService likeRecordService;

    @Pointcut("@annotation(com.forum.annotation.GlobalInterceptor)")
    private void requestInterceptor() {

    }

    @Before("requestInterceptor()")
    public void interceptorDo(JoinPoint point) throws BusinessException {
        try {
            Object target = point.getTarget();
            Object[] arguments = point.getArgs();
            String methodName = point.getSignature().getName();

            Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
            Method method = target.getClass().getMethod(methodName, parameterTypes);
            GlobalInterceptor interceptor = method.getAnnotation(GlobalInterceptor.class);
            if (interceptor == null) {
                return;
            }
            // 校验参数
            if (interceptor.checkParams()) {
                validateParams(method, arguments);
            }

        } catch (BusinessException e) {
            logger.error("全局拦截器异常");
            throw e;
        } catch (Exception e) {
            logger.error("全局拦截器异常:");
            logger.error(e.getMessage());
            throw new BusinessException(ResponseCodeEnum.CODE_500);
        } catch (Throwable e) {
            logger.error("全局拦截器异常:");
            logger.error(e.getMessage());
            throw new BusinessException(ResponseCodeEnum.CODE_500);
        }
    }

    private void validateParams(Method method, Object[] arguments) throws BusinessException {
        Parameter[] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Object value = arguments[i];

            VerifyParam verifyParam = parameter.getAnnotation(VerifyParam.class);
            if (null == verifyParam) {
                continue;
            }
            if (ArrayUtils.contains(TYPE_BASE, parameter.getParameterizedType().getTypeName())) {
                checkValue(value, verifyParam);
            } else {

            }
        }
    }

    private void checkObjValue(Parameter parameter, Object value) throws BusinessException {
        try {
            String typeName = parameter.getParameterizedType().getTypeName();
            Class classz = Class.forName(typeName);
            Field[] files = classz.getDeclaredFields();
            for (Field field : files) {
                VerifyParam fieldVerifyParam = field.getAnnotation(VerifyParam.class);
                if (fieldVerifyParam == null) {
                    continue;
                }
                field.setAccessible(true);
                Object resultValue = field.get(value);
                checkValue(resultValue, fieldVerifyParam);
            }
        } catch (Exception e) {
            logger.error("校验参数失败", e);
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
    }

    private void checkValue(Object value, VerifyParam verifyParam) throws BusinessException {
        Boolean isEmpty = value == null || StringTools.isEmpty(value.toString());
        Integer length = value == null ? 0 : value.toString().length();

        // 校验空
        if (isEmpty && verifyParam.required()) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        // 校验长度
        if (!isEmpty && (verifyParam.max() != -1 && verifyParam.max() < length || verifyParam.min() != -1 && verifyParam.min() > length)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        // 校验正则
        if (!isEmpty && !StringTools.isEmpty(verifyParam.regex().getRegex()) && !VeriftyUtils.verfity(verifyParam.regex(), String.valueOf(value))) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
    }
}
