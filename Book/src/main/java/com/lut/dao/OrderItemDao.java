package com.lut.dao;

import com.lut.entity.OrderItem;

import java.util.List;
import java.util.Map;

public interface OrderItemDao {
	
	public List<OrderItem> findByOid(String oid);
	
	public OrderItem load(String orderItemId);
	
	public void addOrderItem(List<Map<String,Object>> mapList);
}
