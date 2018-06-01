package com.watson.system.dao;

import java.util.List;

import com.watson.system.model.LoginRecord;
import com.watson.system.model.LoginRecordExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRecordMapper {
    int countByExample(LoginRecordExample example);

    int deleteByExample(LoginRecordExample example);

    int deleteByPrimaryKey(String id);

    int insert(LoginRecord record);

    int insertSelective(LoginRecord record);

    List<LoginRecord> selectByExample(LoginRecordExample example);

    LoginRecord selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") LoginRecord record, @Param("example") LoginRecordExample example);

    int updateByExample(@Param("record") LoginRecord record, @Param("example") LoginRecordExample example);

    int updateByPrimaryKeySelective(LoginRecord record);

    int updateByPrimaryKey(LoginRecord record);

    List<LoginRecord> selectLoginRecords(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("searchAccount") String searchAccount);
}