package com.yy.yellow.util;

import java.util.concurrent.Callable;

import com.yy.yellow.po.Movie;
import com.yy.yellow.service.MovieService;

/**
 * 下载线程
 * @author yy
 *
 */
public class DownloadCallable implements Callable<DownloadResult> {
	private String movieId;
	private MovieService ms;

	public DownloadCallable(String movieId, MovieService ms) {
		this.movieId = movieId;
		this.ms = ms;
	}

	@Override
	public DownloadResult call() throws Exception {
		Movie movie = ms.findById(id);
		if(movie != null) {
			
		}
	}
}