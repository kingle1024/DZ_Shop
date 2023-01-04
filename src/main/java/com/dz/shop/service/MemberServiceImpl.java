package com.dz.shop.service;

import com.dz.shop.Page.BoardParam;
import com.dz.shop.Page.PageUtil;
import com.dz.shop.entity.MemberVO;
import com.dz.shop.Dao.MemberDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberDAO memberDAO;

    public PageUtil pageUtil(String search, String pageIndex, String type){
        BoardParam param = BoardParam.builder()
                .pageIndex(pageIndex)
                .search(search)
                .type(type)
                .build();
        param.init();

        long totalCount;
        List<?> list;
        list = memberDAO.memberList(param);
        totalCount = memberDAO.listSize(search);

        return PageUtil.builder()
                .list(list)
                .totalCount(totalCount)
                .pageSize(param.getPageSize())
                .pageIndex(param.getL_pageIndex())
                .build();
    }

    @Override
    public MemberVO login(MemberVO memberVO) {
        MemberVO member = memberDAO.findByMember(memberVO);
        if(member != null){
            member.setLogindatetime(LocalDateTime.now());
            memberDAO.edit(member);
        }
        return member;
    }

    @Override
    public MemberVO dupUidaCheck(String userId) {
        return memberDAO.findById(userId);
    }

    @Override
    public long insert(MemberVO member) {
        return memberDAO.insert(member);
    }

    @Override
    public String sendMail(Map<String, Object> param) {

        return null;
    }


}
