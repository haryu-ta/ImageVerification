package com.example.ImageFileInspection.service;

import com.example.ImageFileInspection.dao.ImageFileDao;
import com.example.ImageFileInspection.dto.ImageFileDto;
import com.example.ImageFileInspection.entity.ImageFileEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ImageFileService {
	
	@Autowired
	ImageFileDao dao;
	
	public ImageFileDto readDb(int id) {
		String base64image = dao.readDb(id);
		ImageFileDto dto = new ImageFileDto();
		//BeanUtils.copyProperties(entity,dto);
		dto.setImage(base64image);
		return dto;

	}

	public int insertDb(ImageFileDto dto){
		ImageFileEntity entity = new ImageFileEntity();
		BeanUtils.copyProperties(dto,entity);
		dao.regist(entity);
		return entity.getId();
	}

	public List<ImageFileDto> read(){
		List<ImageFileDto> list = new ArrayList<ImageFileDto>();

		for(ImageFileEntity entity : dao.selectAll()){
			ImageFileDto dto = new ImageFileDto();
			BeanUtils.copyProperties(entity,dto);
			list.add(dto);
		}

		return list;
	}

}
