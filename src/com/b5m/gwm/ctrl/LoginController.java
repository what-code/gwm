package com.b5m.gwm.ctrl;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.b5m.gwm.model.User;
import com.b5m.gwm.params.ParamsDTO;
import com.b5m.gwm.service.ISystemBulletinService;
import com.b5m.gwm.service.IUserService;
import com.b5m.gwm.utils.CookieUtils;
import com.b5m.gwm.utils.GwmUtils;
import com.b5m.gwm.utils.XMemCachedUtil;
import com.b5m.tfs.util.TfsImageManager;
import com.b5m.tfs.util.TfsImageManager.ImageBean;
import com.b5m.web.core.AbstractBaseController;
import com.b5m.web.core.Constants;

@Controller
public class LoginController extends AbstractBaseController {

	public static Logger logger = Logger.getLogger(LoginController.class);

	@Resource
	private IUserService service;

	@Resource
	private ISystemBulletinService SBservice;

	/**
	 * 首页登陆
	 * 
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping("/index.do")
	public String indexAction(ParamsDTO dto, Model model) {
		Cookie[] cks = getRequest().getCookies();
		if (cks != null) {
			String remember = CookieUtils.getCooKieValue(Constants.USER_IS_REMEMBER_COOKIE, cks);
			boolean isRemember = false;
			// 记住密码。从cookie里获取用户名密码
			if (("true").equals(remember)) {
				String mail = CookieUtils.getCooKieValue(Constants.USER_MAIL_COOKIE, cks);
				String pwd = CookieUtils.getCooKieValue(Constants.USER_PWD_COOKIE, cks);
				String pwdLength = CookieUtils.getCooKieValue(Constants.USER_PWD_LENGTH_COOKIE, cks);
				model.addAttribute("email", mail);
				String pwdlen = "";
				for (int i = 0; i < Integer.valueOf(pwdLength); i++) {
					pwdlen += i;
				}
				model.addAttribute("pwd", pwdlen);
				model.addAttribute("pwdRemember", pwd);
				isRemember = true;
			}
			model.addAttribute("remember", isRemember);
			logger.info("登陆首页-------------------isRemember:" + isRemember);
		}
		return "/index";
	}

	/**
	 * 登陆
	 * 
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping("/login.do")
	public String loginAction(ParamsDTO dto, Model model) {
		logger.info("登陆-------------------email:" + dto.getEmail() + "------pwd:" + dto.getPwd() + "------是否记住密码：" + dto.getIsRemember()
				+ "-----pwdRemember:" + dto.getPwdRemember());
		String pwd = "";
		if (("false").equals(dto.getIsRemember()) || StringUtils.isBlank(dto.getPwdRemember())) {
			pwd = dto.getPwd();
		} else {
			pwd = dto.getPwdRemember();
		}

		// 时间log
		long sMis = System.currentTimeMillis();

		User user = service.login(dto.getEmail(), pwd);

		// 时间log
		long userMis = System.currentTimeMillis();
		logger.info("time log--------查找用户信息-----:" + (userMis - sMis));

		if (user != null) {
			// 邮箱是否激活
			if (("0").equals(user.getMailStatus())) {
				// 获取项目访问主路径
				String rootPath = (String) getRequest().getAttribute("rootPath");
				// 发送激活邮件
				service.sendActivationMail(dto.getEmail(), user.getChnId(), rootPath);

				// 时间log
				long mailSendMis = System.currentTimeMillis();
				logger.info("time log--------邮箱激活-----:" + (mailSendMis - userMis));

				model.addAttribute("email", dto.getEmail());
				model.addAttribute("chnId", user.getChnId());
				return "/login-reg/register-activation";
			}
			// 时间log
			long mailMis = System.currentTimeMillis();
			logger.info("time log--------邮箱激活-----:" + (mailMis - userMis));

			// 获取频道信息
			Map<String, String> map = service.getChannel(user.getChnId());

			// 时间log
			long channelMis = System.currentTimeMillis();
			logger.info("time log--------获取频道信息-----:" + (channelMis - mailMis));

			if (map.size() > 0) {
				try {
					// 设置单个cookie值
					HttpServletResponse response = getResponse();

					CookieUtils.setCooKieValue(Constants.USER_CHANNEL_ID_COOKIE, user.getChnId(), response);
					CookieUtils.setCooKieValue(Constants.USER_CHECK_STATUS_COOKIE, user.getCheckStatus(), response);
					CookieUtils.setCooKieValue(Constants.USER_MAIL_STATUS_COOKIE, user.getMailStatus(), response);
					CookieUtils.setCooKieValue(Constants.USER_CLOSE_STATUS_COOKIE, user.getCloseStatus(), response);
					CookieUtils.setCooKieValue(Constants.USER_GROUP_IDS_COOKIE, user.getGroupIds(), response);
					CookieUtils.setCooKieValue(Constants.USER_CHANNEL_NAME_COOKIE, URLEncoder.encode(map.get("name"), "UTF-8"), response);
					CookieUtils.setCooKieValue(Constants.USER_CHANNEL_AVATAR_COOKIE, map.get("imgUrl"), response);
					CookieUtils.setCooKieValue(Constants.USER_CHANNEL_ISLOGIN, Constants.STATUS_OK, response);

					// 记住密码，信息写入cookie
					if (("true").equals(dto.getIsRemember())) {
						CookieUtils
								.setCooKieValue(Constants.USER_MAIL_COOKIE, URLEncoder.encode(dto.getEmail(), "UTF-8"), response, 7 * 24 * 60 * 60);
						CookieUtils.setCooKieValue(Constants.USER_IS_REMEMBER_COOKIE, dto.getIsRemember(), response, 7 * 24 * 60 * 60);
						CookieUtils.setCooKieValue(Constants.USER_PWD_COOKIE, user.getPassword(), response, 7 * 24 * 60 * 60);
						CookieUtils.setCooKieValue(Constants.USER_PWD_LENGTH_COOKIE, dto.getPwdLength().toString(), response, 7 * 24 * 60 * 60);
					} else {
						CookieUtils.setCooKieValue(Constants.USER_MAIL_COOKIE, URLEncoder.encode(dto.getEmail(), "UTF-8"), response);
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return "/index";
				}
				// 时间log
				long cookieMis = System.currentTimeMillis();
				logger.info("time log--------获取cookie-----:" + (cookieMis - channelMis));

				model.addAttribute("user_session_name", map.get("name"));
				model.addAttribute("user_session_login", "true");
				// 获取全部系统公告
				model.addAttribute("sysList", SBservice.findBulletinByAll());
				model.addAttribute("chnMap", map);
			} else {
				// 频道信息没注册
				model.addAttribute("email", dto.getEmail());
				model.addAttribute("chnId", user.getChnId());
				return "/login-reg/register-info";
			}
		} else {
			model.addAttribute("msg", "您输入的帐号或密码有误！");
			return "/index";
		}
		// 时间log
		long eMis = System.currentTimeMillis();
		logger.info("time log-------登陆总时间------:" + (eMis - sMis));

		return "/channel-info/home";
	}

	/**
	 * 注册首页
	 * 
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping("/register.do")
	public String registerAction(ParamsDTO dto, Model model) {
		logger.info("注册首页-------------------");
		return "/login-reg/register";
	}

	/**
	 * 邮箱验证
	 * 
	 * @return
	 */
	@RequestMapping("/mialValidate.do")
	public @ResponseBody
	boolean mailValidate(ParamsDTO dto) {
		logger.info("邮箱验证-----输入邮箱---------------" + dto.getEmail());
		return service.isMailValidate(dto.getEmail());
	}

