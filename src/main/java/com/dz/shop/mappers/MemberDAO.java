package com.dz.shop.mappers;

import java.util.List;

import com.dz.shop.Page.BoardParam;
import com.dz.shop.entity.MemberVO;
import org.springframework.stereotype.Component;

@Component
public interface MemberDAO {
	List<MemberVO> memberList(BoardParam boardParam);
	long listSize(String search);
	MemberVO findByMember(MemberVO memberVO);
}
