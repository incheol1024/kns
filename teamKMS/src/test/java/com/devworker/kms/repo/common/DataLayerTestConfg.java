package com.devworker.kms.repo.common;

import com.devworker.kms.component.board.BoardComponent;
import com.devworker.kms.service.FTSService;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jooq.AutoConfigureJooq;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
@AutoConfigureJooq
//@Transactional // Transactional 먹는지 확인필요
@ImportAutoConfiguration(classes = {ElasticsearchAutoConfiguration.class, ElasticsearchDataAutoConfiguration.class})
public class DataLayerTestConfg {

    @Bean
    public BoardComponent boardComponent() {
        return new BoardComponent();
    }

    @Bean
    public FTSService ftsService() {
        return new FTSService();
    }
}
