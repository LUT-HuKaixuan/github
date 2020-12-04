package cn.lut.cart.controller;

import cn.lut.cart.service.CartService;
import com.jt.common.pojo.Cart;
import com.jt.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart/manage")
public class CartController {
    @Autowired
    private CartService cartService;

    /*
        查询我的购物车
        /cart/manage/query
     */
    @RequestMapping("/query")
    public List<Cart> queryCart(String userId){
        return cartService.queryCart(userId);
    }

    /*
        新增购物车
        /cart/manage/save
     */
    @RequestMapping("/save")
    public SysResult addCart(Cart cart){
        try{
            cartService.addCart(cart);
            return SysResult.ok();
        }catch(Exception e){
            e.printStackTrace();
            return SysResult.build(201,"新增失败",null);
        }

    }

    /*
        更新购物车
        /cart/manage/update
        参数
        ---Cart cart,具有三个属性userId,productId,num
     */
    @RequestMapping("/update")
    public SysResult editCartNum(Cart cart){
        try{
            cartService.editCartNum(cart);
            return SysResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return SysResult.build(201,"更新数量失败",null);
        }
    }

    /*
        删除购物车
        /cart/manage/delete
        参数
        ---Cart cart,具有两个属性userId,productId
     */
    @RequestMapping("/delete")
    public SysResult deleteCart(Cart cart){
        try{
            cartService.deleteCart(cart);
            return SysResult.ok();
        }catch(Exception e){
            e.printStackTrace();
            return SysResult.build(201,"删除购物车失败",null);
        }
    }
}
