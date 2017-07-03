package edu.fjut.fundsys.service;

import java.util.ArrayList;
import java.util.List;



import edu.fjut.fundsys.dao.AdminUserDao;
import edu.fjut.fundsys.domain.AdminUser;
import edu.fjut.fundsys.domain.ClientUser;
import edu.fjut.fundsys.domain.Feature;
import edu.fjut.fundsys.domain.Fund;
import edu.fjut.fundsys.domain.FundHold;
import edu.fjut.fundsys.domain.FundType;
import edu.fjut.fundsys.helper.ClientInfosQueryHelper;
import edu.fjut.fundsys.helper.FundQueryHelper;

public class AdminUserServiceImpl implements AdminUserService {
	private AdminUserDao adminuserdao;
	public void setAdminuserdao(AdminUserDao adminuserdao) {
		this.adminuserdao = adminuserdao;
	}

	@Override
	public void checkUser(AdminUser user) {
		adminuserdao.checkUser(user);
	}

	@Override
	public void fundAdd(Fund fund) {
		adminuserdao.fundAdd(fund);

	}

	@Override
	public List<Fund> fundList(Integer fundPageNo) {
		return adminuserdao.fundList(fundPageNo);
	}

	@Override
	public Fund getFundById(Integer FundNo) {
		return adminuserdao.getFundById(FundNo);
	}

	@Override
	public void updateFund(Fund fund) {
		adminuserdao.updateFund(fund);

	}

	@Override
	public void deleteFund(Integer fundNo) {
		adminuserdao.deleteFund(fundNo);

	}

	@Override
	public void batchDeleteFund(String fundNo) {
		List<Integer> fundNoList = new ArrayList<Integer>();
		String[] fundNoArr = fundNo.split(",");
		for (String string : fundNoArr) {
			fundNoList.add(Integer.valueOf(string));
		}
		System.out.println(fundNoList.toString());
		adminuserdao.batchDeleteFund(fundNoList);

	}

	@Override
	public List<ClientUser> clientList() {
		return adminuserdao.clientList();
	}

	@Override
	public void frezonOrThawClient(String clientId, String active) {
		adminuserdao.frezonOrThawClient(clientId, active);

	}

	@Override
	public void featureAdd(Feature feature) {
		adminuserdao.featureAdd(feature);

	}

	@Override
	public List<Feature> features() {
		return adminuserdao.features();
	}

	@Override
	public void featureDelete(Integer featureId) {
		adminuserdao.deleteFeature(featureId);

	}

	@Override
	public Integer fundListSize() {
		return adminuserdao.fundListSize();
	}

	@Override
	public List<FundType> fundTypes() {
		return adminuserdao.fundTypes();
	}

	@Override
	public void updateFeature(Feature feature) {
		adminuserdao.updateFeature(feature);

	}

	@Override
	public List<Fund> fundByType(Integer typeId) {
		return adminuserdao.fundByType(typeId);
	}

	@Override
	public int totalFundsInfo(FundQueryHelper helper) {
		return adminuserdao.totalFundsInfo(helper);
	}

	@Override
	public List<Fund> allFundsInfos(FundQueryHelper helper, int beginIdx) {
		return adminuserdao.allFundsInfos(helper, beginIdx);
	}

	@Override
	public void fundTypeAdd(FundType fundType) {
		adminuserdao.fundTypeAdd(fundType);

	}

	@Override
	public void fundTypeDelete(Integer typeId) {
		adminuserdao.fundTypeDelete(typeId);

	}

	@Override
	public List<FundHold> loadFundHold() {
		return adminuserdao.loadFundHold();
	}

	@Override
	public void addAdminUser(AdminUser adminUser) {
		adminuserdao.addAdminUser(adminUser);
	}

	@Override
	public AdminUser findAdminUserById(String adminUserId) {
		return adminuserdao.findAdminUserById(adminUserId);
	}

	@Override
	public void deleteAdminUserById(String adminUserId) {
		adminuserdao.deleteAdminUserById(adminUserId);
	}

	@Override
	public int totalClient(ClientInfosQueryHelper helper) {
		return adminuserdao.totalClient(helper);
	}

	@Override
	public List<ClientUser> allClientList(ClientInfosQueryHelper helper,
			int beginIdx) {
		return adminuserdao.allClientList(helper, beginIdx);
	}

	@Override
	public Boolean isOwn(Integer fundno) {
     return adminuserdao.isOwn(fundno);
	}

}
