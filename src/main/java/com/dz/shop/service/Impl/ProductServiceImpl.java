package com.dz.shop.service.Impl;

import com.dz.shop.Dao.AdminProductDAO;
import com.dz.shop.Dao.BoardFileDAO;
import com.dz.shop.Dao.CommentDAO;
import com.dz.shop.Page.BoardParam;
import com.dz.shop.Page.PageUtil;
import com.dz.shop.entity.BoardFile;
import com.dz.shop.entity.ProductVO;
import com.dz.shop.service.ProductService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    private final AdminProductDAO adminProductDAO;
    private final BoardFileDAO boardFileDAO;
    private final CommentDAO commentDAO;
    private final String fileRepository = "/Users/ejy1024/Documents/upload";

    @Autowired
    public ProductServiceImpl(AdminProductDAO adminProductDAO, BoardFileDAO boardFileDAO, CommentDAO commentDAO) {
        this.adminProductDAO = adminProductDAO;
        this.boardFileDAO = boardFileDAO;
        this.commentDAO = commentDAO;
    }


    @Override
    public PageUtil pageUtil(String search, String pageIndex, String type) {
        BoardParam param = BoardParam.builder()
                .pageIndex(pageIndex)
                .search(search)
                .type(type)
                .build();
        param.init();

        long totalCount = 0;
        List<?> list = null;

        switch (type){
            case "product":{
                list = adminProductDAO.list(param);
                totalCount = adminProductDAO.listSize(param.getSearch());
                break;
            }
            case "comment":{
                list = commentDAO.list(param);
                totalCount = commentDAO.listSize(param.getSearch());
                break;
            }
        }

        return PageUtil.builder()
                .list(list)
                .totalCount(totalCount)
                .pageSize(param.getPageSize())
                .pageIndex(param.getL_pageIndex())
                .build();
    }

    @Override
    @Transactional
    public long add(Map<String, Object> map) {
        int price = Integer.parseInt((String) map.get("price"));
        ProductVO product = ProductVO.builder()
                .title((String) map.get("title"))
                .content((String) map.get("editor"))
                .writer((String) map.get("writer"))
                .regist_date(LocalDateTime.now())
                .price(price)
                .build();
        System.out.println("product = " + product);
        if(adminProductDAO.add(product) > 0){
            return adminProductDAO.maxNo();
        }
        return -1;
    }


    public long edit(Map<String, Object> map){
        ProductVO product = adminProductDAO.findByNo((String) map.get("no"));
        product.setTitle((String) map.get("title"));
        product.setContent((String) map.get("editor"));

        return adminProductDAO.edit(product);
    }

    @Override
    public void fileAdd(String no, Map<String, MultipartFile> fileMap) {
        System.out.println("ProductServiceImpl.fileAdd");

        List<BoardFile> boardFileList = new ArrayList<>();
        fileMap.forEach( (strKey, strValue)-> {
            if(strValue.getSize() != 0){
                try {
                    String realName = String.valueOf(System.nanoTime());
                    String thumbnail = "thumbnail_" + System.nanoTime();
                    String contentType = strValue.getContentType().toLowerCase();
                    String fileFormat = contentType.split("/")[1];
                    BoardFile boardFile = BoardFile.builder()
                            .number(Integer.parseInt(no))
                            .org_name(strValue.getOriginalFilename())
                            .real_name(realName)
                            .content_type(contentType+"."+fileFormat)
                            .length((int)strValue.getSize())
                            .register_date(LocalDateTime.now())
                            .build();

                    boardFileList.add(boardFile);

                    File file = new File(fileRepository+"/"+realName+"."+fileFormat);
                    strValue.transferTo(file);

                    if (strKey.equals("thumbnail") && contentType.contains("image")) {
                        boardFile.setThumbnail(thumbnail + "."+fileFormat);
                        File thumbFile = new File(fileRepository + "/" + thumbnail);
                        Thumbnails.of(file)
                                .size(500,500)
                                .outputFormat(fileFormat)
                                .toFile(thumbFile);

                        ProductVO product = adminProductDAO.findByNo(no);
                        product.setThumbnail(fileRepository+"/"+thumbnail + "." + fileFormat);
                        adminProductDAO.edit(product);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            for(BoardFile boardFile : boardFileList){
                boardFileDAO.add(boardFile);
            }
        });
    }

    @Override
    public List<BoardFile> fileList(String number) {
        return boardFileDAO.list(number);
    }

    @Override
    public BoardFile getBoardFile(String f_id) {
        System.out.println("ProductServiceImpl.getBoardFile");
        return boardFileDAO.view(f_id);
    }

    @Override
    public ProductVO getProduct(String no) {
        return adminProductDAO.findByNo(no);
    }
}
