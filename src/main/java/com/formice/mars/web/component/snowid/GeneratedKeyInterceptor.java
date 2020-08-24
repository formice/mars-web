package com.formice.mars.web.component.snowid;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;


/**
 * 生成主键拦截器
 * @author YJX
 */

@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class,Object.class})})
public class GeneratedKeyInterceptor implements Interceptor {

    /**
     * 主键名
     */
    private static final String KEY_NAME = "snowflakeId";
    /**
     * 主键类型
     */
    private static final String KEY_TYPE = "Long";
    /**
     * 单个插入名称
     */
    private static final String INSERT = "insert";
    /**
     * 批量插入名称
     */
    private static final String BATCH_INSERT = "batchInsert";

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];

        // 获取 SQL
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();

        // 不是 insert 类型的跳过
        if (!SqlCommandType.INSERT.equals(sqlCommandType)) {
            return invocation.proceed();
        }

        // 获取参数
        Object parameter = invocation.getArgs()[1];

        if (mappedStatement.getId().contains(INSERT)){
            generatedKey(parameter);
        }else if (mappedStatement.getId().contains(BATCH_INSERT)){
            // 获取批量查询的参数并生成主键
            if (parameter instanceof HashMap){
                Object list = ((Map)parameter).get("list");
                if (list instanceof ArrayList) {
                    for (Object o : (ArrayList) list) {
                        generatedKey(o);
                    }
                }
            }
        }

        return invocation.proceed();
    }

    /**
     * 获取私有成员变量 ,并设置主键
     * @param parameter 参数
     */
    private void generatedKey(Object parameter) throws Throwable {

        List<Field> fieldList = new ArrayList<>() ;
        Class tempClass = parameter.getClass();

        //获取该实体类的字段,包括该实体类的父类,当父类为null的时候说明到达了最上层的父类(Object类).
        while (tempClass !=null && !"java.lang.object".equals(tempClass.getName().toLowerCase())) {
            fieldList.addAll(Arrays.asList(tempClass .getDeclaredFields()));
             //得到父类,然后赋给自己
            tempClass = tempClass.getSuperclass();
        }

        for (Field field : fieldList) {
            if (!KEY_NAME.equals(field.getName()) || !KEY_TYPE.equals(field.getType().getSimpleName())) {
                continue;
            }
            field.setAccessible(true);
            if (field.get(parameter) == null) {
                // 这里设置雪花id
                field.set(parameter, snowflakeIdWorker.nextId());
            }
        }
    }

    /**
     * Plugin.wrap生成拦截代理对象
     */
    @Override
    public Object plugin(Object o) {
        if (o instanceof Executor) {
            return Plugin.wrap(o, this);
        } else {
            return o;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }

}

