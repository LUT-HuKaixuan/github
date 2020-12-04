package cn.lut.lucene.index;

import cn.lut.lucene.analyzer.IKAnalyzer6x;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 1.选择一个文件夹,作为保存索引数据的目标
 * 2.创建一个输出流对象Writer
 * 3.读取数据源,封装document对象数据
 *   *根据数据源的结构定义document的各种域属性的内容
 * 4.通过writer输出流,将数据经过倒排索引计算输出到文件形成所以的数据
 */
public class CreateIndex {
    @Test
    public void createIndex() throws IOException {
        //1.指定 e:/app/a/04-internet-archetype/index01
        Path path = Paths.get("e:/app/a/04-internet-archetype/index01");
        FSDirectory dir = FSDirectory.open(path);

        //2.创建writer输出流,对接到dir,并且输出流后续计算数据时使用到分词器

        IndexWriterConfig config = new IndexWriterConfig(new IKAnalyzer6x());
        // 判断 index01 是否存在
        // CREATE 已存在时 覆盖，不存在时 创建
        // APPEND 已存在时 追加，不存在时 报错
        // CREATE_OR_APPEND 有则追加,无则创建
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter writer = new IndexWriter(dir, config);

        //3.封装document对象
        Document doc1=new Document();
        Document doc2=new Document();
        Document doc3=new Document();

        doc1.add(new TextField("title","美国耍赖", Field.Store.YES));
        //域属性类型Filed有哪些
        //Store.YES/NO是什么意思?
        doc1.add(new TextField("content","频繁甩锅,控诉中国疫情信息透露不明确",
                Field.Store.YES));
        doc1.add(new TextField("publisher","新华网", Field.Store.YES));
        doc1.add(new IntPoint("click",58));
        doc1.add(new TextField("web","http://www.news.com/1.html", Field.Store.YES));

        doc2.add(new TextField(
                "title","美国甩锅", Field.Store.YES));
        doc2.add(new TextField("content","美国谎称,援助中国1亿美金,中国外交部称从未收到",
                Field.Store.YES));
        doc2.add(new TextField("publisher","新华网", Field.Store.YES));
        doc2.add(new IntPoint("click",66));
        doc2.add(new StringField("click","66次", Field.Store.YES));
        doc2.add(new StringField("web","这个网站不存在", Field.Store.YES));

        doc3.add(new TextField("title","掌门去世了", Field.Store.YES));
        //域属性类型Filed有哪些
        //Store.YES/NO是什么意思?
        doc3.add(new TextField("content","门派内弟子开始争抢掌门的钱",
                Field.Store.YES));
        doc3.add(new TextField("publisher","纯阳日报", Field.Store.YES));
        doc3.add(new IntPoint("click",100));
        doc3.add(new TextField("web","没有网址，不外传", Field.Store.YES));

        //writer创建索引
        //writer.addDocument(doc1);
        //writer.addDocument(doc2);
        List<Document> docs=new ArrayList<>();
        docs.add(doc1);
        docs.add(doc2);
        docs.add(doc3);
        writer.addDocuments(docs);
        writer.commit();
        System.out.println("索引创建完毕");

    }
}
