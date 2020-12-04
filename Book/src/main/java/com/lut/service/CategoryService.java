package com.lut.service;

import com.lut.entity.Category;

import java.util.List;

public interface CategoryService {

	public void delete(String cid);
	
	public int findChildrenByParent(String pid);
	
	public void edit(Category category);
	
	public Category load(String cid);
	
	public void add(Category category);
	
	public List<Category> findAll();
	
	public List<Category> findParents();
	
	public List<Category> findByParent(String pid);
}
