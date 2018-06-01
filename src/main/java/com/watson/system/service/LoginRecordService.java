package com.watson.system.service;

import com.watson.core.utils.PageResult;
import com.watson.system.model.LoginRecord;

/**
 * 登录日志Service
 */
public interface LoginRecordService {

    /**
     * 添加登录日志
     *
     * @param loginRecord
     * @return
     */
    boolean addLoginRecord(LoginRecord loginRecord);

    /**
     * 查询登录日志
     *
     * @param pageIndex
     * @param pageSize
     * @param startDate
     * @param endDate
     * @param searchAccount
     * @return
     */
    PageResult<LoginRecord> getLoginRecords(int pageIndex, int pageSize, String startDate, String endDate, String searchAccount);
}
