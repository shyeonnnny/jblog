package com.douzone.jblog.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.exception.FileUploadException;
import com.douzone.jblog.security.AuthUser;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.FileUploadService;
import com.douzone.jblog.service.PostService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/blog/{id}")
public class BlogController {
	
	@Autowired
	private ServletContext servletContext;

	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PostService postService;

	@Autowired
	private FileUploadService fileUploadService;

	
	@RequestMapping({"","/{no}","/{no}/{pno}"})
	public String main(@PathVariable(value="no", required=false) Long no,
					   @PathVariable(value="pno", required=false) Long pno,
					   @PathVariable("id") String id, Model model) {
		BlogVo blog = blogService.getBlog(id);

		List<CategoryVo> list = categoryService.getCategory(id);
		
//		if(no == null) {
//			for(CategoryVo vo : list) {
//				if(vo.isDefault) no = vo.getNo();
//			}			
//		}
		
		if(no == null) no = list.get(0).getNo();
		
		List<PostVo> post = postService.getPost(no);
		
		if(pno == null && post.size() != 0) pno = post.get(0).getNo();
		
		PostVo pvo = postService.getContents(pno);
		
		model.addAttribute("pvo", pvo);
		model.addAttribute("post", post);
		model.addAttribute("blog", blog);
		model.addAttribute("list", list);
		return "blog/blog-main";
	}

	@RequestMapping("blog-admin-basic")
	public String admin(@AuthUser UserVo vo, Model model) {
		BlogVo blog = blogService.getBlog(vo.getId());
		model.addAttribute("blog", blog);
		return "blog/blog-admin-basic";
	}
	
	@RequestMapping("main/update")
	public String admin(@PathVariable("id") String id,BlogVo blog, @RequestParam("file") MultipartFile file) {
		try {
			String logo = fileUploadService.restoreImage(file);
			blog.setLogo(logo);
		} catch(FileUploadException ex) {
			System.out.println("error: " + ex);		
		}
		blogService.update(blog);
		servletContext.setAttribute("blog", blog);
		
		return "redirect:/blog/"+id;
	}
	

	
	
	
	// -------------------------------------------------------------------------
	
	@RequestMapping("blog-admin-category")
	public String categorymain(@AuthUser UserVo vo, Model model) {
		BlogVo blog = blogService.getBlog(vo.getId());
		model.addAttribute("blog", blog);
		List<CategoryVo> list = categoryService.getCategory(vo.getId());
		model.addAttribute("list", list);
		return "blog/blog-admin-category";
	}
	
	@RequestMapping("categoryadd")
	public String cateadd(@PathVariable("id") String id,CategoryVo vo, @AuthUser UserVo authUser) {
		vo.setBlogId(authUser.getId());
		categoryService.addCategory(vo);
		return "redirect:/blog/"+id+"/blog-admin-category";
	}
	
	@RequestMapping(value="delete/{no}")
	public String delete(@PathVariable("no") Long no, @PathVariable("id") String id) {
		categoryService.deletecategory(no);
		return "redirect:/blog/"+id+"/blog-admin-category";
	}
	
	// -------------------------------------------------------------------------
	
	@RequestMapping("blog-admin-write")
	public String adminwrite(@AuthUser UserVo vo, Model model) {
		BlogVo blog = blogService.getBlog(vo.getId());
		model.addAttribute("blog", blog);
		List<CategoryVo> list = categoryService.getCategory(vo.getId());
		model.addAttribute("categorylist", list);
		return "blog/blog-admin-write";
	}
	
	@RequestMapping("write")
	public String write(@AuthUser UserVo authUser,
			@PathVariable("id") String id,
			@RequestParam(value = "title",required = true, defaultValue = "") String title,
			@RequestParam(value = "contents",required = true, defaultValue = "") String content,
			@RequestParam(value = "category", required = true, defaultValue = "") String no ) {
		PostVo vo = new PostVo();
		vo.setCategoryNo(Long.parseLong(no));
		vo.setTitle(title);
		vo.setContents(content);
		
		postService.write(vo, authUser.getId());
		return "redirect:/blog/"+id;
		
	}

}

