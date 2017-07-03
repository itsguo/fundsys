package edu.fjut.fundsys.dao;

import java.util.List;

import edu.fjut.fundsys.domain.ClientPicture;
import edu.fjut.fundsys.domain.ClientUser;
import edu.fjut.fundsys.domain.ClientUserTrans;
import edu.fjut.fundsys.helper.CLientUserQueryhelper;

public interface ClientUserDao {
	/*
	 * 用户的增删改查
	 */
	void checkUser(String no, String pwd);

	void addclientUser(ClientUser clientUser);

	ClientUser getClientUserById(String clientUserId);

	void updateClientUser(ClientUser clientUser);

	void modifyClientUserPwd(String clientUserId, String pwd);

	List<ClientUserTrans> clientUserFindAll(String clientUserId);

	void addClientUserTrans(ClientUserTrans clientUserTrans);

	void addClientPicture(ClientPicture clientPicture);

	ClientPicture getClientPicture(String clientId);

	void updateClientPicture(ClientPicture clientPicture);

	int totalNumber(CLientUserQueryhelper helper);

	List<ClientUserTrans> totalClientUserTrans(CLientUserQueryhelper helper,
			int beginIdx);

}