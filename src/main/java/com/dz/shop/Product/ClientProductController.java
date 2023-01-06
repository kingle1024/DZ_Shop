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
import java.io.*;
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
    private final String fileRepository = "/Users/ejy1024/Documents/upload";

    @RequestMapping(value = "/view/{no}", method = RequestMethod.GET)
    public String view(Model model, @PathVariable("no") String no, HttpSession session){
        logger.info("ClientProductController.view");
        ProductVO product = productService.getProduct(no);
        model.addAttribute("product", product);

        PageUtil pageUtil = productService.pageUtil(no, "", "comment");
        model.addAttribute("cs", pageUtil.getList());

        List<BoardFile> files = productService.fileList(no);
        System.out.println("files = " + files);
        model.addAttribute("files", files);

        String userId = (String) session.getAttribute("sessionUserId");
        String myStatus = popularityService.findByBnoAndUserIdAndIsDelete(no, userId);
        model.addAttribute("myStatus", myStatus);

        return "product/view";
    }

    @RequestMapping(value = "/thumbnail.do", method = RequestMethod.GET)
    public void thumbnail(
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

    @RequestMapping(value = "/fileDownload.do", method = RequestMethod.GET)
    public void fileDownload(@RequestParam("fid") String fid, HttpServletResponse response)
             {
                 System.out.println("f_id = " + fid);
        BoardFile boardFile = productService.getBoardFile(fid);
                 System.out.println("boardFile = " + boardFile);
        try {
            if (boardFile != null) {
                response.setHeader("Cache-Control", "no-cache");
                response.setContentLength(boardFile.getLength());
                response.addHeader("Content-disposition", "attachment; fileName=" + boardFile.getOrg_name());
                BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                String path = fileRepository + "/" + boardFile.getReal_name();
                BufferedInputStream in = new BufferedInputStream(Files.newInputStream(Paths.get(path)));

                byte [] data = new byte[4096];
                int count;
                while(true) {
                    count = in.read(data);
                    if (count <= 0) break;
                    out.write(data);
                }
                out.close();
                in.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
