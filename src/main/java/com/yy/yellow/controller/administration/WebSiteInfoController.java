package com.yy.yellow.controller.administration;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.yy.yellow.util.ResponseObject;

@RestController
@RequestMapping(value="/administration", method=RequestMethod.POST)
public class WebSiteInfoController {

	/**
	 * 返回网站相关信息
	 * @return
	 */
	@RequestMapping("/getWebsiteInfo")
	public ResponseObject getWebsiteInfo() {
		ResponseObject ro = new ResponseObject();
		ro.setCode(100);
		ro.setMsg("返回成功");
		ro.setResult(System.getProperties());
		return ro;
	}
}