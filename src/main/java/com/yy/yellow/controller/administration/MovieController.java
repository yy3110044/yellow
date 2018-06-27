package com.yy.yellow.controller.administration;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.yy.yellow.po.Movie;
import com.yy.yellow.service.MovieService;
import com.yy.yellow.util.ResponseObject;
import com.yy.yellow.util.Util;

@RestController
@RequestMapping(value="/administration", method=RequestMethod.POST)
public class MovieController {
	@Autowired
	private MovieService ms;
	
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
}