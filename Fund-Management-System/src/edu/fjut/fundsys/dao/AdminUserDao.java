package edu.fjut.fundsys.dao;

import java.util.List;

import org.logicalcobwebs.proxool.admin.Admin;

import edu.fjut.fundsys.domain.AdminUser;
import edu.fjut.fundsys.domain.ClientUser;
import edu.fjut.fundsys.domain.Feature;
import edu.fjut.fundsys.domain.Fund;
import edu.fjut.fundsys.domain.FundHold;
import edu.fjut.fundsys.domain.FundType;
import edu.fjut.fundsys.helper.ClientInfosQueryHelper;
import edu.fjut.fundsys.helper.FundQueryHelper;

public interface AdminUserDao {
	void checkUser(AdminUser user);

	void fundAdd(Fund fund);

	Integer fundListSize();

	List<Fund> fundList(Integer fundPageNo);

	Fund getFundById(Integer FundNo);

	void updateFund(Fund fund);

	void deleteFund(Integer fundNo);

	void batchDeleteFund(List<Integer> fundNoList);

	List<ClientUser> clientList();

	void frezonOrThawClient(String clientId, String active);

	void featureAdd(Feature feature);

	List<Feature> features();

	void deleteFeature(Integer featureId);

	Feature findFeatureById(Integer featureId);

	void updateFeature(Feature feature);

	List<FundType> fundTypes();

	List<Fund> fundByType(Integer typeId);

	int totalFundsInfo(FundQueryHelper helper);

	List<Fund> allFundsInfos(FundQueryHelper helper, int beginIdx);

	void fundTypeAdd(FundType fundType);

	void fundTypeDelete(Integer typeId);

	List<FundHold> loadFundHold();

	List<Fund> fundList();

	void addAdminUser(AdminUser adminUser);

	AdminUser findAdminUserById(String adminUserId);

	void deleteAdminUserById(String adminUserId);

	int totalClient(ClientInfosQueryHelper helper);

	List<ClientUser> allClientList(ClientInfosQueryHelper helper, int beginIdx);
	
	Boolean isOwn(Integer fundno);
}
