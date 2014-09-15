package com.b5m.gwm.ctrl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.b5m.gwm.params.ParamsDTO;
import com.b5m.web.core.AbstractBaseController;

/**
 * Title:ErrorController.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-12-5
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Jia Liu
 * 
 * @version 1.0
 */
@Controller
public class ErrorController extends AbstractBaseController {

	public static Logger logger = Logger.getLogger(ErrorController.class);

	/**
	 * 错误页面跳转
	 * 
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping("/error.do")
	public String errorAction(ParamsDTO dto, Model model) {
		logger.info("------------error 错误页面-------");
		return "/error/error";
	}
}
