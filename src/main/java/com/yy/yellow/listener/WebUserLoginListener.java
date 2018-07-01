package com.yy.yellow.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import com.yy.yellow.util.LoginManager;
import com.yy.yellow.util.Util;

/**
 * 用户web登陆监听器
 * @author yy
 *
 */
@WebListener
public class WebUserLoginListener implements HttpSessionListener {
	private LoginManager loginManager;

	@Override
	public void sessionCreated(HttpSessionEvent e) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent e) {
		if(loginManager == null) {
			loginManager = Util.getBean(LoginManager.class, e.getSession().getServletContext());
		}
		loginManager.webLogout(e.getSession());
	}
}