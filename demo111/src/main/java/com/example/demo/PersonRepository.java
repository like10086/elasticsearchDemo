package com.example.demo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: PersonRepository
 * @Description:
 * @author: like
 * @date 2022/6/7 8:28
 */
@Repository
public interface PersonRepository extends ElasticsearchRepository<Person, String> {
}
