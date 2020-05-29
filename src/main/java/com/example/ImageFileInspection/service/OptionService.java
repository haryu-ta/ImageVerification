package com.example.ImageFileInspection.service;

import com.example.ImageFileInspection.dao.ImageFileDao;
import com.example.ImageFileInspection.entity.ImageFileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeMap;

@Service
public class OptionService {

    @Autowired
    ImageFileDao dao;

    public TreeMap<String,String> getOption(){
        List<ImageFileEntity> entity = dao.selectOption();

        TreeMap<String,String> resultMap = new TreeMap<String,String>();
        for(ImageFileEntity en : entity){
            resultMap.put(en.getFilename(),String.valueOf(en.getId()));
        }
        return resultMap;
    }
}
