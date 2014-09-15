package com.b5m.web.core;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class Constants {

	/** 请求或发送数据时的默认编码,UTF-8 */
	public static String DEFAULT_ENCODING = "UTF-8";

	/** HTML类型文档的ContentType,text/html;charset=$default_encoding */
	public final static String HTML_CONTENT_TYPE = "text/html;charset=" + DEFAULT_ENCODING;

	public final static String XML_CONTENT_TYPE = "text/xml;charset=" + DEFAULT_ENCODING;

	/** application/x-json */
	public final static String JSON_CONTENT_TYPE = "application/x-json";

	/** 数据类型 */
	public final static int TYPE_STRING = Types.VARCHAR;
	public final static int TYPE_INTEGER = Types.INTEGER;
	public final static int TYPE_LONG = Types.BIGINT;
	public final static int TYPE_BOOLEAN = Types.BOOLEAN;
	public final static int TYPE_DOUBLE = Types.DOUBLE;
	public final static int TYPE_FLOAT = Types.FLOAT;
	public final static int TYPE_DATE = Types.DATE;
	public final static int TYPE_ARRAY = Types.ARRAY;

	/** 数据类型映射表 */
	public final static Map<String, Integer> TYPE_MAPPING = new HashMap<String, Integer>();
	static {
		TYPE_MAPPING.put("string", TYPE_STRING);
		TYPE_MAPPING.put("integer", TYPE_INTEGER);
		TYPE_MAPPING.put("long", TYPE_LONG);
		TYPE_MAPPING.put("boolean", TYPE_BOOLEAN);
		TYPE_MAPPING.put("double", TYPE_DOUBLE);
		TYPE_MAPPING.put("float", TYPE_FLOAT);
		TYPE_MAPPING.put("date", TYPE_DATE);
		TYPE_MAPPING.put("array", TYPE_ARRAY);
	}

	public final static String LOG_NAME = "plugin.b5m.log";
	public final static String MSG_FORMAT_START = "[";
	public final static String MSG_FORMAT_END = "]";
	public final static String MSG_FORMAT_SPLIT = " ";

	/** 登陆用户cookie对象--->登陆的频道ID **/
	public final static String USER_CHANNEL_ID_COOKIE = "chnId";
	/** 登陆用户cookie对象--->登陆的mail **/
	public final static String USER_MAIL_COOKIE = "email";
	/** 登陆用户cookie对象--->登陆的密码 **/
	public final static String USER_PWD_COOKIE = "pwd";
	/** 登陆用户cookie对象--->登陆的审核状态：0、未审核，1、已审核 **/
	public final static String USER_CHECK_STATUS_COOKIE = "checkStatus";
	/** 登陆用户cookie对象--->登陆的邮箱状态：0、未激活，1、已激活 **/
	public final static String USER_MAIL_STATUS_COOKIE = "mailStatus";
	/** 登陆用户cookie对象--->登陆的封号状态：0、未封号，1、已封号 **/
	public final static String USER_CLOSE_STATUS_COOKIE = "closeStatus";
	/** 登陆用户cookie对象--->群组id，用，隔开 **/
	public final static String USER_GROUP_IDS_COOKIE = "groupIds";
	/** 登陆用户cookie对象--->登陆的频道名 **/
	public final static String USER_CHANNEL_NAME_COOKIE = "chnName";
	/** 登陆用户cookie对象--->登陆的频道头像 **/
	public final static String USER_CHANNEL_AVATAR_COOKIE = "avatar";
	/** 登陆用户cookie对象--->是否登陆 **/
	public final static String USER_CHANNEL_ISLOGIN = "isLogin";
	/** 登陆用户cookie对象--->登陆的登录状态 **/
	public final static String STATUS_OK = "true";
	/** 登陆用户cookie对象--->登陆的非登录状态 **/
	public final static String STATUS_OFF = "false";

	/** 登陆用户cookie对象--->登陆的记住密码 **/
	public final static String USER_IS_REMEMBER_COOKIE = "isRemember";
	/** 登陆用户cookie对象--->登陆的密码长度 **/
	public final static String USER_PWD_LENGTH_COOKIE = "pwdLength";

	public final static String BIZ_USER_LOGIN = "login";

	public final static String BIZ_USER_NOT_LOGIN = "noLogin";

	public final static String BIZ_USER_CHECKED = "checked";

	public final static String BIZ_USER_NOT_CHECKED = "notChecked";

	public final static String MAKER_CODE_SESSION_ID = "makerSessionID";
}
