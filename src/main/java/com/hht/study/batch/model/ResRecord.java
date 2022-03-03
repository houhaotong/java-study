package com.hht.study.batch.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author hht
 * @date 2022/3/2
 */
@Data
public class ResRecord {

    @ExcelProperty(value = "任务编号")
    private String sn;

    @ExcelProperty("批处理内容")
    private Map<String,String> content;

    /**
     * 0失败 1成功
     */
    @ExcelProperty("执行状态")
    private Integer status;

    @ExcelProperty("返回信息")
    private String msg;
}
