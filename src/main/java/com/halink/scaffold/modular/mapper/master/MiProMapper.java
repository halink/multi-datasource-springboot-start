package com.halink.scaffold.modular.mapper.master;

import com.halink.scaffold.common.entity.MiPro;

public interface MiProMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MiPro record);

    int insertSelective(MiPro record);

    MiPro selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MiPro record);

    int updateByPrimaryKey(MiPro record);
}