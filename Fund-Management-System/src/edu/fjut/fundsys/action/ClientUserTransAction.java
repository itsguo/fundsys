package edu.fjut.fundsys.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.fjut.fundsys.domain.ClientUser;
import edu.fjut.fundsys.domain.ClientUserTrans;
import edu.fjut.fundsys.exception.ApplicationException;
import edu.fjut.fundsys.exception.DataAccessException;
import edu.fjut.fundsys.helper.CLientUserQueryhelper;
import edu.fjut.fundsys.service.ClientUserService;

public class ClientUserTransAction extends BaseAction {
	private int resultcode;
	private String message;
	private String token;
	private Map<String, Object> data = null;

	private ClientUserService clientUserService;
	private String balance;
	private ClientUserTrans clientUserTrans;
	private ClientUserTrans anotherClientUserTrans;
	private String anotherClientUserId;
	private String anotherClientUserName;
	private List<ClientUserTrans> clientUserTransList = null;
	private CLientUserQueryhelper helper = null;
	private String pageNo;
	private List<ClientUserTrans> listHelper = null;
	private int totalCnt;

	private ClientUser clientUser;
	private String transType;
	private String targetId;
	private String transId;

	public void setClientUser(ClientUser clientUser) {
		this.clientUser = clientUser;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getPageNo() {
		return pageNo;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setListHelper(List<ClientUserTrans> listHelper) {
		this.listHelper = listHelper;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public void setClientUserTransList(List<ClientUserTrans> clientUserTransList) {
		this.clientUserTransList = clientUserTransList;
	}

	public void setAnotherClientUserId(String anotherClientUserId) {
		this.anotherClientUserId = anotherClientUserId;
	}

	public void setAnotherClientUserName(String anotherClientUserName) {
		this.anotherClientUserName = anotherClientUserName;
	}

	public void setClientUserTrans(ClientUserTrans clientUserTrans) {
		this.clientUserTrans = clientUserTrans;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public int getResultcode() {
		return resultcode;
	}

	public void setResultcode(int resultcode) {
		this.resultcode = resultcode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setClientUserService(ClientUserService clientUserService) {
		this.clientUserService = clientUserService;
	}

	public String saveBalanceClientUser() {
		/*
		 * clientUserTrans.clientUser.ClientId balance//��Ǯ���
		 */
		data = new HashMap<String, Object>();
		try {
			double saveMoney = Double.parseDouble(balance);
			clientUserService.clientUserSaveBalance(clientUserTrans
					.getClientUser().getClientId(), saveMoney);
			System.out.println(saveMoney);
			System.out.println(clientUserTrans.getClientUser().getClientId());
			clientUserTrans.setTransType("���");
			clientUserTrans.setTransPrice(saveMoney);
			clientUserTrans.setTargetId("-");

			clientUserService.addClientUserTrans(clientUserTrans);
			data.put("����˻���", clientUserTrans.getClientUser().getClientId());
			data.put("�����", saveMoney);
			resultcode = 0;
			message = "�û���Ǯ�ɹ�";
		} catch (ApplicationException e) {
			data.put("����˻���", clientUserTrans.getClientUser().getClientId());
			resultcode = -1;
			message = e.getMessage();
		}
		return "saveBalance_ClientUser";
	}

	public String gotBalanceClientUser() {
		/*
		 * clientUserTrans.clientUser.ClientId balance//��Ǯ���
		 */
		data = new HashMap<String, Object>();
		try {
			double getMoney = Double.parseDouble(balance);
			clientUserService.clientUserGetBalance(clientUserTrans
					.getClientUser().getClientId(), getMoney);
			clientUserTrans.setTransType("ȡ��");
			clientUserTrans.setTransPrice(getMoney);
			clientUserTrans.setTargetId("-");
			clientUserService.addClientUserTrans(clientUserTrans);
			data.put("ȡ���˻���", clientUserTrans.getClientUser().getClientId());
			data.put("ȡ����", getMoney);
			resultcode = 0;
			message = "�û�ȡ��ɹ�";
		} catch (ApplicationException e) {
			data.put("ȡ���˻���", clientUserTrans.getClientUser().getClientId());
			resultcode = -1;
			message = e.getMessage();
		}
		return "getBalance_ClientUser";
	}

	public String transBalanceClientUser() {
		/*
		 * clientUserTrans.clientUser.ClientId balance//��Ǯ���
		 * anotherClientUserId//�Է��˻� anotherClientUserName//�Է�����
		 */
		data = new HashMap<String, Object>();
		try {
			double transMoney = Double.parseDouble(balance);
			clientUserService.clientUserTransBalance(anotherClientUserId,
					anotherClientUserName, transMoney, clientUserTrans
							.getClientUser().getClientId());
			clientUserTrans.setTransType("ת��");
			clientUserTrans.setTransPrice(transMoney);
			clientUserTrans.setTargetId(anotherClientUserId);
			anotherClientUserTrans=new ClientUserTrans();
			anotherClientUserTrans.setTransType("ת��");
			anotherClientUserTrans.setTransPrice(transMoney);
			anotherClientUserTrans.setCreateDate(clientUserTrans.getCreateDate());
			anotherClientUserTrans.setTargetId(clientUserTrans.getClientUser().getClientId());
			anotherClientUserTrans.setClientUser(clientUserService.getClientUserById(anotherClientUserId));
			clientUserService.addClientUserTrans(anotherClientUserTrans);
			clientUserService.addClientUserTrans(clientUserTrans);
			data.put("�Է��˻���", anotherClientUserId);
			data.put("�Է�����", anotherClientUserName);
			data.put("ת�˽��", transMoney);
			data.put("ת���˻�", clientUserTrans.getClientUser().getClientId());
			resultcode = 0;
			message = "�û�ת�˳ɹ�";
		} catch (ApplicationException e) {
			data.put("ת���˻�", clientUserTrans.getClientUser().getClientId());
			resultcode = -1;
			message = e.getMessage();
		}
		return "transBalance_ClientUser";
	}

	public String loadBalanceClientUser() {
		/*
		 * clientUserTrans.clientUser.ClientId
		 */
		data = new HashMap<String, Object>();
		try {
			clientUserTransList = clientUserService
					.clientUserFindAll(clientUserTrans.getClientUser()
							.getClientId());
			data.put("clientUserTransList", clientUserTransList);
			resultcode = 0;
			message = "�û�������ϸ";
		} catch (DataAccessException e) {
			data.put("clientUserTransList", clientUserTransList);
			resultcode = -1;
			message = e.getMessage();
		}
		return "loadBalance_ClientUser";
	}

	public String loadBalanceListByHelper() {
		/*
		 * ��ȡ�û���ת�˽�����Ϣ,ע��һ��Ҫ�ش�clientUser.ClientId,��Ȼ������ transId transType
		 * targetId pageNo clientUser.ClientId����Ǻ�̨�Ļ�,���Իش���ֵ
		 */
		data = new HashMap<String, Object>();
		try {
			int page = Integer.parseInt(pageNo);
			helper = new CLientUserQueryhelper(this.transId, this.transType,
					this.targetId, clientUser.getClientId());
			totalCnt = clientUserService.totalNumber(helper);
			listHelper = clientUserService.totalClientUserTrans(helper, page);
			data.put("listHelper", listHelper);
			data.put("total", totalCnt);
			resultcode = 0;
			message = "�û�������ϸ,��ϲ�ѯ�汾";
		} catch (DataAccessException e) {
			data.put("listHelper", listHelper);
			data.put("���", "�������Ϊ��/û��¼");
			resultcode = -1;
			message = "��";
		}
		return "loadBalanceListHelper_ClientUser";
	}
}