package com.zhao.deep.dao;

import com.zhao.deep.bean.User;
import com.zhao.deep.bean.UserExample;
import com.zhao.deep.common.ServerResponse;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    void deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	int checkUsername(String username);

	User selectLogin(@Param("username") String username,@Param("password") String password);

	int checkEmail(String email);

	String selectQuestionByUsername(String username);

	int checkAnswer(@Param("username") String username,@Param("question")  String question, @Param("answer") String answer);

	/*int updatePasswordByUsername(@Param("username")String username, @Param("passwordNew")String passwordNew);*/
	int updatePasswordByUsername(@Param("username") String username,@Param("md5Password") String md5Password);

	int checkPassword(@Param(value="password")String password,@Param("userId")Integer userId);

	int checkEmailByUserId(@Param(value="email")String email,@Param(value="userId")Integer userId);

	int activeCode(@Param(value="username") String username, @Param(value="activeCode") String activeCode);

	//User select();

}