package com.example.demo;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @ClassName: TestController
 * @Description:
 * @author: like
 * @date 2022/6/6 17:18
 */
@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ElasticsearchRepository<Person, String> elasticsearchRepository;

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @GetMapping("add")
    public void test(String id,String name) {
        Test test = new Test();
        test.setTest("123");
        test.setTest2("24");
        Person person = Person.builder().age(14).name(name).phone(1212423545L).id(id).test(test).build();
        try {
            elasticsearchRestTemplate.save(person);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("save")
    public void save(String id,String name){
        Person person = Person.builder().name(name).id(id).build();
        elasticsearchRestTemplate.save(person);
    }

    @RequestMapping("query")
    public Person query(String id){
        Criteria criteria = new Criteria().and("id").is(id);
        Query query = new CriteriaQuery(criteria);
        return Objects.requireNonNull(elasticsearchRestTemplate.searchOne(query, Person.class)).getContent();
    }

    @RequestMapping("query2")
    public SearchHits<Person> query2(String id){
        Criteria criteria = new Criteria().and("id").is(id);
        Pageable pageable = PageRequest.of(0,5);
        Query query = new CriteriaQuery(criteria,pageable);
        return elasticsearchRestTemplate.search(query,Person.class,IndexCoordinates.of("person"));
    }

    @RequestMapping("query3")
    public SearchHits<Person> query3(){
        Pageable pageable = PageRequest.of(0,5);
        Query query = new NativeSearchQueryBuilder().withQuery(QueryBuilders.nestedQuery("test",QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("test.test","12")), ScoreMode.Total)).withPageable(pageable).build();
        return elasticsearchRestTemplate.search(query,Person.class,IndexCoordinates.of("person"));
    }
}
