package com.yy.yellow.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import com.yy.yellow.util.LoginManager;

/**
 * 用户web登陆监听器
 * @author yy
 *
 */
@WebListener
public class WebUserLoginListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent e) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent e) {
		LoginManager.webLogout(e.getSession());
	}
}