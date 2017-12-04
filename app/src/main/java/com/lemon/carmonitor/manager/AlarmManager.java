package com.lemon.carmonitor.manager;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.lemon.LemonDaoManager;
import com.lemon.annotation.Autowired;
import com.lemon.annotation.Component;
import com.lemon.annotation.InitMethod;
import com.lemon.carmonitor.db.Alarm;

import java.sql.SQLException;


/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.manager]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2016/2/17 15:17]
 * 修改人:    [xflu]
 * 修改时间:  [2016/2/17 15:17]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
@Component
public class AlarmManager {

    @Autowired
    public LemonDaoManager lemonDaoManager;

    public Dao alarmDao;

    @InitMethod
    public void init() throws SQLException {
        alarmDao = lemonDaoManager.getDao(Alarm.class);
    }

    public void readAll() {
        try {
            alarmDao.updateBuilder().updateColumnValue("statue", "1").update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void readAll(String devSn) {
        try {
            UpdateBuilder updateBuilder = alarmDao.updateBuilder();
            updateBuilder.updateColumnValue("statue", "1");
            updateBuilder.where().eq("devSn", devSn);
            updateBuilder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cleanAll() {
        try {
            DeleteBuilder deleteBuilder = alarmDao.deleteBuilder();
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cleanAll(String devSn) {
        try {
            DeleteBuilder deleteBuilder = alarmDao.deleteBuilder();
            deleteBuilder.where().eq("devSn", devSn);
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void read(String devSn, int id) {
        try {
            UpdateBuilder updateBuilder = alarmDao.updateBuilder();
            updateBuilder.updateColumnValue("statue", "1");
            updateBuilder.where().eq("devSn", devSn).and().eq("id", id);
            updateBuilder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
