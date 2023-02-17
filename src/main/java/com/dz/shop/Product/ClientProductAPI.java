package com.dz.shop.Product;

import com.dz.shop.Page.PageUtil;
import com.dz.shop.entity.BoardFile;
import com.dz.shop.entity.Popularity;
import com.dz.shop.service.PopularityService;
import com.dz.shop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/product/*")
public class ClientProductAPI {
    private static final Logger logger = LoggerFactory.getLogger(ClientProductAPI.class);
    @Autowired
    PopularityService popularityService;
    @Autowired
    ProductService productService;
    private final String fileRepository = "/Users/ejy1024/Documents/upload";

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Map<String, Object> search(
            @RequestParam String search,
            @RequestParam String pageIndex
    ){
        Map<String, Object> map = new HashMap<>();
        PageUtil pageUtil = productService.pageUtil(search, pageIndex, "product");

        map.put("list", pageUtil.getList());
        map.put("pager", pageUtil.paper());

        return map;
    }

    @RequestMapping(value = "/popularity", method = RequestMethod.POST)
    public Map<String, Object> popularity(HttpSession session,
                             @RequestBody Popularity popularity
    ){
        logger.info("ClientProductAPI.popularity");
        String userId = (String) session.getAttribute("sessionUserId");
        popularity.setUserId(userId);

        return popularityService.add(popularity);
    }

    @RequestMapping(value = "/fileDownload.do", method = RequestMethod.GET)
    public void fileDownload(@RequestParam("f_id") String f_id, HttpServletResponse response)
    {
        BoardFile boardFile = productService.getBoardFile(f_id);
        System.out.println("boardFile = " + boardFile);
        try {
            if (boardFile != null) {
                response.setHeader("Cache-Control", "no-cache");
                response.setContentLength(boardFile.getLength());
                response.addHeader("Content-disposition", "attachment; fileName=" + boardFile.getOrg_name());
                BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                BufferedInputStream in = new BufferedInputStream(Files.newInputStream(Paths.get(fileRepository + "/" + boardFile.getReal_name())));

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
