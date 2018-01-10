package com.lemon;

import com.lemon.annotation.Autowired;
import com.lemon.annotation.Component;
import com.lemon.carmonitor.db.NoteDao;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * 项目名称:  [CarMagic]
 * 包:        [com.lemon]
 * 类描述:    [类描述]
 * 创建人:    [xflu]
 * 创建时间:  [2018/1/10 11:25]
 * 修改人:    [xflu]
 * 修改时间:  [2018/1/10 11:25]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */

@Component
public class LemonGreenDaoManager {
    @Autowired
    public LemonGreenDaoDatabaseHelper lemonGreenDaoDatabaseHelper;


    public <T> void create(Class<T> cls, T obj) {
        lemonGreenDaoDatabaseHelper.getDao(cls).insert(obj);
    }

    public <T> List<T> queryAll(Class<T> cls) {
            return lemonGreenDaoDatabaseHelper.getDao(cls).loadAll();
    }

    public <T> List<T> queryAllOrderBy(Class<T> cls, Property orderProperty, boolean ascending){
        QueryBuilder queryBuilder = lemonGreenDaoDatabaseHelper.getDao(cls).queryBuilder();
        if(ascending){
            queryBuilder.orderAsc(orderProperty);
        }else {
            queryBuilder.orderDesc(orderProperty);
        }

        return queryBuilder.list();
    }

    public <T> T queryOne(Class<T> cls, String sql) {
        QueryBuilder queryBuilder = lemonGreenDaoDatabaseHelper.getDao(cls).queryBuilder();
        queryBuilder.where(new WhereCondition.StringCondition(sql));
        return (T)queryBuilder.unique();
    }

    public <T> T queryOne(Class<T> cls, WhereCondition cond, WhereCondition... condMore) {
        QueryBuilder queryBuilder = lemonGreenDaoDatabaseHelper.getDao(cls).queryBuilder();
        queryBuilder.where(cond,condMore);
        return (T)queryBuilder.unique();
    }


    public <T> List<T> query(Class<T> cls, WhereCondition cond, WhereCondition... condMore) {
        QueryBuilder queryBuilder = lemonGreenDaoDatabaseHelper.getDao(cls).queryBuilder();
        queryBuilder.where(cond,condMore);
        return queryBuilder.list();
    }

    public <T> List<T> query(Class<T> cls, String sql) {
        QueryBuilder queryBuilder = lemonGreenDaoDatabaseHelper.getDao(cls).queryBuilder();
        queryBuilder.where(new WhereCondition.StringCondition(sql));
        return queryBuilder.list();
    }

    public <T> List<T> query(Class<T> cls, WhereCondition cond, Property orderProperty, boolean ascending, WhereCondition... condMore) {
        QueryBuilder queryBuilder = lemonGreenDaoDatabaseHelper.getDao(cls).queryBuilder();
        queryBuilder.where(cond,condMore);
        if(ascending){
            queryBuilder.orderAsc(orderProperty);
        }else {
            queryBuilder.orderDesc(orderProperty);
        }
        return queryBuilder.list();
    }


    public <T> void delete(Class<T> cls, T t) {
        lemonGreenDaoDatabaseHelper.getDao(cls).delete(t);
    }

    public <T> void delete(Class<T> cls, WhereCondition cond, WhereCondition... condMore)  {
        QueryBuilder queryBuilder = lemonGreenDaoDatabaseHelper.getDao(cls).queryBuilder();
        queryBuilder.where(cond,condMore);
        DeleteQuery deleteQuery = queryBuilder.buildDelete();
        deleteQuery.executeDeleteWithoutDetachingEntities();
    }

    public <T> void delete(Class<T> cls, String sql)  {
        QueryBuilder queryBuilder = lemonGreenDaoDatabaseHelper.getDao(cls).queryBuilder();
        queryBuilder.where(new WhereCondition.StringCondition(sql));
        DeleteQuery deleteQuery = queryBuilder.buildDelete();
        deleteQuery.executeDeleteWithoutDetachingEntities();
    }

    public <T> void delete(Class<T> cls, String key,String value)  {
        QueryBuilder queryBuilder = lemonGreenDaoDatabaseHelper.getDao(cls).queryBuilder();
        String sql = key + " = " + value;
        queryBuilder.where(new WhereCondition.StringCondition(sql));
        DeleteQuery deleteQuery = queryBuilder.buildDelete();
        deleteQuery.executeDeleteWithoutDetachingEntities();
    }

    public <T> void deleteAll(Class<T> cls){
        lemonGreenDaoDatabaseHelper.getDao(cls).deleteAll();
    }

    public <T> void update(Class<T> cls, T t) {
        lemonGreenDaoDatabaseHelper.getDao(cls).update(t);
    }

    public <T> void save(Class<T> cls, T t) {
        lemonGreenDaoDatabaseHelper.getDao(cls).save(t);
    }

    public <T> AbstractDao getDao(Class<T> cls) {
        return lemonGreenDaoDatabaseHelper.getDao(cls);
    }

    public <T> long count(Class<T> cls) {
        return lemonGreenDaoDatabaseHelper.getDao(cls).count();
    }
}
