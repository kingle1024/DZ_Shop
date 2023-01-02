package com.dz.shop.mappers;

import com.dz.shop.Page.BoardParam;
import com.dz.shop.entity.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Component
public interface AdminDAO {
	List<MemberVO> memberList(BoardParam boardParam);
	long listSize(String search);
	int userStatus(Map<String, Object> map);
}
