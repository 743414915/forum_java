package com.forum.service.impl;

import com.forum.entity.dto.SysSetting4AuditDto;
import com.forum.entity.dto.SysSetting4CommentDto;
import com.forum.entity.dto.SysSettingDto;
import com.forum.entity.po.SysSetting;
import com.forum.entity.query.SimplePage;
import com.forum.entity.query.SysSettingQuery;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.PageSize;
import com.forum.enums.SysSettingCodeEnum;
import com.forum.exception.BusinessException;
import com.forum.mappers.SysSettingMapper;
import com.forum.service.SysSettingService;
import com.forum.utils.JsonUtils;
import com.forum.utils.StringTools;
import com.forum.utils.SysCacheUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;

;

/**
 * @Description: 系统设置信息SysSettingServiceImpl
 * @auther: chong
 * @date: 2023/03/27
 */
@Service("sysSettingService")
public class SysSettingServiceImpl implements SysSettingService {

    private static final Logger logger = LoggerFactory.getLogger(SysSettingService.class);

    @Resource
    private SysSettingMapper<SysSetting, SysSettingQuery> sysSettingMapper;

    /**
     * 根据条件查询列表
     */
    public List<SysSetting> findListByParam(SysSettingQuery query) {
        return this.sysSettingMapper.selectList(query);
    }

    /**
     * 根据条件查询数量
     */
    public Integer findCountByParam(SysSettingQuery query) {
        return this.sysSettingMapper.selectCount(query);
    }

    /**
     * 分页查询
     */
    public PaginationResultVO<SysSetting> findListByPage(SysSettingQuery query) {
        Integer count = this.findCountByParam(query);
        int pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();

        SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
        query.setSimplePage(page);
        List<SysSetting> list = this.findListByParam(query);
        PaginationResultVO<SysSetting> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    public Integer add(SysSetting bean) {
        return this.sysSettingMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    public Integer addBatch(List<SysSetting> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.sysSettingMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或修改
     */
    public Integer addOrUpdateBatch(List<SysSetting> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.sysSettingMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 根据Code查询
     */
    public SysSetting getSysSettingByCode(String code) {
        return this.sysSettingMapper.selectByCode(code);
    }

    /**
     * 根据Code更新
     */
    public Integer updateSysSettingByCode(SysSetting bean, String code) {
        return this.sysSettingMapper.updateByCode(bean, code);
    }

    /**
     * 根据Code删除
     */
    public Integer deleteSysSettingByCode(String code) {
        return this.sysSettingMapper.deleteByCode(code);
    }

    /**
     * 刷新缓存
     */
    @Override
    public SysSettingDto refreshCache() throws BusinessException {
        try {
            SysSettingDto sysSettingDto = new SysSettingDto();
            List<SysSetting> list = this.sysSettingMapper.selectList(new SysSettingQuery());

            for (SysSetting sysSetting : list) {
                String jsonContent = sysSetting.getJsonContent();
                if (StringTools.isEmpty(jsonContent)) {
                    continue;
                }
                String code = sysSetting.getCode();
                SysSettingCodeEnum sysSettingCodeEnum = SysSettingCodeEnum.getByCode(code);

                PropertyDescriptor pd = new PropertyDescriptor(sysSettingCodeEnum.getPropName(), SysSettingDto.class);
                Method method = pd.getWriteMethod();
                Class subClassz = Class.forName(sysSettingCodeEnum.getClassz());
                method.invoke(sysSettingDto, JsonUtils.convertJson2Obj(jsonContent, subClassz));
            }

            logger.info(JsonUtils.convertObj2Json(sysSettingDto));

            SysCacheUtils.refresh(sysSettingDto);
            return sysSettingDto;
        } catch (Exception e) {
            logger.error("刷新缓存失败", e);
            throw new BusinessException("刷新缓存失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSetting(SysSettingDto sysSettingDto) throws BusinessException {
        try {
            Class classz = SysSettingDto.class;
            for (SysSettingCodeEnum codeEnum : SysSettingCodeEnum.values()) {
                PropertyDescriptor pd = new PropertyDescriptor(codeEnum.getPropName(), classz);
                Method method = pd.getWriteMethod();
                Object obj = method.invoke(sysSettingDto);
                SysSetting sysSetting = new SysSetting();
                sysSetting.setCode(codeEnum.getCode());
                sysSetting.setJsonContent(JsonUtils.convertObj2Json(obj));
                this.sysSettingMapper.insertOrUpdate(sysSetting);
            }

        } catch (Exception e) {
            logger.error("保存设置失败", e);
            throw new BusinessException("保存设置失败");
        }
    }
}