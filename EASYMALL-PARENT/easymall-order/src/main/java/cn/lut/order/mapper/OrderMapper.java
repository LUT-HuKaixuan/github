package cn.lut.order.mapper;

import com.jt.common.pojo.Order;

import java.util.List;

public interface OrderMapper {
    void insertOrder(Order order);

    List<Order> selectOrderByUserid(String userId);

    void deleteByOrderid(String orderId);
}
