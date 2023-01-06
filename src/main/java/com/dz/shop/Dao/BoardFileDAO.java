package com.dz.shop.Dao;

import com.dz.shop.entity.BoardFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardFileDAO {

    long add(BoardFile bf);
    List<BoardFile> list(String number);
    BoardFile view(String f_id);
}
