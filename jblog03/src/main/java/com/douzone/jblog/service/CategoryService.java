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
	
	public List<CategoryVo> getCategory(String blogId){
		return categoryRepository.findAll(blogId);
	}
	
	public boolean addCategory(CategoryVo vo) {
		return categoryRepository.insert(vo);
	}
	
	public boolean deletecategory(Long no) {
		CategoryVo vo = new CategoryVo();
		vo.setNo(no);
		
		return categoryRepository.delete(vo);
	}
}