	/**
	 * 验证码验证
	 * 
	 * @return true验证码通过
	 */
	@RequestMapping("/codeValidate.do")
	public @ResponseBody
	boolean codeValidate(ParamsDTO dto) {
		// String randcode = (String) this.getRequest().getSession().getAttribute("randcode");
		if (dto.getCode() != null) {
			// 从cookie获取存入MemCache的key
			String sessionId = CookieUtils.getCooKieValue(Constants.MAKER_CODE_SESSION_ID, this.getRequest().getCookies());
			logger.info("cookie里的sessionId--------：" + sessionId);
			// 根据cookie获取的key取出验证码
			String randcode = (String) XMemCachedUtil.getCache(sessionId);

			logger.info("验证码验证-----输入验证码---------------" + dto.getCode() + "-------------生成验证码:" + randcode);
			try{
				if (dto.getCode().equals(randcode)) {
					return true;
				} else {
					return false;
				}
			}catch(Exception e){
				logger.info("-----codeValidate--error-->");
				return false;
			}
		}
		return false;
	}
	/**
	 * 头像上传
	 * 
	 * @param filedata
	 *            文件对象
	 * @param dto
	 * @param response
	 */
	@RequestMapping("/imgUpload.do")
	public void img_upload(@RequestParam(value = "filedata", required = false) MultipartFile filedata, ParamsDTO dto, HttpServletResponse response,
			PrintWriter out) {
		logger.info(dto.getFile());
		try {
			InputStream fis = filedata.getInputStream();
			int filelong = fis.available();
			byte[] long_buf = new byte[filelong];
			long_buf = filedata.getBytes();

			// 图片上次TFS。
			ImageBean ib = TfsImageManager.save(long_buf, "gwmweb");
			// 路径拼接，tfs返回图片名字！
			String path = "http://img.b5m.com/image/" + ib.getImageName();
			logger.info("----upload-avatar:" + path);
			response.setCharacterEncoding("utf-8");

			// 成功，输出script,参数，路径，和是否成功。
			out.println("<script type=\"text/javascript\">window.parent.upload.uploadImgComplete(\"" + path + "\", \"success\");</script>");
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 频道名是否被占用
	 * 
	 * @return true被占用
	 */
	@RequestMapping("/chnNameValidate.do")
	public @ResponseBody
	boolean chnNameValidate(ParamsDTO dto) {
		return service.isChnName(dto.getChnName());
	}

	/**
	 * 注册之平台用户信息
	 * 
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping("/regBase.do")
	public String registerBaseAction(ParamsDTO dto, Model model) {
		String uuid = dto.getChnId();

		// 时间log
		long sMis = System.currentTimeMillis();

		if (dto.getChnId() == null || ("").equals(dto.getChnId())) {
			logger.info("mail:" + dto.getEmail() + "--------------------pwd:" + dto.getPwd());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.format(new Date());

			// 按邮箱查找，是否已经注册过。
			User eUser = service.findUserByEmail(dto.getEmail());
			if (eUser == null) {
				// 生成频道ID
				uuid = GwmUtils.getUuid();

				// 封装user对象
				User user = new User(dto.getEmail(), dto.getPwd(), new Date(), uuid);
				// 添加平台用户
				if (!service.saveUser(user)) {
					model.addAttribute("msg", "注册失败,请重新注册！");
					return "/register";
				}
			}

			// 时间log
			long regMis = System.currentTimeMillis();
			logger.info("time log--------注册，insert-----:" + (regMis - sMis));

		}

		if (StringUtils.isNotBlank(dto.getEmail())) {
			// 获取项目访问主路径
			String rootPath = (String) getRequest().getAttribute("rootPath");
			// 发送激活邮件
			service.sendActivationMail(dto.getEmail(), uuid, rootPath);
		} else {
			model.addAttribute("msg", "发送激活邮件失败请重新发送！");
			return "login-reg/register-activation";
		}

		model.addAttribute("email", dto.getEmail());
		model.addAttribute("chnId", uuid);

		// 时间log
		long eMis = System.currentTimeMillis();
		logger.info("time log------平台用户信息总时间-------:" + (eMis - sMis));

		return "/login-reg/register-activation";
	}

	/**
	 * 注册之邮箱激活
	 * 
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping("/regActivation.do")
	public String registerActivationAction(ParamsDTO dto, Model model) {
		logger.info("用户邮箱:" + dto.getMail() + "--------------------");

		// 用户是否激活
		User user = service.findUserByEmail(dto.getMail());
		if (("0").equals(user.getMailStatus())) {
			// 激活用户
			if (service.activationUser(dto.getMail()) != 0) {
				// 已经激活，但为填写频道信息
				model.addAttribute("chnId", dto.getChnId());
				model.addAttribute("email", dto.getMail());
				return "/login-reg/register-info";
			} else {
				model.addAttribute("msg", "邮箱验证失败！");
				return "login-reg/register-activation";
			}
		} else {
			// 根据邮箱获取是否有频道信息
			Map<String, String> map = service.getChannelByMail(dto.getMail());
			// 已经激活，并且频道信息填写完成
			if (map != null && map.size() > 0) {
				model.addAttribute("email", user.getEmail());
				model.addAttribute("chnId", user.getChnId());
				return "login-reg/register-activation";
			} else {
				// 已经激活，但为填写频道信息
				model.addAttribute("chnId", dto.getChnId());
				model.addAttribute("email", dto.getMail());
				return "/login-reg/register-info";
			}
		}

	}

	/**
	 * 注册之频道信息
	 * 
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping("/regInfo.do")
	public String registerInfoAction(ParamsDTO dto, Model model) {
		logger.info("email:" + dto.getEmail() + "--------------------chnId:" + dto.getChnId() + "-----------avatar:" + dto.getAvatar());

		// (接口保存)保存频道信息
		Map<String, String> map = service.saveChannel(dto);
		if (map != null) {
			if (map.size() > 0) {
				// 根据邮箱获取用户信息
				User user = service.findUserByEmail(dto.getEmail());

				try {
					// 设置单个cookie值
					HttpServletResponse response = getResponse();

					CookieUtils.setCooKieValue(Constants.USER_CHANNEL_ID_COOKIE, user.getChnId(), response);
					CookieUtils.setCooKieValue(Constants.USER_MAIL_COOKIE, URLEncoder.encode(dto.getEmail(), "UTF-8"), response);
					CookieUtils.setCooKieValue(Constants.USER_CHECK_STATUS_COOKIE, user.getCheckStatus(), response);
					CookieUtils.setCooKieValue(Constants.USER_MAIL_STATUS_COOKIE, user.getMailStatus(), response);
					CookieUtils.setCooKieValue(Constants.USER_CLOSE_STATUS_COOKIE, user.getCloseStatus(), response);
					CookieUtils.setCooKieValue(Constants.USER_GROUP_IDS_COOKIE, user.getGroupIds(), response);
					CookieUtils.setCooKieValue(Constants.USER_CHANNEL_NAME_COOKIE, URLEncoder.encode(map.get("name"), "UTF-8"), response);
					CookieUtils.setCooKieValue(Constants.USER_CHANNEL_AVATAR_COOKIE, map.get("imgUrl"), response);
					CookieUtils.setCooKieValue(Constants.USER_CHANNEL_ISLOGIN, Constants.STATUS_OK, response);

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return "/index";
				}

				model.addAttribute("user_session_name", map.get("name"));
				model.addAttribute("user_session_login", "true");
				model.addAttribute("chnMap", map);
				return "/channel-info/home";
			}
			return "/index";
		}
		// 获取项目访问主路径,拼接错误页面的链接
		String httpPost = (String) getRequest().getAttribute("rootPath") + "/error.html";
		return "redirect :" + httpPost;
	}

	/**
	 * 频道信息
	 * 
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping("/chnInfo.do")
	public String channelInfoAction(ParamsDTO dto, Model model) {
		Cookie[] cookie = getRequest().getCookies();
		String isLogin = CookieUtils.getCooKieValue(Constants.USER_CHANNEL_ISLOGIN, cookie);
		// 是否登陆
		if (Constants.STATUS_OK.equals(isLogin)) {
			String chnId = CookieUtils.getCooKieValue(Constants.USER_CHANNEL_ID_COOKIE, cookie);
			Map<String, String> map = service.getChannel(chnId);
			if (map == null) {
				// 获取项目访问主路径,拼接错误页面的链接
				String httpPost = (String) getRequest().getAttribute("rootPath") + "/error.html";
				return "redirect :" + httpPost;
			}

			model.addAttribute("chnMap", map);
			return "/channel-info/channel-info";
		} else {
			return "/index";
		}
	}

	/**
	 * 修改频道信息
	 * 
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping("/chnUpdateInfo.do")
	public String channelUpdateAction(ParamsDTO dto, Model model) {
		Cookie[] cookie = getRequest().getCookies();
		String isLogin = CookieUtils.getCooKieValue(Constants.USER_CHANNEL_ISLOGIN, cookie);
		// 是否登陆
		if (Constants.STATUS_OK.equals(isLogin)) {
			// 修改频道信息
			if (service.updateChannel(dto)) {
				// 获取频道信息
				Map<String, String> map = service.getChannel(dto.getChnId());
				if (map == null) {
					// 获取项目访问主路径,拼接错误页面的链接
					String httpPost = (String) getRequest().getAttribute("rootPath") + "/error.html";
					return "redirect :" + httpPost;
				}

				model.addAttribute("chnMap", map);
			}

			logger.info("email:" + dto.getEmail() + "--------------------chnId:" + dto.getChnId() + "-----------content:" + dto.getContent());
			return "/channel-info/channel-info";
		} else {
			return "/index";
		}
	}

	/**
	 * 退出登陆
	 * 
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping("/exitLogin.do")
	public String exitLoginAction(ParamsDTO dto, Model model) {

		CookieUtils.setCooKieValue(Constants.USER_CHANNEL_ISLOGIN, Constants.STATUS_OFF, getResponse());

		Cookie[] cks = getRequest().getCookies();
		if (cks != null) {
			String remember = CookieUtils.getCooKieValue(Constants.USER_IS_REMEMBER_COOKIE, cks);
			boolean isRemember = false;
			// 记住密码。从cookie里获取用户名密码
			if (("true").equals(remember)) {
				String mail = CookieUtils.getCooKieValue(Constants.USER_MAIL_COOKIE, cks);
				String pwd = CookieUtils.getCooKieValue(Constants.USER_PWD_COOKIE, cks);
				String pwdLength = CookieUtils.getCooKieValue(Constants.USER_PWD_LENGTH_COOKIE, cks);
				model.addAttribute("email", mail);
				String pwdlen = "";
				for (int i = 0; i < Integer.valueOf(pwdLength); i++) {
					pwdlen += i;
				}
				model.addAttribute("pwd", pwdlen);
				model.addAttribute("pwdRemember", pwd);
				isRemember = true;
			}
			model.addAttribute("remember", isRemember);
			logger.info("登陆首页-------------------isRemember:" + isRemember);
		}

		return "/index";
	}

	/**
	 * 返回主页
	 * 
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping("/home.do")
	public String homeAction(ParamsDTO dto, Model model) {
		Cookie[] cookie = getRequest().getCookies();
		String isLogin = CookieUtils.getCooKieValue(Constants.USER_CHANNEL_ISLOGIN, cookie);
		// 是否登陆
		if (Constants.STATUS_OK.equals(isLogin)) {
			// cookie里获取频道ID
			String chnId = CookieUtils.getCooKieValue(Constants.USER_CHANNEL_ID_COOKIE, cookie);
			String chnCheck = CookieUtils.getCooKieValue(Constants.USER_CHECK_STATUS_COOKIE, cookie);
			// 获取频道信息
			Map<String, String> map = service.getChannel(chnId);

			// 获取全部系统公告
			model.addAttribute("sysList", SBservice.findBulletinByAll());
			model.addAttribute("chnMap", map);
			model.addAttribute("chnCheck", chnCheck);
			return "/channel-info/home";
		} else {
			return "/index";
		}
	}

	/**
	 * 用户协议
	 * 
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping("/pact.do")
	public String pactAction(ParamsDTO dto, Model model) {

		logger.info("--------------用户协议-------------");

		return "/login-reg/pact";
	}
}