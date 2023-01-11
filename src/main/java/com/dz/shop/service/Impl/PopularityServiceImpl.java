package com.dz.shop.service.Impl;

import com.dz.shop.Dao.AdminProductDAO;
import com.dz.shop.Dao.PopularityDAO;
import com.dz.shop.entity.Popularity;
import com.dz.shop.entity.PopularityEnum;
import com.dz.shop.entity.ProductVO;
import com.dz.shop.service.PopularityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class PopularityServiceImpl implements PopularityService {

    private final PopularityDAO popularityDAO;
    private final AdminProductDAO adminProductDAO;

    @Autowired
    public PopularityServiceImpl(PopularityDAO popularityDAO, AdminProductDAO adminProductDAO) {
        this.popularityDAO = popularityDAO;
        this.adminProductDAO = adminProductDAO;
    }


    @Override
    public Map<String, Object> add(Popularity popularityParam) {
        String bno = popularityParam.getBno();
        String clickedType = popularityParam.getType();
        String user = popularityParam.getUserId();

        Popularity popularity = popularityDAO.findByBnoAndUserIdAndIsDelete(popularityParam);

        ProductVO product = adminProductDAO.findByNo(bno);
        int like_count = product.getLike_count();
        int dislike_count = product.getDislike_count();

        Map<String, Object> resultMap = new HashMap<>();

        // 내가 좋아요, 싫어요 한 내역이 없으면
        if(popularity == null){
            product = setProductPopularity(product, null, clickedType, like_count, dislike_count);
            adminProductDAO.edit(product);

            popularity = new Popularity();
            popularity.setBno(bno);
            popularity.setUserId(user);
            popularity.setType(clickedType);
            popularity.setRegister_date(LocalDateTime.now());

            popularityDAO.insert(popularity);

            resultMap.put("message", "성공");
            resultMap.put("status", "add");
        } else {
            // 동일한 데이터가 존재하면 제거
            if(clickedType.equals(popularity.getType())){
                popularity.setDelete(true);
                popularityDAO.update(popularity);

                if(clickedType.equals(PopularityEnum.like.name())){
                    like_count -= 1 ;
                }else{
                    dislike_count -= 1;
                }
                product.setLike_count(like_count);
                product.setDislike_count(dislike_count);
                adminProductDAO.edit(product);

                resultMap.put("message", "취소되었습니다.");
                resultMap.put("status", "cancel");
            } else { // 좋아요에서 싫어요를 선택한 경우
                popularity.setDelete(true);
                popularityDAO.update(popularity);
                popularity.setType(clickedType);
                popularity.setDelete(false);
                popularity.setRegister_date(LocalDateTime.now());
                popularityDAO.insert(popularity);

                if(clickedType.equals(PopularityEnum.like.name())){
                    like_count += 1;
                    dislike_count -=1;
                }else if(clickedType.equals(PopularityEnum.dislike.name())){
                    like_count -=1;
                    dislike_count += 1;
                }
                product.setLike_count(like_count);
                product.setDislike_count(dislike_count);
                adminProductDAO.edit(product);

                resultMap.put("message", "변경되었습니다.");
                resultMap.put("status", "change");
            }
        }
        return resultMap;
    }

    private ProductVO setProductPopularity(ProductVO product, Popularity popularity, String clickedType, int like_count, int dislike_count) {
        // 내가 클릭한 값과 기존 값과 동일하면 -1을 해야함.
        if(popularity == null){
            if(clickedType.equals(PopularityEnum.like.name())){
                like_count +=1;
            }else{
                dislike_count +=1;
            }
        }else if(clickedType.equals(popularity.getType())){
            // 내가 클릭한 값이 좋아요면
            if(clickedType.equals(PopularityEnum.like.name())){
                like_count -=1;
            }else{
                dislike_count -=1;
            }
        }else{
            if(clickedType.equals(PopularityEnum.like.name())){
                like_count += 1;
                dislike_count -=1;
            }else if(clickedType.equals(PopularityEnum.dislike.name())){
                like_count -=1;
                dislike_count += 1;
            }
        }

        product.setLike_count(like_count);
        product.setDislike_count(dislike_count);
        return product;

    }

    @Override
    public String findByBnoAndUserIdAndIsDelete(String no, String userId) {
        Popularity popularityParam = new Popularity();
        popularityParam.setUserId(userId);
        popularityParam.setBno(no);
        Popularity popularity1 = popularityDAO.findByBnoAndUserIdAndIsDelete(popularityParam);
        System.out.println("popularity1 = " + popularity1);
        if (popularity1 != null){
            return popularity1.getType();
        }else{
            return "no";
        }
    }
}
