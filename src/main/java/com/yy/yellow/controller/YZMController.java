package com.yy.yellow.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

/**
 * 验证码controller
 * @author yy
 *
 */
@Controller
public class YZMController {
	@Resource(name="defaultKaptcha")
	private Producer producer;
	
	@RequestMapping("/yzm.png")
	public void yzm(HttpServletResponse resp, HttpSession session) throws IOException {
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/png");
		
		String capText = producer.createText();
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
		BufferedImage bi = producer.createImage(capText);
		ServletOutputStream out = resp.getOutputStream();
		ImageIO.write(bi, "png", out);
		out.flush();
	}
}