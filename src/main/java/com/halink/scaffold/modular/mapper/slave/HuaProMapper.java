package com.halink.scaffold.modular.mapper.slave;

import com.halink.scaffold.common.annotation.DBConnection;
import com.halink.scaffold.common.entity.HuaPro;
import com.halink.scaffold.common.enumerate.DataSourceTypeEnum;
import org.apache.ibatis.annotations.Param;

@DBConnection(DataSourceTypeEnum.SLAVE)
public interface HuaProMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HuaPro record);

    int insertSelective(HuaPro record);

    HuaPro selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HuaPro record);

    int updateByPrimaryKey(HuaPro record);

    void sdInsert(@Param("value") String value);
}