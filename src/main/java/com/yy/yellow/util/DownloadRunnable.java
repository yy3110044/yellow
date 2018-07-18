package com.yy.yellow.util;

import com.yy.yellow.po.Movie;
import com.yy.yellow.po.Movie.DownloadStatus;
import com.yy.yellow.service.MovieService;

/**
 * 下载线程类
 * @author yy
 *
 */
public class DownloadRunnable implements Runnable {
	private String movieId;
	private MovieService ms;
	private StaticSourceAdmin ssa;

	public DownloadRunnable(String movieId, MovieService ms, StaticSourceAdmin ssa) {
		this.movieId = movieId;
		this.ms = ms;
		this.ssa = ssa;
	}
	
	@Override
	public void run() {
		Movie movie = ms.findById(movieId);
		if(movie != null) {//将状态设为下载中
			movie.setDownloadStatus(DownloadStatus.下载中);
			ms.update(movie);
			
			if(!Util.empty(movie.getExternalLink())) { //外部链接不为空
				ResponseObject ro = ssa.saveDownloadFile(movie.getExternalLink());
				if(ro.getCode() == 100) {
					MyMap map = (MyMap)ro.getResult();
					String internalLink = (String)map.get("serverUrl");
					String filePath = (String)map.get("filePath");

					movie.setDownloadStatus(DownloadStatus.已完成);
					movie.setInternalLink(internalLink);
					movie.setFilePath(filePath);
					ms.update(movie);
				} else {
					movie.setDownloadStatus(DownloadStatus.已取消);
					ms.update(movie);
				}
			} else {
				movie.setDownloadStatus(DownloadStatus.已取消);
				ms.update(movie);
			}
		}
	}
}