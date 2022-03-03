package com.hht.study.batch.controller;

import com.hht.study.batch.model.TestReq;
import com.hht.study.common.model.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author hht
 * @date 2022/3/1
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @PostMapping
    public JsonResult<TestReq> test(@RequestBody TestReq req){
        log.info("收到请求:{}",req);
        return JsonResult.OK(req);
    }
}
