package cn.lut.cart.service;

import cn.lut.cart.mapper.CartMapper;
import com.jt.common.pojo.Cart;
import com.jt.common.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CartService {
    @Autowired(required = false)
    private CartMapper cartMapper;

    //根据userId查询购物车
    public List<Cart> queryCart(String userId) {
        return cartMapper.selectByUserId(userId);
    }

    //新增购物车
    @Autowired
    private RestTemplate restTemplate;
    public void addCart(Cart cart) {
        /*
        1 判断这个购物车商品是不是已存在 select * from t_cart user_id and product_id
        2 不存在查询结果,典型新增
            2.1补充齐全数据 productName productPrice productImage
            2.2调用以下微服务productservice ribbon调用 使用id查询product对象
        3 存在,更新数量
            3.1将旧数据num和新增新num叠加更新到数据库
         */
        //查询商品是否在购物车存在
        Cart cart1=cartMapper.selectCartByUseridAndProductid(cart.getUserId(),cart.getProductId());
        //不存在
        if (cart1==null){
            String url="http://product-service/product/manage/item/"+cart.getProductId();
            Product product = restTemplate.getForObject(url, Product.class);
            cart.setProductName(product.getProductName());
            cart.setProductPrice(product.getProductPrice());
            cart.setProductImage(product.getProductImgurl());
            //增加商品到购物车
            cartMapper.insertCart(cart);
        }else {
            //存在
            int newNum=cart.getNum();
            int oldNum=cart1.getNum();
            cart.setNum(newNum+oldNum);
            cartMapper.updateNum(cart);

        }
    }

    //修改购物车
    public void editCartNum(Cart cart) {
        cartMapper.updateNum(cart);
    }

    //删除购物车
    public void deleteCart(Cart cart) {
        //delete from t_cart where userId and productId;
        cartMapper.deleteCartByUseridAndProductid(cart);
    }
}
