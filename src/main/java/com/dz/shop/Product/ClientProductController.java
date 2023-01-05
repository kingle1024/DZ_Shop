package com.dz.shop.Product;

import com.dz.shop.Page.PageUtil;
import com.dz.shop.entity.BoardFile;
import com.dz.shop.entity.ProductVO;
import com.dz.shop.service.PopularityService;
import com.dz.shop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ClientProductController {
    private static final Logger logger = LoggerFactory.getLogger(ClientProductController.class);
    @Autowired
    ProductService productService;

    @Autowired
    PopularityService popularityService;

    @RequestMapping(value = "/view/{no}", method = RequestMethod.GET)
    public String view(Model model, @PathVariable("no") String no, HttpSession session){
        logger.info("ClientProductController.view");
        ProductVO product = productService.getProduct(no);
        model.addAttribute("product", product);

        PageUtil pageUtil = productService.pageUtil(no, "", "comment");
        model.addAttribute("cs", pageUtil.getList());

        List<BoardFile> files = productService.fileList(no);
        model.addAttribute("files", files);

        String userId = (String) session.getAttribute("sessionUserId");
        String myStatus = popularityService.findByBnoAndUserIdAndIsDelete(no, userId);
        model.addAttribute("myStatus", myStatus);

        return "product/view";
    }

    @RequestMapping(value = "/thumbnail.do", method = RequestMethod.GET)
    public void fileDownload(
            @RequestParam("no") String no, HttpServletResponse response) throws IOException {
        ProductVO product = productService.getProduct(no);
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
