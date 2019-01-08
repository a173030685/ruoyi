package com.ruoyi.common.core;


import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 */
public abstract class AbstractService<T> implements Service<T> {

    @Autowired
    protected Mapper<T> mapper;

    private Class<T> modelClass;    // 当前泛型真实类型的Class

    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    public Integer save(T model) {
        return mapper.insertSelective(model);
    }

    public Integer save(List<T> models) {
        return mapper.insertList(models);
    }

    public Integer deleteById(Long id) {
        return mapper.deleteByPrimaryKey(id);
    }

    public Integer deleteByIds(String ids) {
        return mapper.deleteByIds(ids);
    }

    public Integer update(T model) {
        return mapper.updateByPrimaryKeySelective(model);
    }

    public T findById(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public T findBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return mapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<T> findByIds(String ids) {
        return mapper.selectByIds(ids);
    }

    public List<T> findByCondition(Condition condition) {
        return mapper.selectByCondition(condition);
    }

    public List<T> findAll() {
        return mapper.selectAll();
    }

    public List<T> findByModel(T model) {
        Class<?> clz = model.getClass();
        Field[] fields = clz.getDeclaredFields();
        if(fields!=null && fields.length>0){
            for (Field field:fields){
                field.setAccessible(true);
                String name = field.getName();
                if(!name.equals("serialVersionUID")){
                    Object o = null;
                    try {
                        o = field.get(model);
                        if(o!=null && o.equals("")){
                            field.set(model, null);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return mapper.select(model);
    }
}
