package com.ruoyi.framework.web.service;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.service.ISysConfigService;

/**
 * RuoYi首创 html调用 thymeleaf 实现参数管理
 * 
 * @author ruoyi
 */
@Service("config")
public class ConfigService
{
    @Autowired
    private ISysConfigService configService;

    /**
     * 根据键名查询参数配置信息
     * 
     * @param configKey 参数名称
     * @return 参数键值
     */
    public String getKey(String configKey)
    {
        SysConfig retConfig = configService.findBy("configKey", configKey);
        return StringUtils.isNotNull(retConfig) ? retConfig.getConfigValue() : "";
    }
}
