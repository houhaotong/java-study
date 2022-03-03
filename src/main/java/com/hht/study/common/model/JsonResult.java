package com.hht.study.common.model;

import lombok.Data;

/**
 * @author hht
 * @date 2022/3/1
 */
@Data
public class JsonResult <T>{

    private String code;

    private T data;

    private boolean success;

    private String msg;

    public static JsonResult OK(){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setSuccess(true);
        return jsonResult;
    }

    public static<T> JsonResult<T> OK(T data){
        JsonResult<T> jsonResult = new JsonResult();
        jsonResult.setSuccess(true);
        jsonResult.setData(data);
        return jsonResult;
    }
}
