package com.hht.study.batch.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.hht.study.batch.component.MapConverter;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hht
 * @date 2022/3/1
 */
public class ExcelUtil {

    private static Integer FILE_NUM=1;

    private static final String FILE_NAME="batchRecord";

    private static final String FILE_FIX=".xlsx";

    public static List<Map<String, String>> getContent(InputStream inputStream){
        List<Map<String, String>> content = new ArrayList<>();

        //Map对应每一行下标对应的值
        AnalysisEventListener<Map<Integer,String>> listener=new AnalysisEventListener<Map<Integer,String>>() {
            //存储表头
            Map<Integer,String> head=new HashMap<>();
            //指示是否为头部
            boolean isHead=true;

            //每一行数据都会回调该方法
            @Override
            public void invoke(Map<Integer,String> data, AnalysisContext analysisContext) {

                if(isHead){
                    head=data;
                    isHead=false;
                    return;
                }

                Map<String,String> res=new HashMap<>();
                data.forEach((k,v)->{
                    //将key转为表头名进行存储
                    res.put(head.get(k),v);
                });
                content.add(res);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {

            }
        };
        ExcelReader reader = EasyExcel.read(inputStream,listener).build();
        ReadSheet sheet = EasyExcel.readSheet(0).headRowNumber(0).build();
        reader.read(sheet);
        return content;
    }

    public static<T> void buildExcelToLocal(List<T> records,Class<T> clazz){
        if(CollectionUtils.isEmpty(records)){
            return ;
        }
        ExcelWriter writer=null;
        try{
            writer = EasyExcel.write(FILE_NAME + FILE_NUM+FILE_FIX,clazz).registerConverter(new MapConverter()).build();

            WriteSheet writeSheet = EasyExcel.writerSheet(0).sheetName("批处理结果").build();
            writer.write(records,writeSheet);
            FILE_NUM++;
        }finally {
            if(writer!=null){
                writer.finish();
            }
        }
    }
}
