package com.hht.study.batch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author hht
 * @date 2022/3/1
 */
@Data
public class TestReq {

    @JsonProperty("名字")
    private String name;

    @JsonProperty("年龄")
    private Integer age;

    @JsonProperty("性别")
    private String sex;
}
