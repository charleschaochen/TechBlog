package org.charlestech.actions.blog;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.charlestech.dao.ArticleCategoryDao;
import org.charlestech.po.ArticleCategory;

/**
 * Article Category Operation CGI
 * 
 * @author Charles Chen
 * 
 */
public class ArticleCategoryAction extends ActionSupport {

	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = -7071524859160732935L;
	// private static Logger logger = Logger
	// .getLogger(ArticleCategoryAction.class);

	private String name;
	private String settop;
	private ArticleCategoryDao acd;
	private String id;

	/**
	 * Get all article categories
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getAllCategories() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = out = response.getWriter();
		// Get all article categories and parse to JSON string
		List<ArticleCategory> categories = acd.findAll();
		JSONArray categories_json = JSONArray.fromObject(categories);
		out.print(categories_json.toString());
		return null;
	}

	/**
	 * Create new category
	 * 
	 * @return
	 * @throws IOException
	 */
	public String addCategory() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = out = response.getWriter();
		// Add article category to database
		ArticleCategory category = new ArticleCategory();
		category.setCategoryName(name);
		category.setSetTop(Integer.parseInt(settop));
		if (acd.save(category) > 0) {
			// logger.info("添加博文分类： " + name);
			out.print("{retcode:0,mess:'New category created'}");
			return null;
		}
		out.print("{retcode:-1,mess:'Failed to create'}");
		return null;
	}

	/**
	 * Update category
	 * 
	 * @return
	 * @throws IOException
	 */
	public String updateCategory() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = out = response.getWriter();
		// Update article category
		ArticleCategory category = acd.findById(Integer.parseInt(id));
		if (category == null) {
			out
					.print("{retcode:-1,mess:'Cannot find this category, or it is unavailable'}");
			return null;
		}
		category.setCategoryName(name);
		category.setSetTop(Integer.parseInt(settop));
		acd.update(category);
		// logger.info("更新博文分类： " + name);
		out.print("{retcode:0,mess:'Update category successfully'}");
		return null;
	}

	/**
	 * Delete category logically
	 * 
	 * @return
	 * @throws IOException
	 */
	public String deleteCategory() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = out = response.getWriter();
		ArticleCategory category = acd.findById(Integer.parseInt(id));
		if (category == null) {
			out
					.print("{retcode:-1,mess:'Cannot find this category, or it is unavailable'}");
			return null;
		}
		category.setCategoryState(0);
		acd.update(category);
		// logger.info("逻辑删除博文分类： " + category.getCategoryName());
		out.print("{retcode:0,mess:'Delete category successfully'}");
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArticleCategoryDao getAcd() {
		return acd;
	}

	public void setAcd(ArticleCategoryDao acd) {
		this.acd = acd;
	}

	public void setSettop(String settop) {
		this.settop = settop;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
