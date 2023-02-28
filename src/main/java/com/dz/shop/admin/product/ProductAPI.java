package com.dz.shop.admin.product;

import com.dz.shop.Page.PageUtil;
import com.dz.shop.Utility.CustomUtil;
import com.dz.shop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/api/admin/product/*")
public class ProductAPI {
    private static final Logger logger = LoggerFactory.getLogger(ProductAPI.class);
    @Autowired
    ProductService productService;


    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Map<String, Object> search(
            @RequestParam String search,
            @RequestParam String pageIndex
    ){
        logger.info("ProductAPI.search");
        Map<String, Object> map = new HashMap<>();
        PageUtil pageUtil = productService.pageUtil(search, pageIndex, "product");

        map.put("list", pageUtil.getList());
        map.put("pager", pageUtil.paper());

        return map;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Map<String, Object> add(
            MultipartHttpServletRequest multipartRequest, HttpSession session
    ) {
        try {
            System.out.println("API ProductController.edit");
            multipartRequest.setCharacterEncoding("UTF-8");
            Map<String, Object> map = CustomUtil.getStringObjectMap(multipartRequest);

            map.put("writer", session.getAttribute("sessionName"));
            String no = String.valueOf(productService.add(map));
            if(no != null) productService.fileAdd(no, multipartRequest.getFileMap());
        }catch (Exception e){
            e.printStackTrace();
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", true);
        resultMap.put("message", "성공");
        resultMap.put("url", multipartRequest.getContextPath()+"/admin/product/list");
        return resultMap;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Map<String, Object> edit(
            MultipartHttpServletRequest multipartRequest
    ){
        try {
            System.out.println("API ProductController.edit");
            multipartRequest.setCharacterEncoding("UTF-8");
            Map<String, Object> map = CustomUtil.getStringObjectMap(multipartRequest);
            logger.info("api map -> " + map);
            productService.edit(map);
//            productService.fileDel(map);
            // new thumbnail
            String no = (String) map.get("no");
            productService.fileAdd(no, multipartRequest.getFileMap());

        }catch (Exception e){
            e.printStackTrace();
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", true);
        resultMap.put("message", "성공");
        resultMap.put("url", multipartRequest.getContextPath()+"/admin/product/list");
        return resultMap;
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Map<String, Object> del(
            @RequestBody Map<String, Object> map
    ) {
        System.out.println("map = " + map);
        long result = productService.del(map);
        System.out.println("result = " + result);
        Map<String, Object> resultMap = new HashMap<>();

        if(result > 0) {
            resultMap.put("status", true);
            resultMap.put("message", "삭제 성공");
        }else{
            resultMap.put("status", false);
            resultMap.put("message", "삭제 실패");
        }

        return resultMap;
    }
}
