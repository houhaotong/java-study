package com.hht.study.batch.component;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hht
 * @date 2022/3/3
 */
public class MapConverter implements Converter<Map> {

    @Override
    public Class supportJavaTypeKey() {
        return Map.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Map convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return JSON.parseObject(cellData.getStringValue(),HashMap.class);
    }

    @Override
    public CellData convertToExcelData(Map value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData(JSON.toJSONString(value));
    }
}
