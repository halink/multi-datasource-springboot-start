package com.halink.scaffold.modular.service;

import com.halink.scaffold.common.entity.MiPro;
import com.halink.scaffold.modular.mapper.master.MiProMapper;
import com.halink.scaffold.modular.mapper.slave.HuaProMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IndexService {

    private final MiProMapper miProMapper;
    private final HuaProMapper huaProMapper;

    @Transactional(rollbackFor = Exception.class)
    public void index() {
        String sql = "insert into hua_pro (code, `name`) values ('mate 40','华为 mate40')";
        int i = 1 / 0;
//                HuaPro.builder()
//                        .code("mate 40")
//                        .name("华为 mate40")
//                        .build()
        huaProMapper.sdInsert(sql);
        miProMapper.insert(
                MiPro.builder()
                        .code("MI-11")
                        .name("小米11")
                        .build()
        );
    }

}
