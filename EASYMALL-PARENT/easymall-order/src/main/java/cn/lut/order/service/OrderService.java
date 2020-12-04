package cn.lut.order.service;

import cn.lut.order.mapper.OrderMapper;
import com.jt.common.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired(required = false)
    private OrderMapper orderMapper;

    //新增订单
    public void addOrder(Order order) {
        //Order order,其中缺少order的id值,缺支付状态,创建订单时间
        //
        //对象还包含了订单商品数据orderItems
        String orderId= UUID.randomUUID().toString();
        order.setOrderId(orderId);
        order.setOrderTime(new Date());

        //业务层处理多条写入
        /*orderMapper.insertOrder(order);//主表数据order_id,order_money,order_rece..
        for(OrderItem oi: order.getOrderItems()){
            orderMapper.insertOrderItem
        }*/
        //持久层映射文件中,做多条写入
        orderMapper.insertOrder(order);//写入主表个子表

    }

    public List<Order> queryMyOrders(String userId) {
        return orderMapper.selectOrderByUserid(userId);
    }

    public void deleteOrder(String orderId) {
        orderMapper.deleteByOrderid(orderId);
    }
}
