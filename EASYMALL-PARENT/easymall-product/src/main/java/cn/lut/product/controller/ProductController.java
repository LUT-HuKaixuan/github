package cn.lut.product.controller;

import cn.lut.product.service.ProductService;
import com.jt.common.pojo.Product;
import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/manage")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * 分页查询
     * 后台地址：/product/manage/pageManage
     * 请求参数：Integer page（第几页）,Integer rows（一页有几行）
     * 返回值：EasyUIResult对象
     */
    @RequestMapping("/pageManage")
    public EasyUIResult queryByPage(Integer page,Integer rows){
        //业务逻辑中的数字一般使用封装类型,原因多了一个null值可以表示
        //更现实的意义 Integer score 白卷0分,缺考null
        if(page==null||rows==null){
            page=1;
            rows=5;
        }
        EasyUIResult result=productService.queryByPage(page,rows);
        return result;
    }

    /**
     * 单个商品查询
     * 后台地址：/product/manage/item/{productId}
     * 请求参数：@PathVariable String productid
     * 返回值：Product对象
     */
    @RequestMapping("/item/{productId}")
    public Product queryOneProduct(@PathVariable String productId){
        Product product=productService.queryOneProduct(productId);
        return product;
    }

    /**
     * 新增商品
     * /product/manage/save
     * Post
     * Product product对象接参 缺少productId
     * 返回SysResult对象
     */
    @RequestMapping("/save")
    public SysResult addProduct(Product product){
        try {
            productService.addProduct(product);
            return SysResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return SysResult.build(201,"新增失败",null);
        }

    }

    /**
     * 修改单个商品信息
     * /product/manage/update
     * Post
     * Product product
     * 返回SysResult对象的json
     */
    @RequestMapping("/update")
    public SysResult editProduct(Product product) {
        try{
            productService.editProduct(product);
            return SysResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return SysResult.build(201,"更新失败",null);
        }

    }



}
