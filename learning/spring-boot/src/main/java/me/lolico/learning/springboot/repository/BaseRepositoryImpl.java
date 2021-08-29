package me.lolico.learning.springboot.repository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author lolico
 */
@Transactional(readOnly = true)
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {
    private Class<T> entityClass;
    private EntityManager entityManager;
    private JpaEntityInformation<T, ?> entityInformation;
    private static int BATCH_SIZE = 100;

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityClass = entityInformation.getJavaType();
        this.entityManager = entityManager;
        this.entityInformation = entityInformation;
    }

    /**
     * @param list 实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchUpdate(List<? extends T> list) {
        Iterator<? extends T> iterator = list.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            T entity = iterator.next();
            // 持久化到单元
            entityManager.merge(entity);
            i++;
            if (i % BATCH_SIZE == 0) {
                // 提交到数据库并清除上下文
                entityManager.flush();
                entityManager.clear();
                i = 0;
            }
        }
    }

    /**
     * @param list 实体集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchInsert(List<? extends T> list) {
        Iterator<? extends T> iterator = list.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            T entity = iterator.next();
            // 持久化到单元
            entityManager.persist(entity);
            i++;
            if (i % BATCH_SIZE == 0) {
                // 提交到数据库并清除上下文
                entityManager.flush();
                entityManager.clear();
                i = 0;
            }
        }
    }

    /**
     * @param entity 实体
     * @return 更新后的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public T dynamicUpdate(T entity) {
        BeanWrapper wrapper = new BeanWrapperImpl(entity);
        //获取id的值
        Object id = entityInformation.getRequiredId(entity);
        //先根据id从数据库中查出记录
        T old = entityManager.find(entityClass, id);
        //存放为null的属性名
        Set<String> nullProperty = new HashSet<>();
        for (PropertyDescriptor propertyDescriptor : wrapper.getPropertyDescriptors()) {
            //属性名
            String propertyName = propertyDescriptor.getName();
            //属性值
            Object propertyValue = wrapper.getPropertyValue(propertyName);
            //如果属性值为null就添加到set中
            if (propertyValue == null) {
                nullProperty.add(propertyName);
            }
        }
        //将t中属性的值复制到old中，忽略nullProperty.toArray(new String[0])中的属性
        BeanUtils.copyProperties(entity, old, nullProperty.toArray(new String[0]));
        //更新并返回更新后的结果
        return entityManager.merge(old);
    }
}
