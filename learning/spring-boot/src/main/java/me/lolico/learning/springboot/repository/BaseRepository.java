package me.lolico.learning.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author lolico
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    /**
     * 批量更新
     *
     * @param list 实体集合
     */
    void batchUpdate(List<? extends T> list);

    /**
     * 批量插入
     *
     * @param list 实体集合
     */
    void batchInsert(List<? extends T> list);

    /**
     * 忽略实体中为null的属性更新记录
     *
     * @param entity 实体
     * @return 更新后的实体
     * @see BaseRepositoryImpl#dynamicUpdate(Object)
     */
    T dynamicUpdate(T entity);

}
