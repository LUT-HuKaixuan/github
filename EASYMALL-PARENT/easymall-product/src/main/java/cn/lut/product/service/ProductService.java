package cn.lut.product.service;

import cn.lut.product.mapper.ProductMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jt.common.pojo.Product;
import com.jt.common.utils.MapperUtil;
import com.jt.common.vo.EasyUIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ProductService {
    @Autowired(required = false)
    private ProductMapper productMapper;

    /*
        封装2个数据到EasyUIResult对象
            Integer total:查询的总条数;
            List<Product> pList:查询出第几页的数据结果;
     */
    public EasyUIResult queryByPage(Integer page, Integer rows) {
        EasyUIResult result=new EasyUIResult();
        Integer total=productMapper.selectCount();
        result.setTotal(total);

        int start=(page-1)*rows;
        List<Product> pList=productMapper.selectProductByPage(start,rows);
        result.setRows(pList);
        return result;
    }

    /*
        单个商品查询
     */
    @Autowired
    private StringRedisTemplate redisTemplate;
    public Product queryOneProduct(String productId) {
        //TODO redis 应用缓存之被动缓存
        /*
         * 可以在查询时添加缓存逻辑(被动缓存),利用商品的id值,做成redis的key到缓存判断存在
         * 存在:数据缓存命中,直接将缓存数据返回
         * 不存在:数据缓存未命中,再到数据库查询,同时将数据库查询的结果放到redis一份供后续使用
         */
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        String key="product_"+productId;
        //如果缓存命中
        if (redisTemplate.hasKey(key)){
            //将缓存数据返回
            String productJson = opsForValue.get(key);
            try {
                Product product = MapperUtil.MP.readValue(productJson, Product.class);
                return product;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
        //缓存不存在
        try {
            Product product = productMapper.selectProductById(productId);
            //创建value值,使用代码存储到redis一份
            String productJson = MapperUtil.MP.writeValueAsString(product);
            //设置2天超时时间
            opsForValue.set(key,productJson,2, TimeUnit.DAYS);
            return product;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    public void addProduct(Product product) {
        //TODO redis 应用缓存之主动缓存
        //新增商品数据时,并不知道用户会不会查询这个商品,提前将缓存写入
        /*
            product 的id是空的 补充完成
            1100（南京） 89955（江宁区） 20100908（时间）0776（操作员编号）
            其他方式生成字符串保证唯一
            product_+currentTime/UUID 通用唯一标识码 每次生成独一无二 500万-1000万
         */
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);

        //写入缓存,实现主动缓存逻辑
        try{
            String productJson = MapperUtil.MP.writeValueAsString(product);
            String productKey="product_"+product.getProductId();
            redisTemplate.opsForValue().set(productKey,productJson,2,TimeUnit.DAYS);
        }catch (Exception e){
            e.printStackTrace();
        }

        productMapper.insertProduct(product);
    }

    public void editProduct(Product product) {
        /*
            每次修改商品数据,对应缓存查看是否存在缓存,存在先将缓存删除,再更新数据库
            /或者/直接发现缓存存在,更新缓存内容
         */
        String key="product_"+product.getProductId();
        try{
            String pJson
                    = MapperUtil.MP.writeValueAsString(product);
            redisTemplate.opsForValue()
                    .setIfPresent(
                            key,
                            pJson,
                            2,
                            TimeUnit.DAYS);
        }catch (Exception e){
            e.printStackTrace();
        }
        productMapper.updateProductById(product);
    }
}
