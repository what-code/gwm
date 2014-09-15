package com.b5m.gwm.ctrl;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.b5m.gwm.model.*;
import com.b5m.gwm.service.IMessageFileService;
import com.b5m.gwm.utils.GwmUtils;
import com.b5m.gwm.utils.XMemCachedUtil;
import com.b5m.tfs.util.TfsImageManager.ImageBean;
import com.b5m.web.core.AbstractBaseController;
import com.b5m.web.core.B5MPageList;

@Controller
public class MessageFileController extends AbstractBaseController {
	private static Log logger = LogFactory.getLog(MessageFileController.class);
	
	@Resource(name="messageFileService")
	private IMessageFileService service;
	
	public @Value("#{configProp[cdnFilePath]}")
	String cdnFilePath;
	
	//上传文件大小限制（2M）
	private static final int MAX_FILE_SIZE = 2 * 1024 * 1024;
	//文件的类型限制
	private static final String FILE_TYPE = "bmp,png,jpeg,jpg,gif";
	//每天可发送的消息数目
	private @Value("#{configProp[messageLimitCount]}") int MES_SEND_LIMIT;
	
	/**
	 * 发送消息页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/send")
	public String sendMessage(HttpServletRequest request,Model model) {
		if("true".equals(isUserLogin(request))){
			setMessageLeft(request,model);
			return "/mes_files/send_mes";
		}
		return isUserLogin(request);
	}
	
	/**
	 * 获取消息的条数
	 * @param request
	 * @param model
	 */
	private void setMessageLeft(HttpServletRequest request,Model model){
		String userEmail = (String)request.getAttribute("user_session_channel_id");
		int count = service.getUserSendMesCount(userEmail);
		int left = (MES_SEND_LIMIT - count) < 0 ? 0 : (MES_SEND_LIMIT - count);
		model.addAttribute("messageLeft",left);
	}
	
	
	/**
	 * 已发送列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/sended")
	public String sendedMessage(HttpServletRequest request,Model model) {
		String email = (String)request.getAttribute("user_session_channel_id");
		String pageNo = GwmUtils.getParamsValueFromPage(request, "page_no");
		if(pageNo == null || "".equals(pageNo)){
			pageNo = "1";
		}
		
		if(email == null){
			return "redirect:/index";
		}
		B5MPageList<MessageLog> list = service.getSendedMsgById(email,pageNo);
		model.addAttribute("sendedList", list);
		model.addAttribute("sendedPageNo", pageNo);
		return "/mes_files/sended_mes";
	}
	
	/**
	 * 单图文页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/file_single")
	public String fileSingle(Model model,HttpServletRequest request) {
		if("true".equals(isUserLogin(request))){
			String msgGroup = GwmUtils.getParamsValueFromPage(request, "target_id");
			String msgSex = GwmUtils.getParamsValueFromPage(request, "target_sex");
			model.addAttribute("msgGroup", msgGroup);
			model.addAttribute("msgSex", msgSex);
			setMessageLeft(request,model);
			return "/mes_files/file_single";
		}
		return isUserLogin(request);
	}

	/**
	 * 多图文页面
	 * @param out
	 * @return
	 */
	@RequestMapping("/file_multi")
	public String fileMulti(HttpServletRequest request,Model model) {
		if("true".equals(isUserLogin(request))){
			String msgGroup = GwmUtils.getParamsValueFromPage(request, "target_id");
			model.addAttribute("msgGroup", msgGroup);
			setMessageLeft(request,model);
			return "/mes_files/file_multi";
		}
		return isUserLogin(request);
	}
	
	/**
	 * 图片消息页面
	 * @param out
	 * @return
	 */
	@RequestMapping("/file_img")
	public String fileImg(PrintWriter out) {
		return "/mes_files/file_img";
	}

