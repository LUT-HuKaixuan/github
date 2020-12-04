package cn.lut.controller;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ESController {
    /*
        创建索引文件
        请求地址/{indexName} put请求
        参数：路径
     */
    @RequestMapping(value = "/{indexName}",method = RequestMethod.PUT)
    public String createIndex(@PathVariable("indexName") String indexName){
        //Lucene 创建索引
        String url="e:/app/a/04-internet-archetype/indices/"+indexName;
        //如果发现这个文件存在 报错
        File dir=new File(url);
        if (dir.exists()){
            return "\"acknowledged:\":false";
        }else {

            try {
                Path path = Paths.get(url);
                FSDirectory directory = FSDirectory.open(path);
                //
                IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
                config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
                IndexWriter writer = new IndexWriter(directory, config);
                writer.commit();
                return "\"acknowledged:\":true";
            } catch (IOException e) {
                e.printStackTrace();
                return "\"acknowledged:\":false";
            }

        }
    }

}
