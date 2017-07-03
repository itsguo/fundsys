package edu.fjut.fundsys.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import antlr.collections.impl.IntRange;
import edu.fjut.fundsys.domain.AdminUser;
import edu.fjut.fundsys.domain.ClientUser;
import edu.fjut.fundsys.domain.Feature;
import edu.fjut.fundsys.domain.Fund;
import edu.fjut.fundsys.domain.FundHold;
import edu.fjut.fundsys.domain.FundType;
import edu.fjut.fundsys.domain.RateRecord;
import edu.fjut.fundsys.exception.DataAccessException;
import edu.fjut.fundsys.helper.ClientInfosQueryHelper;
import edu.fjut.fundsys.helper.FundQueryHelper;

public class AdminUserDaoImpl extends HibernateDaoSupport implements
		AdminUserDao {

	/**
	 * ����û��Ƿ���� ����û����������Ƿ���ȷ
	 */
	@Override
	public void checkUser(AdminUser user) {

		AdminUser adminUser = (AdminUser) this.getHibernateTemplate().get(
				AdminUser.class, user.getUserNo());

		if (adminUser == null) {
			throw new DataAccessException("�û�������");
		}
		;
		if (!adminUser.getUserPwd().equals(user.getUserPwd())) {
			throw new DataAccessException("�û�����,���������");
		}

	}

	/**
	 * ���ӻ���
	 */
	@Override
	public void fundAdd(Fund fund) {
		try {
			this.getHibernateTemplate().save(fund);
		} catch (Exception e) {
			throw new DataAccessException("�������Ѿ�����");
		}

	}

	/**
	 * �����б�
	 */
	@Override
	public List<Fund> fundList(Integer fundPageNo) {
		Query query = this.getHibernateTemplate().getSessionFactory()
				.openSession().createQuery("from Fund");
		query.setFirstResult((fundPageNo - 1) * 5);
		query.setMaxResults(5);
		return query.list();
	}

	/**
	 * �����б� ��ҳ��
	 */
	public List<Fund> fundList() {
		return this.getHibernateTemplate().find("from Fund");
	}

	/**
	 * ����ID�Ų��һ���
	 */
	@Override
	public Fund getFundById(Integer FundNo) {
		Fund fund = (Fund) this.getHibernateTemplate().get(Fund.class, FundNo);
		if (fund == null) {
			throw new DataAccessException("�����ڴ˻���");
		}
		return fund;
	}

	/**
	 * ���»���
	 */
	@Override
	public void updateFund(Fund fund) {
		Fund newFund = this.getFundById(fund.getFundNo());
		newFund.setFundName(fund.getFundName());
		newFund.setFundDescribe(fund.getFundDescribe());
		newFund.setFundStatus(fund.getFundStatus());
		newFund.setRate(fund.getRate());
		this.getHibernateTemplate().update(newFund);
		this.getSession().flush();
	}

	/**
	 * ɾ������
	 */
	@Override
	public void deleteFund(Integer fundNo) {
		List<FundHold> fundHolds = this.loadFundHold();
		for (FundHold fundHold : fundHolds) {
			if (fundHold.getFundId().equals(fundNo)) {
				throw new DataAccessException("��ǰ�пͻ����иò�Ʒ������ɾ��");
			}
		}
		List<RateRecord> rateRecords = this.getHibernateTemplate()
				.getSessionFactory().openSession()
				.createQuery("from RateRecord r where r.fund.FundNo=?")
				.setParameter(0, fundNo).list();
		for (RateRecord rateRecord : rateRecords) {
			this.getHibernateTemplate().delete(rateRecord);
		}
		Fund fund = this.getFundById(fundNo);
		this.getHibernateTemplate().delete(fund);

	}

	/**
	 * ����ɾ������
	 */
	@Override
	public void batchDeleteFund(List<Integer> fundNoList) {
		List<FundHold> fundHolds = this.loadFundHold();
		for (FundHold fundHold : fundHolds) {
			for (int i = 0; i < (fundNoList.size()); i++) {
				if (fundHold.getFundId().equals(fundNoList.get(i))) {
					throw new DataAccessException("��ǰ�пͻ�����" + fundNoList.get(i)
							+ "�Ż��𣬲���ɾ��,�빴��������ɾ��");
				}
			}
		}

		for (int i = 0; i < (fundNoList.size()); i++) {
			List<RateRecord> rateRecords = this.getHibernateTemplate()
					.getSessionFactory().openSession()
					.createQuery("from RateRecord r where r.fund.FundNo=?")
					.setParameter(0, fundNoList.get(i)).list();
			for (RateRecord rateRecord : rateRecords) {
				this.getHibernateTemplate().delete(rateRecord);
			}
			Fund fund = this.getFundById(fundNoList.get(i));
			this.getHibernateTemplate().delete(fund);

		}

	}

	/**
	 * ��ȡ�ͻ���Ϣ�б�
	 */
	@Override
	public List<ClientUser> clientList() {
		return this.getHibernateTemplate().find(
				"from ClientUser c order by c.ClientId desc");
	}

	/**
	 * ����/�ⶳ�ͻ�
	 */
	public void frezonOrThawClient(String clientId, String active) {
		ClientUser clientUser = (ClientUser) this.getHibernateTemplate().get(
				ClientUser.class, clientId);
		if ("true".equals(active)) {
			clientUser.setActive(true);
			this.getHibernateTemplate().update(clientUser);
		} else {
			clientUser.setActive(false);
			this.getHibernateTemplate().update(clientUser);
		}

	}

	/**
	 * ���ӹ��
	 */
	public void featureAdd(Feature feature) {
		List<Feature> features = this.features();
		Integer m = features.size();
		if (m >= 3) {
			throw new DataAccessException("��ǰ��������3������ɾ��1��������");
		} else
			this.getHibernateTemplate().save(feature);

	}

	/**
	 * �鿴����б�
	 */
	@Override
	public List<Feature> features() {
		return this.getHibernateTemplate().find("from Feature");
	}

	/**
	 * ɾ�����
	 */
	@Override
	public void deleteFeature(Integer featureId) {
		Feature feature = this.findFeatureById(featureId);
		this.getHibernateTemplate().delete(feature);
	}

	/**
	 * �޸Ĺ��
	 */
	public void updateFeature(Feature feature) {
		this.getHibernateTemplate().update(feature);

	}

	/**
	 * ����Id���ҹ��
	 */
	@Override
	public Feature findFeatureById(Integer featureId) {
		return (Feature) this.getHibernateTemplate().get(Feature.class,
				featureId);
	}

	/**
	 * �����б�����
	 */
	public Integer fundListSize() {
		return this.getHibernateTemplate().find("from Fund").size();
	}

	/**
	 * ��ȡ���������б�
	 */
	public List<FundType> fundTypes() {
		return this.getHibernateTemplate().find("from FundType");
	}

	/**
	 * ���ݻ������ͻ�ȡ����
	 */
	public List<Fund> fundByType(Integer typeId) {
		return this.getHibernateTemplate().getSessionFactory().openSession()
				.createQuery("from Fund where fundType.fundTypeId=?")
				.setParameter(0, typeId).list();

	}

	// ������������--��ϲ�ѯ�����׼�¼

	private DetachedCriteria getDetachedCriteriaByHelper(FundQueryHelper helper) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Fund.class);
		if (StringUtils.isNotEmpty(helper.getQryFundNo())) {
			criteria.add(Restrictions.eq("FundNo",
					Integer.parseInt(helper.getQryFundNo())));
		}
		if (StringUtils.isNotEmpty(helper.getQryFundName())) {
			criteria.add(Restrictions.like("FundName",
					"%" + helper.getQryFundName() + "%"));
		}
		if (StringUtils.isNotEmpty(helper.getQryFundStatus())) {
			criteria.add(Restrictions.eq("FundStatus",
					helper.getQryFundStatus()));
		}
		if (StringUtils.isNotEmpty(helper.getQryfundTypeId())) {
			criteria.createCriteria("fundType").add(
					Restrictions.eq("fundTypeId",
							Integer.parseInt(helper.getQryfundTypeId())));
		}
		criteria.addOrder(Order.desc("FundNo"));
		return criteria;
	}

	@Override
	public int totalFundsInfo(FundQueryHelper helper) {
		DetachedCriteria detachedCriteria = this
				.getDetachedCriteriaByHelper(helper);
		detachedCriteria.setProjection(Projections.rowCount());
		Session session = this.getSession();
		Transaction trans = session.beginTransaction();
		int cnt = 0;
		try {
			Criteria criteria = detachedCriteria.getExecutableCriteria(session);
			cnt = Integer.parseInt(criteria.list().get(0).toString());
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
		} finally {
			if (session.isOpen())
				session.close();
		}

		return cnt;
	}

	@Override
	public List<Fund> allFundsInfos(FundQueryHelper helper, int beginIdx) {
		Session session = this.getSession();
		Transaction trans = session.beginTransaction();
		List<Fund> fundList = null;
		int endIdx;
		try {
			DetachedCriteria detachedCriteria = this
					.getDetachedCriteriaByHelper(helper);
			Criteria criteria = detachedCriteria.getExecutableCriteria(session);
			if ((5 * beginIdx) < this.totalFundsInfo(helper)
					|| (5 * beginIdx) == this.totalFundsInfo(helper)) {
				endIdx = 5;
			} else {
				endIdx = this.totalFundsInfo(helper) - 5 * (beginIdx - 1);
			}
			fundList = criteria.setFirstResult(5 * (beginIdx - 1))
					.setMaxResults(endIdx).list();
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
		} finally {
			if (session.isOpen())
				session.close();
		}
		return fundList;
	}

	/**
	 * ���ӻ�������
	 */
	public void fundTypeAdd(FundType fundType) {
		this.getHibernateTemplate().save(fundType);

	}

	/**
	 * ɾ����������
	 */
	public void fundTypeDelete(Integer typeId) {
		try {
			FundType fundType = (FundType) this.getHibernateTemplate().get(
					FundType.class, typeId);
			this.getHibernateTemplate().delete(fundType);
		} catch (Exception e) {
			throw new DataAccessException("��ǰ�������л����Ʒ������ɾ��������");
		}

	}

	/**
	 * ��ȡ���пͻ��ĳֲ����
	 */
	public List<FundHold> loadFundHold() {
		return this.getHibernateTemplate().find("from FundHold");
	}

	@Override
	public void addAdminUser(AdminUser adminUser) {
		this.getHibernateTemplate().save(adminUser);
	}

	@Override
	public AdminUser findAdminUserById(String adminUserId) {
		return (AdminUser) this.getHibernateTemplate().get(AdminUser.class,
				adminUserId);
	}

	@Override
	public void deleteAdminUserById(String adminUserId) {
		AdminUser adminUser = this.findAdminUserById(adminUserId);
		this.getHibernateTemplate().delete(adminUser);
	}

	// �ͻ����
	private DetachedCriteria getClientDetachedCriteriaHelper(
			ClientInfosQueryHelper helper) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ClientUser.class);
		if (StringUtils.isNotEmpty(helper.getQryClientId())) {
			criteria.add(Restrictions.like("ClientId",
					"%" + helper.getQryClientId() + "%"));
		}
		if (StringUtils.isNotEmpty(helper.getQryClientName())) {
			criteria.add(Restrictions.like("ClientName",
					"%" + helper.getQryClientName() + "%"));
		}
		if (StringUtils.isNotEmpty(helper.getQryClientSex())) {
			criteria.add(Restrictions.eq("Sex", helper.getQryClientSex()));
		}
		if (StringUtils.isNotEmpty(helper.getQryClientActive())) {
			criteria.add(Restrictions.eq("Active",
					Boolean.parseBoolean(helper.getQryClientActive())));
		}
		criteria.addOrder(Order.desc("ClientId"));
		return criteria;
	}

	@Override
	public int totalClient(ClientInfosQueryHelper helper) {
		DetachedCriteria detachedCriteria = this
				.getClientDetachedCriteriaHelper(helper);
		detachedCriteria.setProjection(Projections.rowCount());
		Session session = this.getSession();
		Transaction trans = session.beginTransaction();
		int cnt = 0;
		try {
			Criteria criteria = detachedCriteria.getExecutableCriteria(session);
			cnt = Integer.parseInt(criteria.list().get(0).toString());
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
		} finally {
			if (session.isOpen())
				session.close();
		}

		return cnt;
	}

	@Override
	public List<ClientUser> allClientList(ClientInfosQueryHelper helper,
			int beginIdx) {
		Session session = this.getSession();
		Transaction trans = session.beginTransaction();
		List<ClientUser> clientList = null;
		int endIdx;
		try {
			DetachedCriteria detachedCriteria = this
					.getClientDetachedCriteriaHelper(helper);
			Criteria criteria = detachedCriteria.getExecutableCriteria(session);
			if ((5 * beginIdx) < this.totalClient(helper)
					|| (5 * beginIdx) == this.totalClient(helper)) {
				endIdx = 5;
			} else {
				endIdx = this.totalClient(helper) - 5 * (beginIdx - 1);
			}
			clientList = criteria.setFirstResult(5 * (beginIdx - 1))
					.setMaxResults(endIdx).list();
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
		} finally {
			if (session.isOpen())
				session.close();
		}
		return clientList;
	}

   /**
    * ���ĳ������Ƿ񱻿ͻ�����
    */
	public Boolean isOwn(Integer fundno) {
		
	List<FundHold> fundHolds=this.getSession().createQuery("from FundHold f where f.fund.FundNo=?").setParameter(0,fundno).list();
	if(fundHolds.size()>0&&fundHolds!=null){
		return true;
	}else
	{
		return false;
	}
	
	}

}