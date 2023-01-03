package com.dz.shop.admin;

import com.dz.shop.Page.PageUtil;
import com.dz.shop.entity.ProductVO;
import com.dz.shop.service.AdminProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
@RequestMapping("/admin/product")
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	@Autowired
	AdminProductService adminProductService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request,
			Model model){

		String search = request.getParameter("search");
		String pageIndex = request.getParameter("pageIndex");

		PageUtil pageUtil = adminProductService.pageUtil(search, pageIndex, "");

		model.addAttribute("list", pageUtil.getList());
		model.addAttribute("pager", pageUtil.paper());
		return "admin/product/list";
	}

	@RequestMapping(value = "/view/{no}", method = RequestMethod.GET)
	public String view(Model model, @PathVariable("no") String no){


		model.addAttribute("no", no);

		return "admin/product/view";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String create(){
		return "admin/product/add";
	}

	@RequestMapping(value = "/thumbnail.do", method = RequestMethod.GET)
	public void fileDownload(
			@RequestParam("no") String no, HttpServletResponse response) throws IOException {
		ProductVO product = adminProductService.getProduct(no);
		if(product != null){
			response.setHeader("Cache-Control", "no-cache");
			response.addHeader("Content-disposition", "attachment; fileName=" + product.getThumbnail());
			BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
			BufferedInputStream in = new BufferedInputStream(Files.newInputStream(Paths.get(product.getThumbnail())));

			byte[] data = new byte[4096];
			int count;
			while(true){
				count = in.read(data);
				if (count <= 0) break;
				out.write(data);
			}
			out.close();
			in.close();
		}
	}
}
