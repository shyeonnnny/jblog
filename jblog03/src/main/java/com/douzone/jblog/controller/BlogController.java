package com.douzone.jblog.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.exception.FileUploadException;
import com.douzone.jblog.security.AuthUser;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.FileUploadService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/blog")
public class BlogController {
	
	@Autowired
	private ServletContext servletContext;

	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private FileUploadService fileUploadService;

	@RequestMapping("")
	public String main(@AuthUser UserVo vo, Model model) {
		BlogVo blog = blogService.getBlog(vo);
		List<CategoryVo> list = categoryService.getCategory(vo.getId());
		model.addAttribute("blog", blog);
		model.addAttribute("list", list);
		return "blog/blog-main";
	}

	@RequestMapping("blog-admin-basic")
	public String admin(@AuthUser UserVo vo, Model model) {
		BlogVo blog = blogService.getBlog(vo);
		model.addAttribute("blog", blog);
		return "blog/blog-admin-basic";
	}
	
	@RequestMapping("main/update")
	public String admin(BlogVo blog, @RequestParam("file") MultipartFile file) {
		try {
			String logo = fileUploadService.restoreImage(file);
			blog.setLogo(logo);
		} catch(FileUploadException ex) {
			System.out.println("error: " + ex);		
		}
		blogService.update(blog);
		servletContext.setAttribute("blog", blog);
		
		return "redirect:/blog";
	}
	
	// -------------------------------------------------------------------------
	
	@RequestMapping("blog-admin-category")
	public String categorymain(@AuthUser UserVo vo, Model model) {
		System.out.println(vo.getId());
		List<CategoryVo> list = categoryService.getCategory(vo.getId());
		model.addAttribute("list", list);
		return "blog/blog-admin-category";
	}
	
	@RequestMapping("categoryadd")
	public String cateadd(CategoryVo vo, @AuthUser UserVo authUser) {
		System.out.println(vo);
		vo.setBlogId(authUser.getId());
		categoryService.addCategory(vo);
		return "redirect:/blog/blog-admin-category";
	}
	
	@RequestMapping(value="delete/{no}")
	public String delete(@PathVariable("no") Long no) {
		categoryService.deletecategory(no);
		return "redirect:/blog/blog-admin-category";
	}

}

