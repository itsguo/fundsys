package edu.fjut.fundsys.utils;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.NewsAddress;

public class SendMail extends Thread {
	private String clientEmail;
	private String checkCode;
	private String clientName;
	private Object object = new Object();

	public SendMail(String clientEmail, String checkCode, String clientName) {
		super();
		this.clientEmail = clientEmail;
		this.checkCode = checkCode;
		this.clientName = clientName;
	}

	// 发送邮件
	public void run() {
		try {
			synchronized (object) {
				Properties props = new Properties();
				props.setProperty("mail.host", "smtp.163.com");
				props.setProperty("mail.transport.protocol", "smtp");
				props.setProperty("mail.smtp.auth", "true");// 请求认证，与JavaMail的实现有关
				Session session = Session.getInstance(props);
				// session.setDebug(true);//调试模式
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress("13067415626@163.com"));// 测试的时候用自己的邮箱作为发送使用，需要打开邮箱的smtp协议
				// 设置收件人：正常送(TO)、抄送(CC)、密送(BCC)
				msg.setRecipients(Message.RecipientType.TO, this.clientEmail);
				// 设置主题
				msg.setSubject("建设银行-模拟基金管理系统");
				// 设置邮件正文内容
				msg.setContent("尊敬的 [" + this.clientName + " ],这是你的激活码:<br/>"
						+ this.checkCode, "text/html;charset=UTF-8");
				msg.saveChanges();// 保存
				// 发送邮件
				Transport ts = session.getTransport();
				ts.connect("13067415626", "rxc1666166");// 账号和激活码,不是密码,邮箱的设置.
				ts.sendMessage(msg, msg.getAllRecipients());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("发送邮件失败，请检查");
		}
	}
}

/*
 * //邮件主体内容:组装过程 //文本部分 MimeBodyPart textPart = new MimeBodyPart();
 * textPart.setContent("开始的 aaa<img src='cid:mm'/>aaa",
 * "text/html;charset=UTF-8");
 * 
 * //图片部分 MimeBodyPart imagePart = new MimeBodyPart(); //搞数据进来需要用到JAF的API
 * DataHandler dh = new DataHandler(new
 * FileDataSource("src/1.jpg"));//自动探测文件的MIME类型 imagePart.setDataHandler(dh);
 * imagePart.setContentID("mm");
 * 
 * //附件部分 MimeBodyPart attachmentPart = new MimeBodyPart(); DataHandler dh1 =
 * new DataHandler(new FileDataSource("src/行.rar"));//自动探测文件的MIME类型 String name
 * = dh1.getName(); System.out.println(name);
 * attachmentPart.setDataHandler(dh1); //手工设置附件的名称
 * attachmentPart.setFileName(MimeUtility.encodeText(name));
 * 
 * //描述关系： MimeMultipart mmpart = new MimeMultipart();
 * mmpart.addBodyPart(textPart); mmpart.addBodyPart(imagePart);
 * mmpart.setSubType("related");//有关系的
 * 
 * //把文本图片当做一个部分 MimeBodyPart textImagePart = new MimeBodyPart();
 * textImagePart.setContent(mmpart);
 * 
 * MimeMultipart multipart = new MimeMultipart();
 * multipart.addBodyPart(textImagePart); multipart.addBodyPart(attachmentPart);
 * multipart.setSubType("mixed");//复杂关系
 * 
 * msg.setContent(multipart);
 * 
 * msg.saveChanges();
 */