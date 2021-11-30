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
		List<CategoryVo> catelist = categoryRepository.findAll(blogId);
		List<CategoryVo> count = categoryRepository.findCount(blogId);
		
		for(int i=0; i<catelist.size(); i++) {
			for(int j=0; j<count.size(); j++) {
				if(catelist.get(i).getNo() == count.get(j).getNo()) {
					catelist.get(i).setPostcount(count.get(j).getPostcount());
					break;
				}
			}
		}
		return catelist;
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
