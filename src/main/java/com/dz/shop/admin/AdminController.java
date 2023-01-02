package com.dz.shop.admin;

import com.dz.shop.Page.PageUtil;
import com.dz.shop.mappers.AdminDAO;
import com.dz.shop.service.AdminService;
import com.dz.shop.service.AdminServiceImpl;
import com.dz.shop.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	@Autowired
	AdminService adminService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, HttpServletResponse response
					   , Model model) {
		String search = request.getParameter("search");
		String pageIndex = request.getParameter("pageIndex");

		PageUtil pageUtil = adminService.pageUtil(search, pageIndex, "");

		model.addAttribute("list", pageUtil.getList());
		model.addAttribute("pager", pageUtil.paper());
		return "admin/list";
	}

}
