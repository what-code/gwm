package com.b5m.web.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


/**
 * 控制器的基类
 * @author Wiley
 *
 */
public abstract class AbstractBaseController{

	protected B5MLogger logger=new B5MLogger(AbstractBaseController.class.getName());
	
	private ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();
	private ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();
	private ThreadLocal<PrintWriter> out = new ThreadLocal<PrintWriter>();

	private MultipartHttpServletRequest multipartRequest = null;
	private boolean blMultipart=false;
	
	
	public AbstractBaseController() {
		super();
		logger=new B5MLogger(this.getClass().getName());
	}

	public final void _setServlet(HttpServletRequest request, HttpServletResponse response) {
		this.request.set(this.parseMultipart(request));// 自动解析是否有文件上传
		this.response.set(response);
		response.setCharacterEncoding(Constants.DEFAULT_ENCODING);
		response.setContentType(Constants.HTML_CONTENT_TYPE);
	}
	
	protected final boolean isMultipart(){
		return blMultipart;
	}

	/**
	 * 分析是否上传文件请求
	 * 
	 * @param request as HttpServletRequest
	 * @return HttpServletRequest
	 */
	protected final HttpServletRequest parseMultipart(HttpServletRequest request) {
		this.blMultipart = ServletFileUpload.isMultipartContent(request);
		if (!this.blMultipart) {
			try {
				request.setCharacterEncoding(Constants.DEFAULT_ENCODING);
			} catch (UnsupportedEncodingException uee) {
				logger.logError(uee, "parseMultipart");
			}
			return request;
		}
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding(Constants.DEFAULT_ENCODING);
		try {
			this.multipartRequest = resolver.resolveMultipart(request);
		} catch (MultipartException me) {
			logger.logError(me, "parseMultipart");
		}
		return this.multipartRequest;
	}

	protected final HttpServletRequest getMultipartRequest() {
		return (this.multipartRequest == null) ? this.getRequest() : this.multipartRequest;
	}

	/**
	 * 
	 * @return Iterator&lt;Input.name&gt;
	 */
	public final String[] getUploadNames(String prefix) {
		if (!this.blMultipart)
			return null;
		Iterator<String> ite = multipartRequest.getFileNames();// input.name
		List<String> names = new ArrayList<String>();
		String tmpString = null;
		while (ite.hasNext()) {
			tmpString = ite.next();
			if (tmpString.startsWith(prefix))
				names.add(tmpString);
		}
		return names.toArray(new String[names.size()]);
	}

	/**
	 * //根据getUploadNames()的得到文件对象
	 * 
	 * @param fname as String
	 * @return MultipartFile
	 */
	public final MultipartFile getUploadFile(String fname) {
		if (!this.blMultipart)
			return null;
		return this.multipartRequest.getFile(fname);
	}

	protected final String getString(String pName) {
		return _getParam(request.get(), pName);
	}

	protected final String getString(String pName, String defVal) {
		return _getParam(request.get(), pName, defVal);
	}

	private final String _getParam(HttpServletRequest pReq, String pName, String defVal) {
		String tmp=pReq.getParameter(pName);
		return StringUtils.isBlank(tmp)?defVal:tmp;
	}

	private final String _getParam(HttpServletRequest pReq, String pName) {
		String tmp = "";
		if (StringUtils.isBlank(pName))
			return tmp;
		tmp = pReq.getParameter(pName);
		return StringUtils.isBlank(tmp) ? "" : tmp;
	}

	protected final int getInt(String pName) {
		int val = 0;
		String tmp = _getParam(request.get(), pName);
		try {
			if (StringUtils.isNotBlank(tmp))
				val = Integer.parseInt(tmp);
		} catch (Exception e) {
			val = 0;
		}
		return val;
	}

	protected final long getLong(String pName) {
		long val = 0;
		String tmp = _getParam(request.get(), pName);
		try {
			if (StringUtils.isNotBlank(tmp))
				val = Long.parseLong(tmp);
		} catch (Exception e) {
			val = 0;
		}
		return val;
	}

	//protected final Date getDate(String pName) {
		// String tmp=_getParam(request.get(), pName);
	//	return null;
	//}

