package cn.lut.cart.mapper;

import com.jt.common.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    List<Cart> selectByUserId(String userId);

    Cart selectCartByUseridAndProductid(@Param("userId") String userId,
                                        @Param("productId") String productId);

    void insertCart(Cart cart);

    void updateNum(Cart cart);

    void deleteCartByUseridAndProductid(Cart cart);
}
