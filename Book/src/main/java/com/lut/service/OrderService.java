package com.lut.service;

import com.lut.entity.Order;
import com.lut.pager.PageBean;

public interface OrderService {
	
	public void updateStatus(String oid,int status);
	
	public int findStatus(String oid);
	
	public Order load(String oid);
	
	public void createOrder(Order order);
	
	public PageBean<Order> myOrders(String uid,int pc);
	/**
	 * 查询所有订单
	 * @param pc
	 * @return
	 */
	public PageBean<Order> findAll(int pc);
	
	public PageBean<Order> findByStatus (int stauts ,int pc);
}
