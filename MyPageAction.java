package com.internousdev.spring.action;

import java.sql.SQLException;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.spring.dao.UserInfoDAO;
import com.internousdev.spring.dto.UserInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class MyPageAction extends ActionSupport implements SessionAware {

	UserInfoDTO userInfoDTO = new UserInfoDTO();
	private Map<String, Object> session;

	public String execute() throws SQLException {

		String tempLogined = String.valueOf(session.get("loginFlg"));
		int userId = "null".equals(tempLogined) ? 0 : Integer.parseInt(tempLogined);

		if (userId != 1) {
			return "loginError";
		}

		UserInfoDAO userInfoDAO = new UserInfoDAO();
		userInfoDTO = userInfoDAO.getMypageUserInfo(session.get("userId").toString());
		return SUCCESS;
	}

	public UserInfoDTO getUserInfoDTO() {
		return userInfoDTO;
	}

	public void setUserInfoDTO(UserInfoDTO userInfoDTO) {
		this.userInfoDTO = userInfoDTO;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
