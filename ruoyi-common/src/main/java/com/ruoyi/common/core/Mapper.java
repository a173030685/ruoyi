package com.ruoyi.common.core;

import tk.mybatis.mapper.common.*;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * 定制版MyBatis Mapper插件接口，如需其他接口参考官方文档自行添加。
 */
public interface Mapper<T>
        extends
        BaseMapper<T>,
        ExampleMapper<T>,
        ConditionMapper<T>,
        RowBoundsMapper<T>,
        Marker,
        IdsMapper<T>,
        InsertListMapper<T> {
}
