package com.hht.study.batch.controller;

import com.alibaba.fastjson.JSON;
import com.hht.study.batch.model.ResRecord;
import com.hht.study.batch.mq.producer.BatchProducer;
import com.hht.study.batch.util.ExcelUtil;
import com.hht.study.batch.util.SaveUtil;
import com.hht.study.common.model.JsonResult;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author hht
 * @date 2022/3/1
 */
@RequestMapping("/batch")
@RestController
public class BatchController {

    @Resource
    BatchProducer batchProducer;

    @PostMapping("/resolveExcel")
    public void resolveExcel(@RequestParam("file") MultipartFile file){
        try {
            List<Map<String, String>> content = ExcelUtil.getContent(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/execute")
    public JsonResult<String> execute(@RequestParam("file") MultipartFile file){
        String url="http://localhost:9898";
        String api="/test";
        String apiUrl=url+api;
        RestTemplate restTemplate = new RestTemplate();
        List<ResRecord> resRecords=new ArrayList<>();
        String sn= UUID.randomUUID().toString();
        try {
            List<Map<String, String>> content = ExcelUtil.getContent(file.getInputStream());
            content.forEach(map->{
                ResRecord resRecord = new ResRecord();
                resRecord.setContent(map);
                try {
                    JsonResult res = restTemplate.postForObject(apiUrl, map, JsonResult.class);
                    if(res==null){
                        resRecord.setMsg("请求结果为null");
                        resRecord.setStatus(0);
                        resRecord.setSn(sn);
                    }else{
                        resRecord.setMsg(res.getMsg());
                        resRecord.setStatus(res.isSuccess()?1:0);
                        resRecord.setSn(sn);
                    }
                }catch(Exception e){
                    resRecord.setMsg("请求异常"+e.getMessage());
                }finally {
                    resRecords.add(resRecord);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        //模拟持久化
        SaveUtil.BatchResult.put(sn,resRecords);
        return JsonResult.OK(sn);
    }

    @PostMapping("/export")
    public void exportExcel(@RequestParam("sn") String sn){
        batchProducer.sendDownLoad(sn);
    }

    class executeTask implements Runnable{

        @Override
        public void run() {

        }
    }
}
