package com.zhao.deep.service;

import java.util.List;

import com.zhao.deep.bean.User;
import com.zhao.deep.common.ServerResponse;

public interface IUserService {

	ServerResponse<User> login(String username, String password);

	ServerResponse<String> register(User user);

	ServerResponse<String> checkValid(String str, String type);

	ServerResponse<String> selectQuestion(String username);

	ServerResponse<String> checkAnswer(String username, String question, String answer);

	ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);

	ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user);
	
	ServerResponse<User> updateInformation(User user);

	ServerResponse<User> getInformation(Integer userId);

	ServerResponse checkAdminRole(User user);

	ServerResponse<String> activeCode(String username, String activeCode);

	List<User> allUserMSGList();

	User selectByPrimaryKey(Integer userId);

	void deleteCommonUser(Integer id);
}
