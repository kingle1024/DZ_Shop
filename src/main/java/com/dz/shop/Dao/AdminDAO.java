package com.dz.shop.Dao;

import com.dz.shop.Page.BoardParam;
import com.dz.shop.entity.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface AdminDAO {
	List<MemberVO> memberList(BoardParam boardParam);
	long listSize(String search);
	int userStatus(Map<String, Object> map);
}
