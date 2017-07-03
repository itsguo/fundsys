package edu.fjut.fundsys.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.fjut.fundsys.dao.AdminUserDao;
import edu.fjut.fundsys.dao.FundTransInfoDao;
import edu.fjut.fundsys.domain.Fund;
import edu.fjut.fundsys.domain.RateRecord;
import edu.fjut.fundsys.utils.RandomUtils;

public class SetRateServiceImpl implements SetRateService {
	private AdminUserDao adminUserDao;
	private FundTransInfoDao fundTransInfoDao;

	public void setFundTransInfoDao(FundTransInfoDao fundTransInfoDao) {
		this.fundTransInfoDao = fundTransInfoDao;
	}

	public void setAdminUserDao(AdminUserDao adminUserDao) {
		this.adminUserDao = adminUserDao;
	}

	/**
	 * 利率变化 记录利率变化
	 */
	@Override
	public void setRate(Integer fundPageNo) {
		List<Fund> fundlist = adminUserDao.fundList(fundPageNo);
		for (Fund fund : fundlist) {
			if (fund != null) {
				if (RandomUtils.getPlusOrMinus()) {
					fund.setRate(fund.getRate() + RandomUtils.getChangeNum());
				} else {
			           if(fund.getRate() - RandomUtils.getChangeNum()<0.03){
				        		fund.setRate(0.03+ RandomUtils.getChangeNum());
				           }else{
				        	   fund.setRate(fund.getRate() - RandomUtils.getChangeNum()); 
				           }
				}
				Integer i = fundTransInfoDao.rateRecords(fund.getFundNo())
						.size();
				RateRecord rateRecord = new RateRecord();
				if (i <= 6) {
					rateRecord.setFund(fund);
					rateRecord.setRate(fund.getRate());
					fundTransInfoDao.saveRateRecord(rateRecord);
					adminUserDao.updateFund(fund);
				} else {
					fundTransInfoDao.deleteRateRecord(fund.getFundNo());
					rateRecord.setFund(fund);
					rateRecord.setRate(fund.getRate());
					fundTransInfoDao.saveRateRecord(rateRecord);
					adminUserDao.updateFund(fund);
				}
			}
		}
	}

	@Override
	public void setRate() {
		List<Fund> fundlist = adminUserDao.fundList();
		for (Fund fund : fundlist) {
			if (fund != null) {
				if (RandomUtils.getPlusOrMinus()) {
					fund.setRate(fund.getRate() + RandomUtils.getChangeNum());
				} else {
			           if(fund.getRate() - RandomUtils.getChangeNum()<0.03){
			        		fund.setRate(0.03+ RandomUtils.getChangeNum());
			           }else{
			        	   fund.setRate(fund.getRate() - RandomUtils.getChangeNum()); 
			           }
				          
				}
				Integer i = fundTransInfoDao.rateRecords(fund.getFundNo())
						.size();
				RateRecord rateRecord = new RateRecord();
				if (i <= 6) {
					rateRecord.setFund(fund);
					rateRecord.setRate(fund.getRate());
					fundTransInfoDao.saveRateRecord(rateRecord);
					adminUserDao.updateFund(fund);
				} else {
					fundTransInfoDao.deleteRateRecord(fund.getFundNo());
					rateRecord.setFund(fund);
					rateRecord.setRate(fund.getRate());
					fundTransInfoDao.saveRateRecord(rateRecord);
					adminUserDao.updateFund(fund);
				}
			}
		}

	}

}
