package com.microfinanceBank.Issues.Service.Impl;


import com.microfinanceBank.Issues.entity.DbSequence;
import lombok.RequiredArgsConstructor;

import org.springframework.data.mongodb.core.ReactiveMongoOperations;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Component
@RequiredArgsConstructor
public class SequenceGeneratorService {
    private final ReactiveMongoOperations mongoOperations;

    public Long getSequenceNumber(String sequenceName){
        Query query=new Query(Criteria.where("id").is(sequenceName));

        Update update=new Update().inc("seq",1);

        Mono<DbSequence> counter= mongoOperations
                .findAndModify(query,update,options().returnNew(true).upsert(true),DbSequence.class);

        return !Objects.isNull(counter) ?counter.block().getSeq() :1;
    }
}
