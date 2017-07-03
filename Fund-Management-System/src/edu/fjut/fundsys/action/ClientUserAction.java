package edu.fjut.fundsys.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import edu.fjut.fundsys.domain.ClientPicture;
import edu.fjut.fundsys.domain.ClientUser;
import edu.fjut.fundsys.exception.DataAccessException;
import edu.fjut.fundsys.jwt.Jwt;
import edu.fjut.fundsys.service.ClientUserService;
import edu.fjut.fundsys.utils.SendMail;
import edu.fjut.fundsys.utils.VerificationCode;

public class ClientUserAction extends BaseAction {
	private String ClientId;
	private String ClientPwd;
	private String ClientName;
	private String Sex;
	private String Phone;
	private String Address;
	private String Email;
	private Double Balance;
	private Boolean Active;
	private File image;
	private String imageFileName; // 文件名称
	

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	private int resultcode;
	private String message;
	private String token;
	private Map<String, Object> data = null;
	private ClientUserService clientUserService;

	private ClientUser clientUser;
	private String checkCode;
	private String pwd;
	private String clientCheckCode;

    
	public void setImage(File image) {
		this.image = image;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setResultcode(int resultcode) {
		this.resultcode = resultcode;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@JSON(serialize = false)
	public String getClientCheckCode() {
		return clientCheckCode;
	}

	public void setClientCheckCode(String clientCheckCode) {
		this.clientCheckCode = clientCheckCode;
	}

	@JSON(serialize = false)
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@JSON(serialize = false)
	public ClientUser getClientUser() {
		return clientUser;
	}

	public void setClientUser(ClientUser clientUser) {
		this.clientUser = clientUser;
	}

	@JSON(serialize = false)
	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public int getResultcode() {
		return resultcode;
	}

	public String getMessage() {
		return message;
	}

	public void setClientId(String clientId) {
		ClientId = clientId;
	}

	public void setClientPwd(String clientPwd) {
		ClientPwd = clientPwd;
	}

	public void setClientName(String clientName) {
		ClientName = clientName;
	}

	public void setSex(String sex) {
		Sex = sex;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public void setBalance(Double balance) {
		Balance = balance;
	}

	public void setActive(Boolean active) {
		Active = active;
	}

	public void setClientUserService(ClientUserService clientUserService) {
		this.clientUserService = clientUserService;
	}

	public String clientLogin() {
		// ClientId
		// ClientPwd
		data = new HashMap<String, Object>();
		try {
			clientUserService.checkUser(ClientId, ClientPwd);
			Map<String, Object> payload = new HashMap<String, Object>();
			Date date = new Date();
			payload.put("uid", ClientId);// 用户ID
			payload.put("iat", date.getTime());// 生成时间
			payload.put("ext", date.getTime() + 1000 * 60 * 60);// 过期时间1小时
			token = Jwt.createToken(payload);
			data.put("token", token);
			data.put("ClientId", ClientId);
			resultcode = 0;
			message = "认证成功";
		} catch (DataAccessException e) {
			resultcode = -1;
			message = e.getMessage();
			data.put("ClientId", ClientId);
		}
		return "clientlogin";
	}

	public String checkEmail() {
		// clientUser.Email
		// clientUser.ClientName
		data = new HashMap<String, Object>();
		VerificationCode verificationCode = new VerificationCode();
		checkCode = verificationCode.getRandNum(100000, 999999);
		System.out.println("生成向邮箱发送验证码:" + checkCode);
/*		ServletActionContext.getRequest().getSession()
				.setAttribute("code", checkCode);
		ServletActionContext.getRequest().getSession()
				.setMaxInactiveInterval(180);*/
        ServletActionContext.getServletContext().setAttribute(checkCode, 1);			
		SendMail sm = new SendMail(clientUser.getEmail(), checkCode,
				clientUser.getClientName());
		sm.start();
		data.put("注册用户", clientUser.getClientName());
		resultcode = 0;
		message = "邮箱发送成功";
		return "send_email";
	}

	public String registCilentUser() {
		/*
		 * clientUser.ClientId clientUser.ClientName clientUser.ClientPwd
		 * clientUser.Sex clientUser.Phone clientUser.Address clientUser.Email
		 * clientUser.Balance=10000 clientUser.totolEarnings=0 Active=true
		 * clientCheckCode用户回执验证码
		 */
		data = new HashMap<String, Object>();
		   Integer i=(Integer) ServletActionContext.getServletContext().getAttribute(clientCheckCode);
			try {
				if (i==null) {
					System.out.println("这是用户回传的:" + clientCheckCode);
					data.put("clientUser", "验证码输入错误,请检查!");
					resultcode = -1;
					message = "验证码输入错误，注册失败";
				} else if(i==1){
					clientUserService.addClientUser(clientUser);
					ServletActionContext.getServletContext().setAttribute(clientCheckCode, 0);
					data.put("clientUser", clientUser);
					resultcode = 0;
					message = "用户注册成功";
				}else{
					data.put("clientUser", "验证码输入错误,请检查!");
					resultcode = -1;
					message = "验证码输入错误，注册失败";
				}
			} catch (DataAccessException e) {
			data.put("clientUserName", clientUser.getClientName());
			resultcode = -1;
			message = e.getMessage();
		}
		return "client_regist";
	}

	public String loadClientInfo() {
		// clientUser.ClientId
		// token
		data = new HashMap<String, Object>();
		clientUser = clientUserService.getClientUserById(clientUser
				.getClientId());
		data.put("clientUser", clientUser);
		resultcode = 0;
		message = "用户更新/显示个人信息";
		return "preupdate_ClientUser";
	}

	public String updateClientUser() {
		// * clientUser.ClientId
		// * clientUser.ClientName
		// * clientUser.Sex
		// * clientUser.Phone
		// * clientUser.Address
		// * token
		data = new HashMap<String, Object>();
		clientUser.setClientPwd(clientUserService.getClientUserById(
				clientUser.getClientId()).getClientPwd());
		clientUser.setBalance(clientUserService.getClientUserById(
				clientUser.getClientId()).getBalance());
		clientUser.setTotolEarnings(clientUserService.getClientUserById(
				clientUser.getClientId()).getTotolEarnings());
		clientUser.setActive(clientUserService.getClientUserById(
				clientUser.getClientId()).getActive());
		clientUserService.updateClientUser(clientUser);
		data.put("clientUser", clientUser);
		resultcode = 0;
		message = "用户修改个人信息成功";
		return "update_ClientUser";
	}

	public String modifyPwdClientUser() {
		// * clientUser.ClientId
		// * clientUser.ClientPwd
		// * pwd重复密码
		// * token
		data = new HashMap<String, Object>();
		try {
			clientUserService.modifyClientUserPwd(clientUser.getClientId(),
					clientUser.getClientPwd());
			data.put("clientId", clientUser.getClientId());
			resultcode = 0;
			message = "用户密码修改成功";
		} catch (DataAccessException e) {
			data.put("clientUser", clientUser.getClientId());
			resultcode = -1;
			message = e.getMessage();
		}
		return "client_modifyPwd";
	}

	/**
	 * 上传图片
	 * 
	 * @return
	 */
	public String upload() {
		data = new HashMap<String, Object>();
		String realpath = ServletActionContext.getServletContext().getRealPath(
				"/image");
		System.out.println(realpath);
		ClientPicture clientPicture = clientUserService
				.getClientPicture(ClientId);
		if (clientPicture != null) {
			clientPicture.setPictureOldname(clientPicture.getPictureNewname());
			if (imageFileName != null && imageFileName.length() != 0) {
				Random random = new Random(99999);
				int tempInt = random.nextInt();
				Date datenew = new Date();
				SimpleDateFormat simpleDateFormatnew = new SimpleDateFormat(
						"yyyyMMddhhmmss");
				int last = imageFileName.lastIndexOf(".");
				String head = imageFileName.substring(0, last);
				String type = imageFileName.substring(last);
				imageFileName = simpleDateFormatnew.format(datenew) + tempInt
						+ type;
				System.out.println("新的文件名称是：" + imageFileName);
				clientPicture.setPictureNewname(imageFileName);
			}
			clientUserService.updateClientPicture(clientPicture);
			resultcode = 0;
			message = "图片修改成功";
		} else {
			clientPicture = new ClientPicture();
			clientPicture.setClientId(ClientId);
			clientPicture.setPictureOldname(imageFileName);
			if (imageFileName != null && imageFileName.length() != 0) {
				Random random = new Random(99999);
				int tempInt = random.nextInt();
				Date datenew = new Date();
				SimpleDateFormat simpleDateFormatnew = new SimpleDateFormat(
						"yyyyMMddhhmmss");
				int last = imageFileName.lastIndexOf(".");
				String head = imageFileName.substring(0, last);
				String type = imageFileName.substring(last);
				imageFileName = simpleDateFormatnew.format(datenew) + tempInt
						+ type;
				System.out.println("新的文件名称是：" + imageFileName);
				clientPicture.setPictureNewname(imageFileName);
			}
			clientUserService.addClientPicture(clientPicture);
			resultcode = 0;
			message = "图片添加成功";
		}

		if (image != null) {
			File savefile = new File(new File(realpath), imageFileName);
			System.out.println(savefile);
			System.out.println(savefile.getParentFile());
			if (savefile.getParentFile().exists()) {
				try {
					savefile.getParentFile().mkdirs();
					FileUtils.copyFile(image, savefile);
					data.put("ClientId", ClientId);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return "upload";
	}

	/**
	 * 获取用户头像图片url
	 */
	public String picUrl() {
		data = new HashMap<String, Object>();
		ClientPicture clientPicture = clientUserService
				.getClientPicture(ClientId);
		if (clientPicture == null) {
			data.put("ClientId", ClientId);
			data.put("url", "");
			resultcode = -1;
			message = "该用户没有上传头像";
		} else {
			String name = clientPicture.getPictureNewname();
			StringBuffer path = new StringBuffer();
			path.append("http://localhost:8080/FMS/image/");
			path.append(name);
			data.put("ClientId", ClientId);
			data.put("url", path.toString());
			resultcode = 0;
			message = "返回成功";
		}

		return "picurl";
	}

}