	/**
	 * 上传文件
	 * @param modelAndView
	 * @param request
	 * @return
	 */
	@RequestMapping("/upload_img")
	public void uploadImg(@RequestParam(value = "filedata", required = false) MultipartFile filedata,Model modelAndView,HttpServletRequest request,PrintWriter out) {
		logger.info("---upload img---");
		 //MultipartHttpServletRequest fd2 = (MultipartHttpServletRequest) getMultipartRequest();
	    //Iterator<String> ite2 = fd2.getFileNames();
		String javaScriptMethod = "uploadImgComplete";
		String mesFrom = GwmUtils.getParamsValueFromPage(request, "mes_from");
		if("index".equals(mesFrom)){
			javaScriptMethod = "uploadImgDone";
		}
		cdnFilePath = cdnFilePath.endsWith("/") ? cdnFilePath : cdnFilePath + "/";
	    MultipartHttpServletRequest fd1 = null;
	    Iterator<String> ite = null;
		try {
			fd1 = (MultipartHttpServletRequest) request;
			ite = fd1.getFileNames();
		}catch(Exception e){
			logger.info(e);
		}
		String tempString = null;
		while (ite.hasNext()) {
			tempString = ite.next();
			MultipartFile multipartFile = fd1.getFile(tempString);
			String fileName = multipartFile.getOriginalFilename();
			String fileType = fileName.split("\\.")[fileName.split("\\.").length - 1];
			int fileSize = Integer.parseInt(multipartFile.getSize()+"");
			//logger.info("---upload img name---" + fileName + "---" + cdnFilePath + "--fileSize--" + fileSize + "----" + multipartFile.getSize());
			//文件类型判断
			if(FILE_TYPE.indexOf(fileType) == -1){
				out.println("<script type=\"text/javascript\">window.parent.goMsg." + javaScriptMethod + "(\"上传失败：请上传以下格式的图片:bmp,png,jpeg,jpg,gif\", \"fail\");</script>"); 
				out.flush();
				out.close();
				return;
			}
			//文件大小判断
			if(fileSize > MAX_FILE_SIZE){
				out.println("<script type=\"text/javascript\">window.parent.goMsg." + javaScriptMethod + "(\"上传失败：上传文件不能大于2M!\", \"fail\");</script>"); 
				out.flush();
				out.close();
				return;
			}
			// 将图片保存到图片服务器
			String tfsPath = cdnFilePath;
			InputStream is = null;
			OutputStream fos = null;
			try {
				is = multipartFile.getInputStream();
				/*File file = new File(path);
				fos = new FileOutputStream(file);
				byte[] bytes = new byte[1024];*/
				byte[] allFile = new byte[fileSize];
				is.read(allFile);
				ImageBean ib = GwmUtils.uploadFileToTFS(allFile);
				if(ib != null){
					tfsPath = tfsPath + ib.getImageName();
				}else{
					tfsPath = null;
				}
				/*while(is.read(bytes) != -1){
					fos.write(bytes);
				}*/
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				/*try {
					is.close();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}*/
			}
			
			//保存文件信息
			FileInfo fileInfo = GwmUtils.getFileInfoObject(multipartFile,fileType,tfsPath,request);
			service.saveFileInfo(fileInfo);
			modelAndView.addAttribute("imgPath", tfsPath);
			//设置参数
			setMessageParams(request,modelAndView);
			
			out.println("<script type=\"text/javascript\">window.parent.goMsg." + javaScriptMethod + "(\"" + tfsPath + "\", \"success\");</script>"); 
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 保存单图文消息，并发送消息
	 * @param request
	 * @return
	 */
	@RequestMapping("/save_single_mes")
	public String saveSingleMesInfo(HttpServletRequest request,Model model){
		if("true".equals(isUserLogin(request))){
			String tid = GwmUtils.getParamsValueFromPage(request, "target_id");
			String mesType = GwmUtils.getParamsValueFromPage(request, "mes_type");
			String chnId = (String)request.getAttribute("user_session_channel_id");
			SingleImgAndTxt sit = GwmUtils.getSitObject(request);
			MessageLog log = GwmUtils.getMessageLogInfo(sit, tid,request);
			log.setMesType(mesType);
			sit.setMesType(mesType);
			List list = new ArrayList();
			list.add(sit);
			list.add(sit);
			//保存单图文信息
			service.saveMessageInfo(sit);
			//调用接口发布消息
			boolean result = service.sendSingleMessage(list,mesType,chnId);
			//result false:发送失败
			if(!result){
				log.setMesStatus("2");
				model.addAttribute("sendResult", "0");
			}else{
				model.addAttribute("sendResult", "1");
			}
			//保存发布消息log
			service.saveMessageLog(log);
			//更新已发送列表
			cleanSendedCache(chnId);
			//文本和图片的回到发送页面
			if("3".equals(mesType) || "4".equals(mesType)){
				return "/mes_files/send_mes";
			}
			return "/mes_files/file_single";
		}
		
		return isUserLogin(request);
	}
	
	/**
	 * 刷新已发送列表
	 * @param chnId
	 */
	private void cleanSendedCache(String chnId){
		B5MPageList<MessageLog> list = service.getSendedMsgById(chnId,"1");
		String key = "";
		for(int i = 0;i < list.getTotalPages();i++){
			key = "SENDED_LIST_" + chnId + "_" + (i+1);
			XMemCachedUtil.cleanCache(key);
		}
	}
	
	/**
	 * 保存多图文消息，并发送消息
	 * @param request
	 * @return
	 */
	@RequestMapping("/save_multi_mes")
	public String saveMultiMesInfo(HttpServletRequest request,Model model){
		if("true".equals(isUserLogin(request))){
			String tid = GwmUtils.getParamsValueFromPage(request, "target_id");
			String mesType = GwmUtils.getParamsValueFromPage(request, "mes_type");
			String multiFormData = GwmUtils.getParamsValueFromPage(request, "multi_form_data");
			String chnId = (String)request.getAttribute("user_session_channel_id");
			
			List<SingleImgAndTxt> list = GwmUtils.getMultiObjectFromJson(multiFormData,chnId);
			SingleImgAndTxt sit = list.get(0);
			MessageLog log = GwmUtils.getMessageLogInfo(sit, tid,request);
			log.setMesType(mesType);
			
			//保存多图文信息
			service.saveMultiMessageInfo(list);
			//调用接口发布消息
			boolean result = service.sendSingleMessage(list,mesType,chnId);
			//result false:发送失败
			if(!result){
				log.setMesStatus("2");
				model.addAttribute("sendResult", "0");
			}else{
				model.addAttribute("sendResult", "1");
			}
			//保存发布消息log
			service.saveMessageLog(log);
			//更新已发送列表
			cleanSendedCache(chnId);
			return "/mes_files/file_multi";
		}
		return isUserLogin(request);
	}
	
	/**
	 * 图文消息，模板的详情页面
	 * @param request
	 * @param writer
	 */
	@RequestMapping("/message_detail")
	public void messageDetail(HttpServletRequest request,PrintWriter writer){
		String mid = GwmUtils.getParamsValueFromPage(request, "mid");
		SingleImgAndTxt sa = service.messageDetail(mid);
		String html = service.parseBodyToHtml(sa,sa.getMesType());
		writer.write(html);
		writer.flush();
		writer.close();
	}
	
	@RequestMapping("/preview_multi")
	public String previewMulti(HttpServletRequest request,Model model){
		String mid = GwmUtils.getParamsValueFromPage(request, "mid");
		List<SingleImgAndTxt> list = service.previewMulti(mid);
		model.addAttribute("list",list);
		return "/mes_files/preview_multi";
	}
	
	private String isUserLogin(HttpServletRequest request){
		String isLogin = (String)request.getAttribute("user_session_login");
		String checked = (String)request.getAttribute("user_session_check_status");
		
		if("false".equals(isLogin) || isLogin == null){
			return "redirect:/index";
		}
		
		if("true".equals(isLogin) && "0".equals(checked)){
			return "redirect:/home.html";
		}
		
		return "true";
	}
	
	/**
	 * 设置model的参数
	 * @param request
	 * @param model
	 */
	private void setMessageParams(HttpServletRequest request,Model model){
		String msgGroup = GwmUtils.getParamsValueFromPage(request, "target_id");
		String msgSex = GwmUtils.getParamsValueFromPage(request, "target_sex");
		String mesTitle = GwmUtils.getParamsValueFromPage(request, "mes_title");
		String mesAuthor = GwmUtils.getParamsValueFromPage(request, "mes_author");
		String mesBody = GwmUtils.getParamsValueFromPage(request, "mes_body");
		model.addAttribute("msgGroup", msgGroup);
		model.addAttribute("msgSex", msgSex);
		model.addAttribute("mesTitle", mesTitle);
		model.addAttribute("mesAuthor", mesAuthor);
		model.addAttribute("mesBody", mesBody);
	}
}