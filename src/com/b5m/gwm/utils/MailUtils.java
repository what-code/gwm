package com.b5m.gwm.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;

import com.b5m.web.core.ContextUtils;

public class MailUtils {
	// 邮件发送者邮箱用户
	private static final String SMTPUserName = "noreply@gwmei.com";
	// 邮件发送者邮箱密码
	private static final String SMTPPassword = "izene123";

	// 邮件发送者邮箱SMTP服务器
	private static final String SMTPServerHost = "smtp.gmail.com";
	// smtp服务器端口号
	private static final String SMTPServerPort = "465";
	// 传输类型
	private static final String TransportType = "smtp";
	// 服务器SSL校验
	// private static final String serverSSLEnable = "true";
	// 属性
	private static Properties props;

	/**
	 * 私有构造函数，防止外界新建本实用类的实例，因为直接使用MailUtil.sendMail发送邮件即可
	 * 
	 */
	private MailUtils() {
	}

	/**
	 * 静态构造器
	 */
	static {
		MailUtils.props = new Properties();
		// 存储发送邮件服务器的信息
		MailUtils.props.put("mail.smtp.host", MailUtils.SMTPServerHost);
		// 同时通过验证
		MailUtils.props.put("mail.smtp.auth", "true");
		MailUtils.props.put("mail.smtp.starttls.enable", "true");
	}

	/**
	 * 发送文本邮件
	 * 
	 * @param emailAddr
	 *            :收信人邮件地址
	 * @param emailTitle
	 *            :邮件标题
	 * @param emailConcept
	 *            :邮件内容
	 */
	public static boolean sendMail(String emailAddr, String emailTitle, String emailConcept) {
		// 根据属性新建一个邮件会话，null参数是一种Authenticator(验证程序) 对象
		Session s = Session.getInstance(MailUtils.props, null);
		// 设置调试标志,要查看经过邮件服务器邮件命令，可以用该方法
		s.setDebug(false);

		// 由邮件会话新建一个消息对象
		Message message = new MimeMessage(s);
		try {
			// 设置发件人
			Address from = new InternetAddress(MailUtils.SMTPUserName);
			message.setFrom(from);
			// 设置收件人
			// Address to = new InternetAddress(emailAddr);
			String[] arr = emailAddr.split("#");
			Address[] ato1 = new Address[arr.length];
			for (int i = 0; i < arr.length; i++) {
				if (!"".equals(arr[i].toString())) {
					ato1[i] = new InternetAddress(arr[i]);
				}

			}
			message.setRecipients(Message.RecipientType.TO, ato1);
			// 设置主题
			message.setSubject(emailTitle);
			// 设置信件内容
			message.setText(emailConcept);
			// 设置发信时间
			message.setSentDate(new Date());
			// 存储邮件信息
			message.saveChanges();
			Transport transport = s.getTransport(MailUtils.TransportType);
			// 要填入你的用户名和密码；
			transport.connect(MailUtils.SMTPServerHost, MailUtils.SMTPUserName, MailUtils.SMTPPassword);
			// 发送邮件,其中第二个参数是所有已设好的收件人地址
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			// System.out.println("发送邮件,邮件地址:" + emailAddr + " 标题:" + emailTitle
			// + " 内容:" + emailConcept + "成功!");
		} catch (Exception e) {
			// System.out.println(e.getMessage());
			System.out.println("发送邮件,邮件地址:" + emailAddr + " 标题:" + emailTitle + " 内容:" + emailConcept + "失败! 原因是" + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 通过邮箱发送内容为html网页的邮件
	 * 
	 * @param emailAddr
	 *            :收信人邮件地址
	 * @param emailTitle
	 *            :邮件标题
	 * @param emailConcept
	 *            :邮件内容
	 * @throws MessagingException
	 */
	public static boolean sendHtmlEmail(String emailAddr, String emailTitle, String emailConcept) {
		MailUtils.props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		MailUtils.props.setProperty("mail.smtp.socketFactory.fallback", "false");
		MailUtils.props.setProperty("mail.smtp.port", MailUtils.SMTPServerPort);
		MailUtils.props.setProperty("mail.smtp.socketFactory.port", MailUtils.SMTPServerPort);

		// 根据属性新建一个邮件会话
		Session s = Session.getInstance(MailUtils.props);
		// 设置调试标志,要查看经过邮件服务器邮件命令，可以用该方法
		s.setDebug(false);
		// 由邮件会话新建一个消息对象
		MimeMessage message = new MimeMessage(s);
		try {
			// 设置发件人
			InternetAddress from = null;
			try {
				from = new InternetAddress(MailUtils.SMTPUserName, MimeUtility.encodeText("帮5买服务"));
			} catch (UnsupportedEncodingException e) {
				from = new InternetAddress(MailUtils.SMTPUserName);
				e.printStackTrace();
			}
			message.setFrom(from);
			// 设置收件人
			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddr));
			// 设置主题
			message.setSubject(emailTitle);
			// 设置信件内容
			message.setContent(emailConcept, "text/html;charset=utf-8");
			// 设置发信时间
			message.setSentDate(new Date());
			// 存储邮件信息
			message.saveChanges();
			Transport transport = s.getTransport(MailUtils.TransportType);
			// 要填入你的用户名和密码；
			transport.connect(MailUtils.SMTPServerHost, MailUtils.SMTPUserName, MailUtils.SMTPPassword);
			// 发送邮件,其中第二个参数是所有已设好的收件人地址
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			// System.out.println("发送邮件,邮件地址:" + emailAddr + " 标题:" + emailTitle
			// + " 内容:" + emailConcept + "成功!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("发送邮件,邮件地址:" + emailAddr + " 标题:" + emailTitle + " 内容:" + emailConcept + "失败! 原因是" + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 获得邮箱的登录主页
	 * 
	 * @param email
	 * @return
	 */
	public static String getEmailHomepage(String email) {
		int indexOf = email.indexOf("@");
		String biginStr = "mail.";
		String endStr = email.substring(indexOf + 1);
		if (endStr.indexOf("gmail") == 0) {
			endStr = "google.com";
		}
		HttpServletRequest request = ContextUtils.getInstance().getRequest();
		String base = request.getScheme() + "://";
		return base + biginStr + endStr;
	}

	/**
	 * 测试邮件发送情况
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("开始发送。。。。");
		MailUtils.sendHtmlEmail("jia.liu@b5m.com", "测试验证", "<html><head></head><body>sjdfisdf</body></html>");
		System.out.println("发送完成。。。。");
	}
}
