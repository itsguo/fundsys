package edu.fjut.fundsys.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.opensymphony.xwork2.Preparable;
import com.sun.security.ntlm.Client;

import edu.fjut.fundsys.domain.AdminUser;
import edu.fjut.fundsys.domain.ClientUser;
import edu.fjut.fundsys.domain.Feature;
import edu.fjut.fundsys.domain.Fund;
import edu.fjut.fundsys.domain.FundHold;
import edu.fjut.fundsys.domain.FundType;
import edu.fjut.fundsys.exception.DataAccessException;
import edu.fjut.fundsys.helper.CLientUserQueryhelper;
import edu.fjut.fundsys.helper.ClientInfosQueryHelper;
import edu.fjut.fundsys.helper.FundQueryHelper;
import edu.fjut.fundsys.jwt.Jwt;
import edu.fjut.fundsys.service.AdminUserService;
import edu.fjut.fundsys.service.SetRateService;

/**
 * 后台管理员Action
 * 
 * @author Guo 
 * 后台管理员登录 
 * 添加基金操作 
 * 查看基金列表 
 * 修改基金信息 
 * 修改基金状态 
 * 删除基金产品 
 * 批量删除基金产品 
 * 获取客户信息表
 * 冻结/解冻客户 
 * 广告添加 
 * 广告删除 
 * 查看广告列表 
 * 增加基金类型 
 * 删除基金类型 
 * 获取基金类型 
 * 根据基金类型获取基金 
 * 获取所有客户的持仓情况
 */
public class AdminUserAction extends BaseAction{
	private String userNo;
	private String userPwd;
	/* */
	private AdminUserService adminUserService;
	private SetRateService setRateService;
	/* 登录操作返回token */
	private String token;
	/*
	 * 基金对象： fund.FundName; fund.FundPrice; fund.FundDescribe; fund.date
	 */
	private Fund fund;

	private Map<String, Object> data = null;
	private int resultcode;
	private String message;

	private List<Fund> fundList;
	private List<ClientUser> clientList;

	private String fundNoArr;

	private String clientId;
	private String active;

	private String featureTitle;
	private String featureDescribe;
	private String featureInfo;
	private String featureInfo2;
	private Integer featureId;
	private List<Feature> features;
	private String fundPageNo;

	private List<FundType> fundTypes = null;
	private Integer fundTypesId;

	private String qryFundNo;
	private String qryFundName;
	private String qryFundStatus;
	private String qryfundTypeId;
	private int totalCnt;
	private List<Fund> fundListByHelper = null;
	private FundQueryHelper helper = null;
	private String fundTypeName;
	private String fundTypeId;
	private List<FundHold> fundHolds;
	private String fundTypeDescribe;
	private AdminUser adminUser;
	private String fundStatus;

	private String qryClientId;
	private String qryClientName;
	private String qryClientSex;
	private String qryClientActive;
	private List<ClientUser> clientListHelper = null;
	private ClientInfosQueryHelper helper2 = null;

	public void setQryClientId(String qryClientId) {
		this.qryClientId = qryClientId;
	}

	public void setQryClientName(String qryClientName) {
		this.qryClientName = qryClientName;
	}

	public void setQryClientSex(String qryClientSex) {
		this.qryClientSex = qryClientSex;
	}

	public void setQryClientActive(String qryClientActive) {
		this.qryClientActive = qryClientActive;
	}

	public void setClientListHelper(List<ClientUser> clientListHelper) {
		this.clientListHelper = clientListHelper;
	}

	public void setHelper2(ClientInfosQueryHelper helper2) {
		this.helper2 = helper2;
	}

