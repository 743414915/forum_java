package com.forum.controller.base;

import com.forum.constants.Constants;
import com.forum.entity.dto.SessionWebUserDto;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.entity.vo.ResponseVO;
import com.forum.enums.ResponseCodeEnum;
import com.forum.exception.BusinessException;
import com.forum.utils.CopyTools;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ABaseController {
    protected static final String STATUS_SUCCESS = "success";
    protected static final String STATUS_ERROR = "error";

    protected <T> ResponseVO getSuccessResponseVO(T t) {
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setStatus(STATUS_SUCCESS);
        responseVO.setCode(ResponseCodeEnum.CODE_200.getCode());
        responseVO.setInfo(ResponseCodeEnum.CODE_200.getMsg());
        responseVO.setData(t);
        return responseVO;
    }

    protected <T> ResponseVO getBusinessErrorResponseVO(BusinessException e, T t) {
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setStatus(STATUS_ERROR);
        if (e.getCode() == null) {
            responseVO.setCode(ResponseCodeEnum.CODE_600.getCode());
        } else {
            responseVO.setCode(e.getCode());
        }
        responseVO.setInfo(e.getMessage());
        responseVO.setData(t);
        return responseVO;
    }

    protected <T> ResponseVO getServerErrorResponseVO(BusinessException e, T t) {
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setStatus(STATUS_ERROR);
        responseVO.setCode(ResponseCodeEnum.CODE_500.getCode());
        responseVO.setInfo(ResponseCodeEnum.CODE_500.getMsg());
        responseVO.setData(t);
        return responseVO;
    }

    protected String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X_REAL_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    protected SessionWebUserDto getUserInfoFromSession(HttpSession session) {
        SessionWebUserDto sessionWebUserDto = (SessionWebUserDto) session.getAttribute(Constants.SESSION_KEY);
        return sessionWebUserDto;
    }

    protected <S, T> PaginationResultVO<T> convert2PaginationVO(PaginationResultVO<S> result, Class<T> classz) {
        PaginationResultVO<T> resultVO = new PaginationResultVO<>();
        resultVO.setList(CopyTools.copyList(result.getList(), classz));
        resultVO.setPageNo(result.getPageNo());
        resultVO.setPageSize(result.getPageSize());
        resultVO.setPageTotal(result.getPageTotal());
        resultVO.setTotalCount(result.getTotalCount());
        return resultVO;
    }
}
