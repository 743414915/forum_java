package com.forum.service.impl;

import com.forum.constants.Constants;
import com.forum.entity.config.WebConfig;
import com.forum.entity.po.EmailCode;
import com.forum.entity.po.UserInfo;
import com.forum.entity.query.EmailCodeQuery;
import com.forum.entity.query.SimplePage;
import com.forum.entity.query.UserInfoQuery;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.PageSize;
import com.forum.exception.BusinessException;
import com.forum.mappers.EmailCodeMapper;
import com.forum.mappers.UserInfoMapper;
import com.forum.service.EmailCodeService;
import com.forum.service.UserInfoService;
import com.forum.utils.StringTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;

;

/**
 * @Description: 邮箱验证码EmailCodeServiceImpl
 * @auther: chong
 * @date: 2023/03/27
 */
@Service("emailCodeService")
public class EmailCodeServiceImpl implements EmailCodeService {
    private static final Logger logger = LoggerFactory.getLogger(EmailCodeServiceImpl.class);

    @Resource
    private EmailCodeMapper<EmailCode, EmailCodeQuery> emailCodeMapper;

    @Resource
    private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;

    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private WebConfig webConfig;

    /**
     * 根据条件查询列表
     */
    public List<EmailCode> findListByParam(EmailCodeQuery query) {
        return this.emailCodeMapper.selectList(query);
    }

    /**
     * 根据条件查询数量
     */
    public Integer findCountByParam(EmailCodeQuery query) {
        return this.emailCodeMapper.selectCount(query);
    }

    /**
     * 分页查询
     */
    public PaginationResultVO<EmailCode> findListByPage(EmailCodeQuery query) {
        Integer count = this.findCountByParam(query);
        int pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();

        SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
        query.setSimplePage(page);
        List<EmailCode> list = this.findListByParam(query);
        PaginationResultVO<EmailCode> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    public Integer add(EmailCode bean) {
        return this.emailCodeMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    public Integer addBatch(List<EmailCode> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.emailCodeMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或修改
     */
    public Integer addOrUpdateBatch(List<EmailCode> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.emailCodeMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 根据EmailAndCode查询
     */
    public EmailCode getEmailCodeByEmailAndCode(String email, String code) {
        return this.emailCodeMapper.selectByEmailAndCode(email, code);
    }

    /**
     * 根据EmailAndCode更新
     */
    public Integer updateEmailCodeByEmailAndCode(EmailCode bean, String email, String code) {
        return this.emailCodeMapper.updateByEmailAndCode(bean, email, code);
    }

    /**
     * 根据EmailAndCode删除
     */
    public Integer deleteEmailCodeByEmailAndCode(String email, String code) {
        return this.emailCodeMapper.deleteByEmailAndCode(email, code);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void SendEmailCode(String email, Integer type) throws BusinessException {
        if (Constants.ZERO.equals(type)) {
            UserInfo userInfo = userInfoMapper.selectByEmail(email);
            if (userInfo != null) {
                throw new BusinessException("邮箱已存在");
            }
        }
        String code = StringTools.getRandomString(Constants.LENGTH_5);
        SendEmailCodeDo(email, code);
        emailCodeMapper.disableEmailCode(email);

        EmailCode emailCode = new EmailCode();
        emailCode.setCode(code);
        emailCode.setEmail(email);
        emailCode.setStatus(Constants.ZERO);
        emailCode.setCreateTime(new Date());
        emailCodeMapper.insert(emailCode);
    }

    private void SendEmailCodeDo(String toEmail, String code) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            // 邮件发送人
            helper.setFrom(webConfig.getSendUserName());
            // 邮件收件人
            helper.setTo(toEmail);

            helper.setSubject("注册邮箱验证码");
            helper.setText("邮箱验证码为" + code);
            helper.setSentDate(new Date());
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            logger.error("发送邮件失败");
            try {
                throw new BusinessException("邮件发送失败");
            } catch (BusinessException businessException) {
                businessException.printStackTrace();
            }
        }
    }

    @Override
    public void checkCode(String email, String emailCode) throws BusinessException {
        EmailCode dbInfo = this.emailCodeMapper.selectByEmailAndCode(email, emailCode);
//        if (null != dbInfo) {
//            if (dbInfo.getStatus() == 0 && System.currentTimeMillis() - dbInfo.getCreateTime().getTime() <= 1000 * 60 + Constants.LENGTH_15) {
//            } else {
//                throw new BusinessException("验证码已失效");
//            }
//        } else {
//            throw new BusinessException("邮箱验证码不正确");
//        }

        // 优化
        if (null == dbInfo) {
            throw new BusinessException("邮箱验证码不正确");
        }
        if (dbInfo.getStatus() != 0 && System.currentTimeMillis() - dbInfo.getCreateTime().getTime() > 1000 * 60 + Constants.LENGTH_15) {
            throw new BusinessException("验证码已失效");
        }

        emailCodeMapper.disableEmailCode(email);
    }
}