	public void setFundStatus(String fundStatus) {
		this.fundStatus = fundStatus;
	}

	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}

	public void setFundTypeDescribe(String fundTypeDescribe) {
		this.fundTypeDescribe = fundTypeDescribe;
	}

	public void setFundTypeId(String fundTypeId) {
		this.fundTypeId = fundTypeId;
	}

	public void setFundTypeName(String fundTypeName) {
		this.fundTypeName = fundTypeName;
	}

	public void setHelper(FundQueryHelper helper) {
		this.helper = helper;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

	public void setQryFundNo(String qryFundNo) {
		this.qryFundNo = qryFundNo;
	}

	public void setQryFundName(String qryFundName) {
		this.qryFundName = qryFundName;
	}

	public void setQryFundStatus(String qryFundStatus) {
		this.qryFundStatus = qryFundStatus;
	}

	public void setQryfundTypeId(String qryfundTypeId) {
		this.qryfundTypeId = qryfundTypeId;
	}

	public void setFundListByHelper(List<Fund> fundListByHelper) {
		this.fundListByHelper = fundListByHelper;
	}

	public void setFundTypesId(Integer fundTypesId) {
		this.fundTypesId = fundTypesId;
	}

	public void setFeatureInfo2(String featureInfo2) {
		this.featureInfo2 = featureInfo2;
	}

	public void setFundPageNo(String fundPageNo) {
		this.fundPageNo = fundPageNo;
	}

	public void setFeatureId(Integer featureId) {
		this.featureId = featureId;
	}

	public void setFeatureTitle(String featureTitle) {
		this.featureTitle = featureTitle;
	}

	public void setFeatureDescribe(String featureDescribe) {
		this.featureDescribe = featureDescribe;
	}

	public void setFeatureInfo(String featureInfo) {
		this.featureInfo = featureInfo;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public void setSetRateService(SetRateService setRateService) {
		this.setRateService = setRateService;
	}

	public void setFundNoArr(String fundNoArr) {
		this.fundNoArr = fundNoArr;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public int getResultcode() {
		return resultcode;
	}

	public String getMessage() {
		return message;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}
     @JSON(serialize=false)
	public Fund getFund() {
		return fund;
	}

	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	/**
	 * 操作员登录操作 如果用户名和密码正确，生成一个token返回
	 * 
	 * @return
	 */
	public String adminLogin() {
		// System.out.println(ServletActionContext.getRequest().getRequestURI());
		data = new HashMap<String, Object>();

		AdminUser adminUser = new AdminUser();
		adminUser.setUserNo(userNo);
		adminUser.setUserPwd(userPwd);
		try {
			adminUserService.checkUser(adminUser);
			Map<String, Object> payload = new HashMap<String, Object>();
			Date date = new Date();
			payload.put("uid", userNo);// 用户ID
			payload.put("iat", date.getTime());// 生成时间
			payload.put("ext", date.getTime() + 1000 * 60 * 60);// 过期时间1小时
			token = Jwt.createToken(payload);
			data.put("token", token);
			data.put("userNo", userNo);
			resultcode = 0;
			message = "认证成功";
		} catch (DataAccessException e) {
			data.put("userNo", userNo);
			resultcode = -1;
			message = e.getMessage();
		}

		return "adminlogin";
	}

	/**
	 * 添加基金操作 需要携带token进行身份验证
	 */
	public String adminFundAdd() {
		// 基金名称 fund.FundName
		// 基金描述 fund.FundDescribe
		// 基金创建日期 fund.Date
		// 基金类型Id fund.fundType.fundTypeId
		data = new HashMap<String, Object>();
		// System.out.println(fund.getDate());
		try {
			adminUserService.fundAdd(fund);
			data.put("FundName", fund.getFundName());
			resultcode = 0;
			message = "添加成功";
		} catch (DataAccessException e) {
			data.put("FundName", fund.getFundName());
			resultcode = -1;
			message = e.getMessage();
		}

		return "fundadd";
	}

	/**
	 * 查看基金列表
	 */
	public String adminLoadFund() {
		// fundPageNo
		setRateService.setRate(Integer.valueOf(fundPageNo));
		data = new HashMap<String, Object>();
		fundList = new ArrayList<Fund>();
		fundList = adminUserService.fundList(Integer.valueOf(fundPageNo));
		Integer fundTotleNum = adminUserService.fundListSize();
		data.put("fundPageNo", fundPageNo);
		data.put("FundList", fundList);
		data.put("fundTotleNum", fundTotleNum);
		resultcode = 0;
		message = "返回成功";
		return "loadfund";
	}

	/**
	 * 修改基金信息
	 * 
	 */
	public String adminUpdateFund() {
		// fund.FundNo fund.FundName fund.FundPrice fund.FundDescribe
		// fund.FundStatus
		data = new HashMap<String, Object>();
		adminUserService.updateFund(fund);
		data.put("FundNo", fund.getFundNo());
		resultcode = 0;
		message = "修改成功";
		return "fundupdate";
	}

	/**
	 * 删除基金产品
	 */
	public String adminDeleteFund() {
		// fund.FundNo
		data = new HashMap<String, Object>();
		try {
			adminUserService.deleteFund(fund.getFundNo());
			data.put("FundNo", fund.getFundNo());
			resultcode = 0;
			message = "删除成功";
		} catch (DataAccessException e) {
			data.put("FundNo", fund.getFundNo());
			resultcode = -1;
			message = e.getMessage();
		}

		return "funddelete";
	}

	/**
	 * 修改基金状态
	 */
	public String adminModifyFundStatus() {
		// fund.FundNo fundStatus
		Boolean b=adminUserService.isOwn(fund.getFundNo());
		data = new HashMap<String, Object>();
		Fund newfund = adminUserService.getFundById(fund.getFundNo());
		if ("true".equals(fundStatus)) {
			newfund.setFundStatus("已上市");
			adminUserService.updateFund(newfund);
			data.put("fundno", fund.getFundNo());
			resultcode = 0;
			message = "修改成功";
		} else if(!b){
			newfund.setFundStatus("未上市");
			adminUserService.updateFund(newfund);
			data.put("fundno", fund.getFundNo());
			resultcode = 0;
			message = "修改成功";
		} else {
			data.put("fundno", fund.getFundNo());
			resultcode = -1;
			message = "有人持有该基金，不能下架";
		}

		return "modifyfundstatus";
	}

	/**
	 * 批量删除基金产品
	 */
	public String adminBatchDelete() {
		// fundNoArr
		data = new HashMap<String, Object>();
		try {
			adminUserService.batchDeleteFund(fundNoArr);
			data.put("FundNoArr", fundNoArr);
			resultcode = 0;
			message = "批量删除成功";
		} catch (DataAccessException e) {
			data.put("FundNoArr", fundNoArr);
			resultcode = -1;
			message = e.getMessage();
		}

		return "batchdelete";
	}

	/**
	 * 获取客户信息列表
	 */
	public String adminGetClientList() {
		data = new HashMap<String, Object>();
		clientList = new ArrayList<ClientUser>();
		clientList = adminUserService.clientList();
		Integer clientTotleNum = clientList.size();
		data.put("clientList", clientList);
		data.put("clientTotleNum", clientTotleNum);
		resultcode = 0;
		message = "返回成功";
		return "loadclient";
	}

	/**
	 * 冻结/解冻用户
	 */
	public String adminFrozenOrThawClient() {
		// clientId active
		data = new HashMap<String, Object>();
		adminUserService.frezonOrThawClient(clientId, active);
		data.put("clientId", clientId);
		resultcode = 0;
		if ("true".equals(active))
			message = "解冻成功";
		else
			message = "冻结成功";
		return "frozenorthaw";
	}

	/**
	 * 添加广告
	 */
	public String adminAddFeature() {
		// featureTitle featureDescribe featureInfo featureInfo2
		data = new HashMap<String, Object>();
		try {
			adminUserService.featureAdd(new Feature(featureTitle,
					featureDescribe, featureInfo, featureInfo2));
			data.put("featureTitle", featureTitle);
			message = "广告添加成功";
			resultcode = 0;
		} catch (DataAccessException e) {
			data.put("featureTitle", featureTitle);
			message = e.getMessage();
			resultcode = -1;
		}

		return "addfeature";
	}

	/**
	 * 广告删除
	 */
	public String adminDeleteFeature() {
		// featureId
		data = new HashMap<String, Object>();
		adminUserService.featureDelete(featureId);
		data.put("featureId", featureId);
		message = "广告删除成功";
		resultcode = 0;
		return "deletefeature";
	}

	/**
	 * 查看广告列表
	 */
	public String adminLoadFeature() {
		data = new HashMap<String, Object>();
		features = adminUserService.features();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Feature feature : features) {
			map = new HashMap<String, Object>();
			map.put("id", feature.getFeatureId());
			map.put("title", feature.getFeatureTitle());
			String[] s = new String[3];
			s[0] = feature.getFeatureDescribe();
			s[1] = feature.getFeatureInfo();
			s[2] = feature.getFeatureInfo2();
			map.put("info", s);
			list.add(map);
		}
		data.put("feature_list", list);
		resultcode = 0;
		message = "返回广告列表成功";
		return "loadfeature";
	}

	/**
	 * 修改广告
	 */
	public String adminUpdateFeature() {
		// featureId featureTitle featureDescribe featureInfo featureInfo2
		data = new HashMap<String, Object>();
		adminUserService.updateFeature(new Feature(featureId, featureTitle,
				featureDescribe, featureInfo, featureInfo2));
		data.put("featureId", featureId);
		resultcode = 0;
		message = "修改成功";

		return "updatefeature";
	}

	/**
	 * 获取基金类型
	 */
	public String adminGetFundType() {
		data = new HashMap<String, Object>();
		fundTypes = adminUserService.fundTypes();
		data.put("fundTypes", fundTypes);
		resultcode = 0;
		message = "返回基金类型列表成功";
		return "fundtype";
	}

	/**
	 * 根据基金类型获取基金列表
	 */
	public String adminGetFundByType() {
		// fundTypesId fundPageNo
		setRateService.setRate(Integer.valueOf(fundPageNo));
		data = new HashMap<String, Object>();
		fundList = adminUserService.fundByType(fundTypesId);
		data.put("fundList", fundList);
		resultcode = 0;
		message = "返回成功";
		return "getfundbytype";
	}

	/**********************************************************************/
	public String toGetoneFund() {
		// fund.FundNo
		data = new HashMap<String, Object>();
		try {
			fund = adminUserService.getFundById(fund.getFundNo());
			System.out.println("測試:" + fund.getFundType().getFundTypeName());
			data.put("fund", fund);
			resultcode = 0;
			message = "返回一条记录";
		} catch (DataAccessException e) {
			data.put("listHelper", fund.getFundNo());
			resultcode = -1;
			message = e.getMessage();
		}
		return "toGetOneFund";
	}

	/*
	 * 组合查询,得到所有的基金的信息 返回所有的基金表,和记录数
	 */
	public String lookFundByHelper() {
		// qryFundNo;
		// qryFundName;
		// qryFundStatus;
		// qryfundTypeId;
		// fundPageNo;
		data = new HashMap<String, Object>();
		try {
			int pageNo = Integer.parseInt(fundPageNo);
			helper = new FundQueryHelper(this.qryFundNo, this.qryFundName,
					this.qryFundStatus, this.qryfundTypeId);
			totalCnt = adminUserService.totalFundsInfo(helper);
			fundListByHelper = adminUserService.allFundsInfos(helper, pageNo);
			data.put("listHelper", fundListByHelper);
			data.put("total", totalCnt);
			resultcode = 0;
			message = "用户交易明细,组合查询版本";
		} catch (DataAccessException e) {
			data.put("listHelper", fundListByHelper);
			data.put("结果", "为空");
			resultcode = -1;
			message = "空";
		}
		return "lookFundByHelper";
	}

	/********************************************************************************************/

	/**
	 * 增加基金类型
	 * 
	 * @return
	 */
	public String adminAddFundType() {
		// fundTypeName
		// fundTypeDescribe
		data = new HashMap<String, Object>();
		adminUserService.fundTypeAdd(new FundType(fundTypeName,
				fundTypeDescribe));
		data.put("fundTypeName", fundTypeName);
		resultcode = 0;
		message = "添加成功";
		return "addfundtype";
	}

	/**
	 * 删除基金类型
	 */
	public String adminDeleteFundType() {
		// fundTypeId
		data = new HashMap<String, Object>();
		try {
			adminUserService.fundTypeDelete(Integer.valueOf(fundTypeId));
			data.put("fundTypeId", fundTypeId);
			resultcode = 0;
			message = "删除成功";
		} catch (DataAccessException e) {
			data.put("fundTypeId", fundTypeId);
			resultcode = -1;
			message = e.getMessage();
		}

		return "deletefundtype";
	}

	/**
	 * 获取所有客户的持仓情况
	 */
	public String adminLoadFundHold() {
		data = new HashMap<String, Object>();
		fundHolds = adminUserService.loadFundHold();
		data.put("fundHolds", fundHolds);
		resultcode = 0;
		message = "返回成功";
		return "loadfundhold";
	}

	public String adminAddAdmin() {
		// adminUser.userNo
		// adminUser.userPwd
		// adminUser.userIsSuperAdmin
		data = new HashMap<String, Object>();
		adminUserService.addAdminUser(adminUser);
		data.put("添加信息", adminUser);
		resultcode = 0;
		message = "成功";
		return "adminAddAdmin";
	}

	public String adminDeleteadmin() {
		// adminUser.userNo
		data = new HashMap<String, Object>();
		adminUserService.deleteAdminUserById(adminUser.getUserNo());
		data.put("删除成功", adminUser.getUserNo());
		resultcode = 0;
		message = "成功";
		return "adminDeleteadmin";
	}

	/*
	 * 组合查询,得到所有的用户的信息 返回所有的用户表,和记录数
	 */
	public String allClient() {
		// qryClientId;
		// qryClientName;
		// qryClientSex;
		// qryClientActive;
		// fundPageNo
		data = new HashMap<String, Object>();
		try {
			int pageNo = Integer.parseInt(fundPageNo);
			helper2 = new ClientInfosQueryHelper(qryClientId, qryClientName,
					qryClientSex, qryClientActive);
			totalCnt = adminUserService.totalClient(helper2);
			clientListHelper = adminUserService.allClientList(helper2, pageNo);
			data.put("listHelper", clientListHelper);
			data.put("total", totalCnt);
			resultcode = 0;
			message = "用户交易明细,组合查询版本";
		} catch (DataAccessException e) {
			data.put("listHelper", clientListHelper);
			data.put("结果", "为空");
			resultcode = -1;
			message = "空";
		}
		return "allClient";
	}
    
	public String rateChange(){
		data = new HashMap<String, Object>();
		setRateService.setRate();
		data.put("ratechange", "change");
		resultcode=0;
		message="利率变化成功";
		return "ratechange";
	}
}
