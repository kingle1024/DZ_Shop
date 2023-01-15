package com.dz.shop.admin.order;

import com.dz.shop.service.AdminOrderService;
import com.dz.shop.Delivery.DeliveryStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/order")
public class AdminOrderController {
	private static final Logger logger = LoggerFactory.getLogger(AdminOrderController.class);
	@Autowired
	AdminOrderService adminOrderService;
	@Autowired
	DeliveryStatusService deliveryStatusService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model){
		logger.info("AdminOrderController.list");
		model.addAttribute("list", adminOrderService.list());
		model.addAttribute("deliveryStatusList", deliveryStatusService.list());

		return "admin/order/list";
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(
			@RequestParam("no") String no, Model model
	){
		model.addAttribute("detail", adminOrderService.detail(no));

		return "admin/order/detail";
	}
}