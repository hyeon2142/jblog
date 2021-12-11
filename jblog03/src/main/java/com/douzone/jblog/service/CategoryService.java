package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.vo.CategoryVo;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<CategoryVo> getCategory(String id) {
		return categoryRepository.findById(id);
	}

	public boolean addCategory(CategoryVo categoryVo) {
		if(categoryVo != null && categoryVo.getName().equals("") ) {
			return false;
		}
		return categoryRepository.insert(categoryVo);
	}

	public boolean deleteCategory(String id, Long no) {
		List<CategoryVo> list = getCategory(id);
		//리스트가 하나 있다면
		if(list.size() == 1) {
			return false;
		}
		return categoryRepository.delete(no);
	}

	public boolean addInitialDataCategory(String id) {
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setBlogId(id);
		categoryVo.setName("미분류");
		return addCategory(categoryVo);
	}


}
