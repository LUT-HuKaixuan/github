package cn.lut.lucene.index;

import cn.lut.lucene.analyzer.IKAnalyzer6x;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * 1.指向索引
 * 2.创建搜索对象searcher(包装了输入流)
 * 3.创建搜索query条件
 * 4.searcher 先读倒排索引表,计算搜索
 *   查询结果遍历循环输出(浅查询逻辑)
 */
public class SearchIndex {
    //词项查询
    @Test
    public void termQuery() throws IOException {
        //1.指向索引文件
        Path path = Paths.get("e:/app/a/04-internet-archetype/index01");
        FSDirectory dir = FSDirectory.open(path);
        //2.生成输入流reader
        DirectoryReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        //3.创建搜索query条件
        //词项 2个属性 域名 词语
        Term term = new Term("content", "中国");
        Query query=new TermQuery(term);
        //4.searcher读取倒排索引表,计算结果,前十条
        //TopDocs topDocs = searcher.search(query, 10);
        int page=2;
        int rows=1;
        TopDocs topDocs = searcher.search(query, page*rows);
        System.out.println("搜索到的dom数量："+topDocs.totalHits);

        //数组中包含了 查询到的 最多 前10个documentId
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //scoreDocs数组:
        // [doc=1 score=0.5406597 shardIndex=0, doc=0 score=0.41903937 shardIndex=0]
        System.out.println("scoreDocs数组:"+Arrays.toString(scoreDocs));

        int start=(page-1)*rows;
        for(int i=0;i<page*rows;i++){
            if(i>=start){
                //满足起始条件下标,开始读数据;
                int docId = scoreDocs[i].doc;
                Document doc = searcher.doc(docId);
                System.out.println("title:"+doc.get("title"));
                System.out.println("content:"+doc.get("content"));
                System.out.println("publisher:"+doc.get("publisher"));
                System.out.println("click:"+doc.get("click"));
                System.out.println("web:"+doc.get("web"));
            }
        }
        //遍历循环输出
        /*for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            //有了id才读数据
            Document doc = searcher.doc(docId);
            System.out.println("---------------------------");
            System.out.println("title:"+doc.get("title"));
            System.out.println("content:"+doc.get("content"));
            System.out.println("publisher:"+doc.get("publisher"));
            System.out.println("click:"+doc.get("click"));
            System.out.println("web:"+doc.get("web"));
        }*/

    }
    //多域查询
    @Test
    public void MultiFieldQuery() throws IOException, ParseException {
        //1.指向索引文件
        Path path = Paths.get("e:/app/a/04-internet-archetype/index01");
        FSDirectory dir = FSDirectory.open(path);
        //2.生成输入流reader
        DirectoryReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);

        //3.创建搜索query条件
        //TODO 多域查询
        String[] fields={"web","content"};
        //拿到文本解析器
        /*
            MultiFieldQueryParser parser=
                new MultiFieldQueryParser(fields,new StandardAnalyzer());
            Query query=parser.parse("中国,美国");
            //parse 多个域,和分词器,
            //使用分词器对text文本查询进行分词计算 中,国,美,国
            //做排列组合 title:中,title:美,title:国,content:中,content:美,content:国
         */
        MultiFieldQueryParser parser =
                new MultiFieldQueryParser(fields, new IKAnalyzer6x());
        //做排列组合 web:中国,web:网站,content:中国,content:网站 并集
        Query query=parser.parse("中国,网站");

        //4.searcher读取倒排索引表,计算结果,前十条
        TopDocs topDocs = searcher.search(query, 10);
        System.out.println("搜索到的dom数量："+topDocs.totalHits);

        //数组中包含了 查询到的 最多 前10个documentId
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //scoreDocs数组:
        System.out.println("scoreDocs数组:"+Arrays.toString(scoreDocs));

        //遍历循环输出
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            //有了id才读数据
            Document doc = searcher.doc(docId);
            System.out.println("---------------------------");
            System.out.println("title:"+doc.get("title"));
            System.out.println("content:"+doc.get("content"));
            System.out.println("publisher:"+doc.get("publisher"));
            System.out.println("click:"+doc.get("click"));
            System.out.println("web:"+doc.get("web"));
        }
    }

    //布尔查询
    @Test
    public void booleanQuery() throws Exception {
        //1.指向索引文件
        Path path = Paths.get("e:/app/a/04-internet-archetype/index01");
        FSDirectory dir = FSDirectory.open(path);
        //2.生成输入流reader
        DirectoryReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);

        //3.创建搜索query条件
        //TODO 布尔查询
        //准备子条件 子条件单独使用 可以查询一批结果
        Query query1=new TermQuery(new Term("content","美国"));
        Query query2=new TermQuery(new Term("title","美国"));
        ///利用2个query构造2个boolean子条件对象
        /*
            MUST: boolean结果必须是当前query的子集
            MUST_NOT:boolean结果必须不是当前query的子集
         */
        BooleanClause clause1 = new BooleanClause(query1, BooleanClause.Occur.MUST);
        BooleanClause clause2 = new BooleanClause(query2, BooleanClause.Occur.MUST);
        Query query=new BooleanQuery.Builder().
                add(clause1).add(clause2).build();

        //4.searcher读取倒排索引表,计算结果,前十条
        TopDocs topDocs = searcher.search(query, 10);
        System.out.println("搜索到的dom数量："+topDocs.totalHits);

        //数组中包含了 查询到的 最多 前10个documentId
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //scoreDocs数组:
        System.out.println("scoreDocs数组:"+Arrays.toString(scoreDocs));

        //遍历循环输出
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            //有了id才读数据
            Document doc = searcher.doc(docId);
            System.out.println("---------------------------");
            System.out.println("title:"+doc.get("title"));
            System.out.println("content:"+doc.get("content"));
            System.out.println("publisher:"+doc.get("publisher"));
            System.out.println("click:"+doc.get("click"));
            System.out.println("web:"+doc.get("web"));
        }
    }

    //范围查询
    //可以对域属性中 具备**Point类型的属性值进行范围搜索
    @Test
    public void rangeQuery() throws Exception {
        //1.指向索引文件
        Path path = Paths.get("e:/app/a/04-internet-archetype/index01");
        FSDirectory dir = FSDirectory.open(path);
        //2.生成输入流reader
        DirectoryReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);

        //3.创建搜索query条件
        //TODO 范围查询
        //范围搜索条件,click 0-60 60-100搜索
        Query query = IntPoint.newRangeQuery
                ("click",60,100);

        //4.searcher读取倒排索引表,计算结果,前十条
        TopDocs topDocs = searcher.search(query, 10);
        System.out.println("搜索到的dom数量：" + topDocs.totalHits);

        //数组中包含了 查询到的 最多 前10个documentId
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //scoreDocs数组:
        System.out.println("scoreDocs数组:" + Arrays.toString(scoreDocs));

        //遍历循环输出
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            //有了id才读数据
            Document doc = searcher.doc(docId);
            System.out.println("---------------------------");
            System.out.println("title:" + doc.get("title"));
            System.out.println("content:" + doc.get("content"));
            System.out.println("publisher:" + doc.get("publisher"));
            System.out.println("click:" + doc.get("click"));
            System.out.println("web:" + doc.get("web"));
        }
    }


}
