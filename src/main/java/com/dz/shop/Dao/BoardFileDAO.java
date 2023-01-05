package com.dz.shop.Dao;

import com.dz.shop.entity.BoardFile;

import java.util.List;

public interface BoardFileDAO {

    long add(BoardFile bf);
    List<BoardFile> list(String number);
}
