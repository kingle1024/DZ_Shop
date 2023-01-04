package com.dz.shop.admin.product;

import com.dz.shop.Page.PageUtil;
import com.dz.shop.entity.BoardFile;
import com.dz.shop.entity.ProductVO;
import com.dz.shop.service.ProductService;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/product/*")
public class ProductAPI {
    private static final Logger logger = LoggerFactory.getLogger(ProductAPI.class);
    @Autowired
    ProductService productService;
    private final String fileRepository = "/Users/ejy1024/Documents/upload";


    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Map<String, Object> search(
            @RequestParam String search,
            @RequestParam String pageIndex
    ){
        logger.info("ProductAPI.search");
        Map<String, Object> map = new HashMap<>();
        PageUtil pageUtil = productService.pageUtil(search, pageIndex, "");

        map.put("list", pageUtil.getList());
        map.put("pager", pageUtil.paper());

        return map;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Map<String, Object> add(
            HttpServletRequest request
    ){
        try {
            HttpSession session = request.getSession();
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 업로드 파일 임시로 저장할 경로 설정 -> /temp
            factory.setRepository(new File((fileRepository+"/temp"))); // 임시공간
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(request);

            String title = "";
            String content = "";
            String price = "";
            List<BoardFile> boardFiles = new ArrayList<>();
            String thumbnail = null;

            for(FileItem item : items){
                if(item.isFormField()){
                    String value = new String(item.getString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

                    switch (item.getFieldName()){
                        case "title":{
                            title = value;
                            break;
                        }
                        case "editor":{
                            content = value;
                            break;
                        }
                        case "price":{
                            price = value;
                            break;
                        }
                    }
                }else{
                    if(item.getSize() == 0) continue;
                    String[] contentType = item.getContentType().split("/");
                    String realName = System.currentTimeMillis() + "." + contentType[1];
                    thumbnail = "thumbnail_"+ System.nanoTime();

                    BoardFile boardFile = BoardFile.builder()
                            .org_name(item.getName())
                            .real_name(realName)
                            .content_type(item.getContentType())
                            .length((int) item.getSize())
                            .register_date(LocalDateTime.now())
                            .build();

                    boardFiles.add(boardFile);

                    File saveFile = new File(fileRepository +"/"+realName);
                    if(saveFile.exists()){
                        saveFile = new File(fileRepository +"/"+realName +"_1");
                    }

                    // 실제 저장
                    item.write(saveFile);

                    // 썸네일 저장
                    if(item.getContentType().contains("image")) {
                        File thumbFile = new File(fileRepository + "/" + thumbnail);
                        Thumbnails.of(saveFile)
                                .size(50, 50)
                                .outputFormat("png").toFile(thumbFile);
                        thumbnail = thumbFile + "." + contentType[1];
                        boardFile.setThumbnail(thumbnail);
                    }
                }
            }

            String writer = (String) session.getAttribute("sessionName");
            String writerId = (String) session.getAttribute("sessionUserId");
            System.out.println("thumbnail = " + thumbnail);
            ProductVO product = ProductVO.builder()
                    .title(title)
                    .content(content)
                    .writer(writer)
                    .regist_date(LocalDateTime.now())
                    .price(Integer.parseInt(price))
                    .thumbnail(thumbnail)
                    .build();

            long result = productService.add(product, boardFiles);
            Map<String, Object> jsonResult = new HashMap<>();

            if (result < 0) {
                jsonResult.put("status", false);
                jsonResult.put("message", "등록 실패");
            }else {
                jsonResult.put("status", true);
                jsonResult.put("url", request.getContextPath()+"/admin/product/list");
                jsonResult.put("message", "등록 성공");
            }
            System.out.println(jsonResult);

            return jsonResult;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
