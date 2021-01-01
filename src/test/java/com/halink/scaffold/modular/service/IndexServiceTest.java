package com.halink.scaffold.modular.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IndexServiceTest {
    @Autowired
    private IndexService indexService;

    @Test
    void index() {
        indexService.index();
    }
}