package com.yy.yellow.controller.administration;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.yy.yellow.po.Movie;
import com.yy.yellow.service.MovieService;
import com.yy.yellow.util.MyMap;
import com.yy.yellow.util.Page;
import com.yy.yellow.util.QueryCondition;
import com.yy.yellow.util.ResponseObject;
import com.yy.yellow.util.Util;

@RestController
@RequestMapping(value="/administration", method=RequestMethod.POST)
public class MovieAdminController {
	@Autowired
	private MovieService ms;
	
	@Value("${web.config.staticSource.downloadUrl}")
	private String downloadUrl;
	
	/**
	 * 添加一个影片
	 * @param movie
	 */
	@RequestMapping("/addMovie")
	public ResponseObject addMovie(Movie movie) {
		if(Util.empty(movie.getTitle())) {
			return new ResponseObject(101, "标题不能为空");
		}
		
		if(Util.empty(movie.getExternalLink()) && Util.empty(movie.getInternalLink())) {
			return new ResponseObject(102, "外部链接和内部链接，至少要填一个");
		}
		
		movie.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
		ms.add(movie);
		return new ResponseObject(100, "添加成功");
	}
	
	/**
	 * 根据id返回一部影片的信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/getMovie")
	public ResponseObject getMovie(@RequestParam String id) {
		return new ResponseObject(100, "返回成功", ms.findById(id));
	}
	
	/**
	 * 修改影片
	 * @param movie
	 * @return
	 */
	@RequestMapping("/updateMovie")
	public ResponseObject updateMovie(Movie movie) {
		if(Util.empty(movie.getTitle())) {
			return new ResponseObject(101, "标题不能为空");
		}
		
		if(Util.empty(movie.getExternalLink()) && Util.empty(movie.getInternalLink())) {
			return new ResponseObject(102, "外部链接和内部链接，至少要填一个");
		}
		
		if(Util.empty(movie.getId())) {
			return new ResponseObject(103, "id不能为空");
		}
		ms.update(movie);
		return new ResponseObject(100, "修改成功");
	}
	
	/**
	 * 删除影片
	 * @param id
	 * @param deleteFile 是否同时删除本地文件
	 * @return
	 */
	@RequestMapping("/deleteMovie")
	public ResponseObject deleteMovie(@RequestParam String id, @RequestParam(defaultValue="true") boolean deleteFile) {
		ms.delete(id);
		return new ResponseObject(100, "删除成功");
	}

	/**
	 * 列出影片
	 * @return
	 */
	@RequestMapping("/listMovie")
	public ResponseObject listMovie(@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime,
	                                 @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date endTime,
	                                 @RequestParam(defaultValue="20") int pageSize,
	                                 @RequestParam(defaultValue="1") int pageNo,
	                                 @RequestParam(defaultValue="5") int showCount,
	                                 @RequestParam(defaultValue="createTime") String sortField,
	                                 @RequestParam(defaultValue="DESC") QueryCondition.SortType sortType) {
		QueryCondition qc = new QueryCondition();
		if(startTime != null) {
			qc.addCondition("createTime", ">=", startTime);
		}
		if(endTime != null) {
			qc.addCondition("createTime", "<=", endTime);
		}
		qc.setPage(new Page(pageSize, pageNo, showCount));
		qc.addSort(sortField, sortType);
		List<Movie> movieList = ms.query(qc);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", movieList);
		result.put("page", qc.getPage().setRowCount(ms.getCount(qc)));
		return new ResponseObject(100, "返回成功", result);
	}
	
	/**
	 * 下载影片
	 * @return
	 */
	@RequestMapping("/downloadMovie")
	public ResponseObject downloadMovie(@RequestParam String id, HttpServletRequest req) {
		Movie movie = ms.findById(id);
		if(movie != null && !Util.empty(movie.getExternalLink())) {
			String basePath = (String)req.getAttribute("basePath");
			String result = Util.requestPost(downloadUrl, new MyMap().set("sourceUrl", movie.getExternalLink()).set("notifyUrl", basePath + "downloadCallback"));
			return new ResponseObject(100, "提交成功", result);
		} else {
			return new ResponseObject(101, "资源为空");
		}
	}
}