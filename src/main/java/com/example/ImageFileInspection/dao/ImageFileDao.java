package com.example.ImageFileInspection.dao;

import com.example.ImageFileInspection.dto.ImageFileDto;
import com.example.ImageFileInspection.entity.ImageFileEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImageFileDao {

    public String readDb(int id);

    public void regist(ImageFileEntity entity);

    public List<ImageFileEntity> selectOption();

    public List<ImageFileEntity> selectAll();
}
