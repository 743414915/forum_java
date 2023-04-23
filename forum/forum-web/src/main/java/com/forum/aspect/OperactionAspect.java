package com.forum.aspect;

import com.forum.annotation.GlobalInterceptor;
import com.forum.annotation.VerifyParam;
import com.forum.constants.Constants;
import com.forum.entity.dto.SessionWebUserDto;
import com.forum.entity.dto.SysSettingDto;
import com.forum.entity.query.ForumArticleQuery;
import com.forum.entity.query.ForumCommentQuery;
import com.forum.entity.query.LikeRecordQuery;
import com.forum.entity.vo.ResponseVO;
import com.forum.enums.DateTimePatternEnum;
import com.forum.enums.ResponseCodeEnum;
import com.forum.enums.UserOperFrequencyTypeEnum;
import com.forum.exception.BusinessException;
import com.forum.service.ForumArticleService;
import com.forum.service.ForumCommentService;
import com.forum.service.LikeRecordService;
import com.forum.utils.DateUtils;
import com.forum.utils.StringTools;
import com.forum.utils.SysCacheUtils;
import com.forum.utils.VeriftyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.assertj.core.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;

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

    @Around("requestInterceptor()")
    public Object interceptorDo(ProceedingJoinPoint point) throws BusinessException {
        try {
            Object target = point.getTarget();
            Object[] arguments = point.getArgs();
            String methodName = point.getSignature().getName();

            Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
            Method method = target.getClass().getMethod(methodName, parameterTypes);
            GlobalInterceptor interceptor = method.getAnnotation(GlobalInterceptor.class);
            if (interceptor == null) {
                return null;
            }
            // 校验登录
            if (interceptor.checkLogin()) {
                checkLogin();
            }
            // 校验参数
            if (interceptor.checkParams()) {
                validateParams(method, arguments);
            }
            // 校验频次
            this.checkFrequency(interceptor.frequencyType());

            Object pointResult = point.proceed();
            if (pointResult instanceof ResponseVO) {
                ResponseVO responseVO = (ResponseVO) pointResult;
                if (Constants.STATUS_SUCCESS.equals(responseVO.getStatus())) {
                    this.addOpCount(interceptor.frequencyType());
                }
            }
            return pointResult;
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

    private void checkFrequency(UserOperFrequencyTypeEnum typeEnum) throws BusinessException {
        if (typeEnum == null || typeEnum == UserOperFrequencyTypeEnum.NO_CHECK) {
            return;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        SessionWebUserDto webUserDto = (SessionWebUserDto) session.getAttribute(Constants.SESSION_KEY);

        String curDate = DateUtils.formal(new Date(), DateTimePatternEnum.YYYY_MM_DD.getPattern());
        String sessionKey = Constants.SESSION_KEY_FREQUENCY + curDate + typeEnum.getOperType();
        Integer count = (Integer) session.getAttribute(sessionKey);
        SysSettingDto sysSettingDto = SysCacheUtils.getSysSetting();

        switch (typeEnum) {
            case POST_ARTICLE:
                // 判断该用户当天发帖数量是否超过设置
                if (count == null) {
                    ForumArticleQuery forumArticleQuery = new ForumArticleQuery();
                    forumArticleQuery.setUserId(webUserDto.getUserId());
                    forumArticleQuery.setPostTimeStart(curDate);
                    forumArticleQuery.setPostTimeEnd(curDate);
                    count = forumArticleService.findCountByParam(forumArticleQuery);
                }
                if (count >= sysSettingDto.getPostSetting().getPostDayCountThreshold()) {
                    throw new BusinessException(ResponseCodeEnum.CODE_602);
                }
                break;
            case POST_COMMENT:
                if (count == null) {
                    ForumCommentQuery forumCommentQuery = new ForumCommentQuery();
                    forumCommentQuery.setUserId(webUserDto.getUserId());
                    forumCommentQuery.setPostTimeStart(curDate);
                    forumCommentQuery.setPostTimeEnd(curDate);
                    count = forumCommentService.findCountByParam(forumCommentQuery);
                    if (count >= sysSettingDto.getCommentSetting().getCommentDayCountThreshold()) {
                        throw new BusinessException(ResponseCodeEnum.CODE_602);
                    }
                }
                break;
            case DO_LIKE:
                if (count == null) {
                    LikeRecordQuery likeRecordQuery = new LikeRecordQuery();
                    likeRecordQuery.setUserId(webUserDto.getUserId());
                    likeRecordQuery.setCreateTimeStart(curDate);
                    likeRecordQuery.setCreateTimeEnd(curDate);
                    count = likeRecordService.findCountByParam(likeRecordQuery);
                    if (count >= sysSettingDto.getLikeSetting().getLikeDayCountThreshold()) {
                        throw new BusinessException(ResponseCodeEnum.CODE_602);
                    }
                }
                break;
            case IMAGE_UPLOAD:
                if (count == null) {
                    count = 0;
                }
                if (count >= sysSettingDto.getPostSetting().getDayImageUploadCount()) {
                    throw new BusinessException(ResponseCodeEnum.CODE_602);
                }
                break;
        }
        session.setAttribute(sessionKey, count);
    }

    private void addOpCount(UserOperFrequencyTypeEnum typeEnum) {
        if (typeEnum == null || typeEnum == UserOperFrequencyTypeEnum.NO_CHECK) {
            return;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        String curDate = DateUtils.formal(new Date(), DateTimePatternEnum.YYYY_MM_DD.getPattern());
        String sessionKey = Constants.SESSION_KEY_FREQUENCY + curDate + typeEnum.getOperType();
        Integer count = (Integer) session.getAttribute(sessionKey);
        session.setAttribute(sessionKey, count + 1);
    }

    private void checkLogin() throws BusinessException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object obj = session.getAttribute(Constants.SESSION_KEY);

        if (null == obj) {
            throw new BusinessException(ResponseCodeEnum.CODE_901);
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
