package edu.fjut.fundsys.service;

import java.util.List;
import java.util.Map;

import edu.fjut.fundsys.dao.AdminUserDao;
import edu.fjut.fundsys.dao.FundTransInfoDao;
import edu.fjut.fundsys.domain.Fund;
import edu.fjut.fundsys.domain.FundHold;
import edu.fjut.fundsys.domain.FundTransInfo;
import edu.fjut.fundsys.domain.RateRecord;
import edu.fjut.fundsys.exception.DataAccessException;
import edu.fjut.fundsys.helper.FundTransQueryHelper;

public class FundTransInfoServiceImpl implements FundTransInfoService {
	private FundTransInfoDao fundTransInfoDao;
	private AdminUserDao adminUserDao;
	
	

	public void setAdminUserDao(AdminUserDao adminUserDao) {
		this.adminUserDao = adminUserDao;
	}

	public void setFundTransInfoDao(FundTransInfoDao fundTransInfoDao) {
		this.fundTransInfoDao = fundTransInfoDao;
	}

	@Override
	public List<FundTransInfo> loadAll() {
		// TODO Auto-generated method stub
		return fundTransInfoDao.loadAll();
	}

	@Override
	public void applyFund(FundTransInfo fundTransInfo) {
	    Fund fund=adminUserDao.getFundById(fundTransInfo.getFundNo());
	    String status=fund.getFundStatus();
	    if("未上市".equals(status)){throw new DataAccessException("该基金已下架，不能购买");}
		fundTransInfoDao.applyFund(fundTransInfo);

	}

	@Override
	public void ransomFund(FundTransInfo fundTransInfo) {
		fundTransInfoDao.ransomFund(fundTransInfo);

	}

	@Override
	public void yestodayEarn() {
		fundTransInfoDao.yestodayEarn();

	}

	@Override
	public List<RateRecord> rateRecords() {
		return fundTransInfoDao.rateRecords();
	}

	@Override
	public List<RateRecord> rateRecords(Integer fundNo) {
		return fundTransInfoDao.rateRecords(fundNo);
	}

	@Override
	public List<FundHold> loadFundHoldById(String ClientNo) {
		return fundTransInfoDao.loadFundHoldById(ClientNo);
	}

	@Override
	public Map<String, Object> detailFundHold(Integer hid) {
		return fundTransInfoDao.detailFundHold(hid);
	}

	@Override
	public int totalTransInfo(FundTransQueryHelper helper) {
		return fundTransInfoDao.totalTransInfo(helper);
	}

	@Override
	public List<FundTransInfo> allTransInfos(FundTransQueryHelper helper,
			int beginIdx) {
		return fundTransInfoDao.allTransInfos(helper, beginIdx);
	}

}
