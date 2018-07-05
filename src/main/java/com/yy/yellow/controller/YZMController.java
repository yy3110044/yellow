package com.yy.yellow.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 验证码controller
 * @author yy
 *
 */
@Controller
public class YZMController {
	
	//验证码字体、设置字休，大小
	private Font font = new Font("Times New Roman", Font.PLAIN, 18);
	
	//验证码图片的宽、高
	private int width = 60;
	private int height = 20;
	
	private String codeList = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789";
	
	@RequestMapping("/yzm.jpg")
	public void yzm(HttpServletResponse resp, HttpSession session) throws IOException {
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/jpeg");
		
		Random random = new Random();

		//在内存中创建图像
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		//获取图像的画笔
		Graphics g = image.getGraphics();
		g.setColor(this.getRandomColor(200, 255, random));
		g.fillRect(0, 0, width, height);
		//设定字体
		g.setFont(font);
		
		//随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(this.getRandomColor(160, 200, random));
		for(int i=0; i<155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x+xl, y+yl);
		}
		
		//产生4位数验证码
		String yzmCode = "";
		String str = null;
		int a = 0;
		for(int i=0; i<4; i++) {
			a = random.nextInt(codeList.length() - 1);
			str = codeList.substring(a, a + 1);
			yzmCode += str;
			
			// 将验证码画到到图象中
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(str, 14 * i + 6, 16);
		}
		g.dispose();

		//将认证码存入session
		session.setAttribute("yzmCode", yzmCode);
		ImageIO.write(image, "JPEG", resp.getOutputStream());
		resp.flushBuffer();
	}
	
	/**
	 * 得到指定范围的随机颜色
	 * @return 生成的随机颜色
	 */
	private Color getRandomColor(int fc, int bc, Random random) {
		int difference = bc - fc;
		int r = fc + random.nextInt(difference);
		int g = fc + random.nextInt(difference);
		int b = fc + random.nextInt(difference);
		return new Color(r, g, b);
	}
}