package com.lut.admin.service;

import com.lut.admin.entity.Admin;

public interface AdminService {	
	public Admin findByAdminnameAndAdminpwd(String adminname, String adminpwd);	
}
