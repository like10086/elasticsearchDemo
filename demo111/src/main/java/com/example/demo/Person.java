package com.example.demo;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @ClassName: Person
 * @Description:
 * @author: like
 * @date 2022/6/7 8:23
 */
@Data
@Document(indexName = "person")
@Builder
public class Person implements Serializable {
    @Id
    private String id;
    @Field(type = FieldType.Keyword)
    private String name;
    @Field
    private Long phone;
    @Field
    private Integer age;
    @Field(type = FieldType.Nested)
    private Test test;
}
