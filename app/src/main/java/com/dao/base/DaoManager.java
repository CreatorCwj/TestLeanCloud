package com.dao.base;

import com.application.MyApplication;
import com.dao.generate.DaoMaster;
import com.dao.generate.DaoSession;

/**
 * Created by cwj on 16/2/16.
 * Dao管理器,获取单例session
 */
public class DaoManager {

    private static DaoManager instance;
    private DaoSession daoSession;

    private DaoManager() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(MyApplication.getAppContext(), "note_db", null);
        daoSession = new DaoMaster(helper.getWritableDatabase()).newSession();
    }

    public static DaoManager getInstance() {
        if (instance == null) {
            synchronized (DaoManager.class) {
                if (instance == null) {
                    instance = new DaoManager();
                }
            }
        }
        return instance;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
