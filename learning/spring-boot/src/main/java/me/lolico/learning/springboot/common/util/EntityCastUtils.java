package me.lolico.learning.springboot.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lolico
 */
public class EntityCastUtils {
    private static Logger logger = LoggerFactory.getLogger(EntityCastUtils.class);

    /**
     * 将数组数据转换为实体类
     * 此处数组元素的顺序必须与实体类构造函数中的属性顺序一致
     *
     * @param list  数组对象集合
     * @param clazz 实体类
     * @param <T>   实体类
     * @param model 实例化的实体类
     * @return 实体类集合
     */
    public static <T> List<T> castEntity(List<Object[]> list, Class<T> clazz, Object model) {
        List<T> returnList = new ArrayList<>();
        if (list.isEmpty()) {
            return returnList;
        }
        //获取每个数组集合的元素个数
        Object[] co = list.get(0);
        //获取当前实体类的属性名、属性值、属性类别
        List<Map<String, Object>> attributeInfoList = getFieldsInfo(model);
        //创建属性类别数组
        Class<?>[] c2 = new Class[attributeInfoList.size()];
        //如果数组集合元素个数与实体类属性个数不一致则发生错误
        if (attributeInfoList.size() != co.length) {
            return returnList;
        }
        //确定构造方法
        for (int i = 0; i < attributeInfoList.size(); i++) {
            c2[i] = (Class<?>) attributeInfoList.get(i).get("type");
        }
        try {
            for (Object[] o : list) {
                Constructor<T> constructor = clazz.getConstructor(c2);
                returnList.add(constructor.newInstance(o));
            }
        } catch (Exception ex) {
            logger.error("实体数据转化为实体类发生异常：异常信息：{}", ex.getMessage());
            return returnList;
        }
        return returnList;
    }

    public static <T> List<T> castEntity(List<Object[]> list, Class<T> entityClass) throws InstantiationException, IllegalAccessException {
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        List<T> castList = new ArrayList<>(list.size());
        for (Object[] objects : list) {
            castList.add(transfer(objects, entityClass));
        }
        return castList;
    }

    @SuppressWarnings("unchecked")
    private static <T> T transfer(Object[] objects, Class<T> entityClass) throws IllegalAccessException, InstantiationException {
        if (objects.length == 1 && objects[0].getClass() == entityClass) {
            return (T) objects[0];
        }
        Field[] fields = entityClass.getDeclaredFields();
        Method[] methods = entityClass.getMethods();
        // if (fields.length != objects.length) {
        //     throw new EntityCastException("Mapping Error:The number of array elements is not consistent with the number of entity class attributes.");
        // }
        T t = entityClass.newInstance();
        int index = 0;
        for (Field field : fields) {
            String fieldName = field.getName();
            try {
                Method method = entityClass.getDeclaredMethod("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), field.getType());
                if (field.getType() == objects[index].getClass()) {
                    method.invoke(t, objects[index++]);
                }
            } catch (Exception ex) {
                logger.warn(ex.getMessage());
            }
        }
        return t;
    }

    /**
     * 根据属性名获取属性值
     *
     * @param fieldName 属性名
     * @param modle     实体类
     * @return 属性值
     */
    private static Object getFieldValueByName(String fieldName, Object modle) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = modle.getClass().getMethod(getter);
            return method.invoke(modle);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     *
     * @param model 实体类
     * @return list集合
     */
    private static List<Map<String, Object>> getFieldsInfo(Object model) {
        Field[] fields = model.getClass().getDeclaredFields();
        List<Map<String, Object>> list = new ArrayList<>(fields.length);
        Map<String, Object> infoMap;
        for (Field field : fields) {
            infoMap = new HashMap<>(3);
            infoMap.put("type", field.getType());
            infoMap.put("name", field.getName());
            infoMap.put("value", getFieldValueByName(field.getName(), model));
            list.add(infoMap);
        }
        return list;
    }

}