	protected final boolean getBoolean(String pName) {
		String tmp = _getParam(request.get(), pName);
		return (tmp.equalsIgnoreCase("true") || tmp.equalsIgnoreCase("y") || tmp.equalsIgnoreCase("1"));
	}

	protected final double getDouble(String pName) {
		double val = 0.0d;
		String tmp = _getParam(request.get(), pName);
		try {
			if (StringUtils.isNotBlank(tmp))
				val = Double.parseDouble(tmp);
		} catch (Exception e) {
			val = 0.0d;
		}
		return val;
	}

	protected final float getFloat(String pName) {
		float val = 0.0f;
		String tmp = _getParam(request.get(), pName);
		try {
			if (StringUtils.isNotBlank(tmp))
				val = Float.parseFloat(tmp);
		} catch (Exception e) {
			val = 0.0f;
		}
		return val;
	}

	protected final String[] getArrays(String pName) {
		String[] vals = null;
		vals = request.get().getParameterValues(pName);
		if (null==vals)
			vals = new String[] {};
		return vals;
	}

	protected final String getInputString() {
		String str = null;
		InputStream in = null;
		try {
			in = request.get().getInputStream();
			str = IOUtils.toString(in, request.get().getCharacterEncoding());
			in.close();
		} catch (IOException ioe) {
			logger.logError(ioe, "getInputString");
			str = null;
		} finally {
			IOUtils.closeQuietly(in);
		}
		return str;
	}
	
	/**
	 * 获取参数值
	 * @param key
	 * @param type
	 * @return
	 */
	private final Object _getClearTypeObject(String key,int type){
		Object obj=null;
		switch(type){
			case Constants.TYPE_STRING: obj=getString(key); break;	
			case Constants.TYPE_INTEGER: obj=getInt(key); break;
			case Constants.TYPE_LONG: obj= getLong(key); break;
			case Constants.TYPE_BOOLEAN: obj= getBoolean(key); break;
			case Constants.TYPE_DOUBLE: obj= getDouble( key); break;
			case Constants.TYPE_FLOAT: obj=getFloat( key); break;
			case Constants.TYPE_DATE: obj=getString(key);/*obj=getDate(key);*/ break;
			case Constants.TYPE_ARRAY: obj= getArrays(key); break;
			default:
		}
		return obj;
	}
	
	/**
	 * 将参数映射成DTO对象
	 * @param fieldType "{fieldName1:String,fieldName2:Integer，...}"
	 * @param t
	 * @return
	 */
	protected final <T> T getParamDTO(String fieldType,T t){
		BeanWrapperImpl bwi=new BeanWrapperImpl(t);
		JSONObject jo=JSONObject.fromString(fieldType);
		JSONArray jaNames=jo.names();
		Object[] objNames=jaNames.toArray();
		for (Object objName : objNames) {
			String strName=(String)objName;//字段名
			String strValue=jo.getString(strName).toLowerCase();//字段类型
			int intType=Constants.TYPE_MAPPING.get(strValue);
			//logger.logDebug("paramName:"+strName);
			//logger.logDebug("paramValue:"+_getClearTypeObject(strName,intType));
			bwi.setPropertyValue(strName, _getClearTypeObject(strName,intType));
		}
		return t;
	}
	
	
	/*************************/
	/*
	@RequestMapping()
	public abstract void DefaultAction(PrintWriter out);
	//public abstract void DefaultAction(ModelMap map)
		
	@RequestMapping(params = "action=json")
	public abstract void JsonAction(PrintWriter out);
	*/
	protected final PrintWriter getOut(){
		if(this.out.get()==null){
			try{
				this.out.set(this.response.get().getWriter());
			}catch(IOException ioe){
				logger.logError(ioe, "getOut");
			}
		}
		return this.out.get();
	}
	protected final HttpServletRequest getRequest(){
		return this.request.get();
	}
	protected final HttpServletResponse getResponse(){
		return this.response.get();
	}
	
	protected final String getIpAddr(){
        String ip=this.getRequest().getHeader("x-forwarded-for");
        if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip))
            ip=this.getRequest().getHeader("Proxy-Client-IP");
        if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip))
            ip=this.getRequest().getHeader("WL-Proxy-Client-IP");
        if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip))
            ip=this.getRequest().getRemoteAddr();
        return ip;
    }
}
