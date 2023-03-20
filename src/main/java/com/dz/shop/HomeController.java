package com.dz.shop;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import com.dz.shop.Page.PageUtil;
import com.dz.shop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {		
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	ProductService productService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main(HttpServletRequest request, Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );

		String search = request.getParameter("search");
		String pageIndex = request.getParameter("pageIndex");

		PageUtil pageUtil = productService.pageUtil(search, pageIndex, "product");

		model.addAttribute("list", pageUtil.getList());
		model.addAttribute("pager", pageUtil.paper());

		return "main";
	}
}
