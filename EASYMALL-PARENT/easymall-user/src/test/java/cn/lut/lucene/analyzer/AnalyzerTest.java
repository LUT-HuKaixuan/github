package cn.lut.lucene.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

/**
 * 通过对analyzer的方法调用,对字符串进行
 * 不同的类型的分词器计算
 */
public class AnalyzerTest {
    //准备一个调用方法,调用不同分词器计算 打印分词计算文本结果
    public void printTerm(Analyzer a, String msg) throws IOException {
        //reader流获取
        StringReader reader = new StringReader(msg);
        //调用分词器获取token
        TokenStream tokenStream = a.tokenStream("test", reader);
        //重置指针
        tokenStream.reset();
        //获取文本属性,让指针跳动
        CharTermAttribute attribute = tokenStream.getAttribute(CharTermAttribute.class);
        while (tokenStream.incrementToken()){
            //打印所有文本此项
            System.out.println(attribute.toString());
        }

    }

    @Test
    public void run() throws Exception {

        //简单分词器:SimpleAnalyzer 对文本中标点 空格

        //标准分词器:StandardAnalyzer 英文处理的单词逻辑,中文,对字处理

        //空格分词器:WhitespaceAnalyzer 碰到空格分词计算,段逻辑,句处理,英文词

        //智能中文分词器:SmartChinese 对比ik使用的中文分词器

        Analyzer a1=new SimpleAnalyzer();

        Analyzer a2=new StandardAnalyzer();

        Analyzer a3=new WhitespaceAnalyzer();

        Analyzer a4=new SmartChineseAnalyzer();

        Analyzer a5=new IKAnalyzer6x();
        //文本数据
        String msg="中国,网站";

        System.out.println("**********简单**********");
        printTerm(a1,msg);
        System.out.println("**********标准**********");
        printTerm(a2,msg);
        System.out.println("**********空格**********");
        printTerm(a3,msg);
        System.out.println("**********中文**********");
        printTerm(a4,msg);
        System.out.println("**********IK**********");
        printTerm(a5,msg);
    }
}
