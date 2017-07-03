package edu.fjut.fundsys.service;

import java.util.List;
import java.util.Map;

import edu.fjut.fundsys.domain.FundHold;
import edu.fjut.fundsys.domain.FundTransInfo;
import edu.fjut.fundsys.domain.RateRecord;
import edu.fjut.fundsys.helper.FundTransQueryHelper;

public interface FundTransInfoService {
	List<FundTransInfo> loadAll();

	void applyFund(FundTransInfo fundTransInfo);

	void ransomFund(FundTransInfo fundTransInfo);

	void yestodayEarn();

	List<RateRecord> rateRecords();

	List<RateRecord> rateRecords(Integer fundNo);

	// ×éºÏ²éÑ¯
	int totalTransInfo(FundTransQueryHelper helper);

	List<FundTransInfo> allTransInfos(FundTransQueryHelper helper, int beginIdx);

	List<FundHold> loadFundHoldById(String ClientNo);

	Map<String, Object> detailFundHold(Integer hid);

}
