package com.dz.shop.Dao;

import java.util.List;
import java.util.Map;
import com.dz.shop.Page.BoardParam;
import com.dz.shop.entity.MemberVO;
import org.springframework.stereotype.Component;

@Component
public interface MemberDAO {
	List<MemberVO> memberList(BoardParam boardParam);
	long listSize(String search);
	MemberVO findByMember(MemberVO memberVO);

	MemberVO findById(String userId);
	MemberVO findByEmailAndName(Map<String, Object> map);
	MemberVO findByIdAndEmail(Map<String, Object> map);
	long insert(MemberVO member);
	long edit(MemberVO member);
}
