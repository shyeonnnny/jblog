package com.douzone.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public List<CategoryVo> findAll(String blogId) {
		return sqlSession.selectList("category.findAll", blogId);
	}
	
	public boolean insert(CategoryVo vo) {
		int count = sqlSession.insert("category.insert", vo);
		return count == 1;
	}
	
	public boolean delete(CategoryVo vo) {
		int count = sqlSession.delete("category.delete", vo);
		return count == 1;
	}
	
	public List<CategoryVo> findCount(String blogId){
		return sqlSession.selectList("category.findCount", blogId);
	}
}
