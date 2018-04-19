package com.lemon;

import android.content.Context;

import com.lemon.carmonitor.db.gd.DaoMaster;
import com.lemon.carmonitor.db.gd.DaoSession;
import com.lemon.config.Config;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

/**
 * 项目名称:  [CarMagic]
 * 包:        [com.lemon]
 * 类描述:    [类描述]
 * 创建人:    [xflu]
 * 创建时间:  [2018/1/10 11:29]
 * 修改人:    [xflu]
 * 修改时间:  [2018/1/10 11:29]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */

public class LemonGreenDaoDatabaseHelper {

    public static final boolean ENCRYPTED = false;
    private DaoSession daoSession;
    public Context mContext;

    public void init(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mContext, Config.getDbName());
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb(Config.getDbName()) : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public AbstractDao getDao(Class<? extends Object> entityClass){
        return daoSession.getDao(entityClass);
    }
}
