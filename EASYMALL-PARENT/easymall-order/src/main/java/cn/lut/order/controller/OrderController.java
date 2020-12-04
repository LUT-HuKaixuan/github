package cn.lut.order.controller;

import cn.lut.order.service.OrderService;
import com.jt.common.pojo.Order;
import com.jt.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order/manage")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /*
        新增订单
     */
    @RequestMapping("/save")
    public SysResult addOrder(Order order){
        try {
            orderService.addOrder(order);
            return SysResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return SysResult.build(201,"新增订单失败",null);
        }
    }

    /*
        订单查询
        /order/manage/query/{userId}

     */
    @RequestMapping("/query/{userId}")
    public List<Order> queryMyOrders(@PathVariable("userId") String userId){
        return orderService.queryMyOrders(userId);
    }
    /*
        删除订单
        /order/manage/delete/{orderId}
     */
    @RequestMapping("/delete/{orderId}")
    public SysResult deleteOrder(@PathVariable("orderId") String orderId){
        try {
            orderService.deleteOrder(orderId);
            return SysResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return SysResult.build(201,"删除订单失败",null);
        }
    }
}
