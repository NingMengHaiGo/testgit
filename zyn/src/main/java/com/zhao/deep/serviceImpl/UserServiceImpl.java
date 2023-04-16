package com.zhao.deep.serviceImpl;


import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhao.deep.bean.User;
import com.zhao.deep.common.Const;
import com.zhao.deep.common.ServerResponse;
import com.zhao.deep.common.TokenCache;
import com.zhao.deep.dao.UserMapper;
import com.zhao.deep.service.IUserService;
import com.zhao.deep.utils.MD5Util;
import com.zhao.deep.utils.MailUtil;

@Service("iUserService")
public class UserServiceImpl implements IUserService{

	@Autowired
	private UserMapper userMapper;
	
	public ServerResponse<User> login(String username, String password) {
		 int resultCount = userMapper.checkUsername(username);
	        if(resultCount == 0){
	            return ServerResponse.createByErrorMessage("用户名不存在");
	        }
	        // // TODO: 密码登陆MD5
	        String md5Password = MD5Util.MD5EncodeUtf8(password);
	        User user = userMapper.selectLogin(username,md5Password);
	        if(user == null){
	            return ServerResponse.createByErrorMessage("密码错误");
	        }
	        //查看是否激活
	        
	       /* if(user.getState()!=1){
	        	 return ServerResponse.createByErrorMessage("该用户未激活");
	        }*/
	        //User user1 = userMapper.select();
	        //将密码置空，不给客户端
	        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
	        return ServerResponse.createBySuccess("登陆成功",user);
	}

	public ServerResponse<String> register(User user) {
		// TODO Auto-generated method stub
		//校验用户是否存在
		ServerResponse<String> validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
		
		if(!validResponse.isSuccess()){
			return validResponse;
		}
		//检验邮箱
		validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
		if(!validResponse.isSuccess()){
			return validResponse;
		}
		user.setRole(Const.Role.ROLE_CUSTOMER);
		
		//激活状态设置为未激活
		user.setState(1);
		//设置激活码
		String activecode = this.generateActiveCode();
		user.setActivecode(activecode);
		//MD5加密
		user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
		
		int resultCount = userMapper.insert(user);
		//邮箱激活
		//sendMail(user.getUsername(),user.getEmail(),activecode);
		if(resultCount==0){
			return ServerResponse.createByErrorMessage("注册失败");
		}
		return ServerResponse.createBySuccessMessage("注册成功");
	}
	
	 private String generateActiveCode(){
		 	String currentTime =String.valueOf(System.currentTimeMillis());
	        return currentTime+new Random().nextInt(100);
	    }
	
	private void sendMail(String username, String email, String activeCode) {
		// TODO Auto-generated method stub
	    String subject = "";
        String text  = "";
        MailUtil.sendMail(email,subject,text);

	}

	public ServerResponse<String> checkValid(String str,String type){
		if(org.apache.commons.lang3.StringUtils.isNotBlank(type)){
			//开始校验
			if(Const.USERNAME.equals(type)){
				int resultCount = userMapper.checkUsername(str);
				if(resultCount>0){
					return ServerResponse.createByErrorMessage("用户名已存在");
				}
			}
			if(Const.EMAIL.equals(type)){
				int resultCount =userMapper.checkEmail(str);
				if(resultCount>0){
					return ServerResponse.createByErrorMessage("邮箱已存在");
				}
			}
		}else{
			return ServerResponse.createByErrorMessage("参数错误");
		}
		return ServerResponse.createBySuccessMessage("校验成功");
		
	}

	public ServerResponse<String> selectQuestion(String username) {
		// TODO Auto-generated method stub
		ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
		if(validResponse.isSuccess()){
			//用户不存在
			return ServerResponse.createByErrorMessage("用户不存在");
		}
		String question=userMapper.selectQuestionByUsername(username);
		if(org.apache.commons.lang3.StringUtils.isNotBlank(question)){
			return ServerResponse.createBySuccess(question);
		}
		
		return ServerResponse.createByErrorMessage("找回密码的问题是空的");
	}

	public ServerResponse<String> checkAnswer(String username, String question, String answer) {
		// TODO Auto-generated method stub
		
		int resultCount = userMapper.checkAnswer(username,question,answer);
		if(resultCount>0){
			String forgetToken = UUID.randomUUID().toString();
			TokenCache.setKey(TokenCache.TOKEN_PREFIX+username, forgetToken);
			return ServerResponse.createBySuccess(forgetToken);
		}
		return ServerResponse.createByErrorMessage("问题答案错误");
	}

	public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
		// TODO Auto-generated method stub
		
		if(org.apache.commons.lang3.StringUtils.isBlank(forgetToken)){
			return ServerResponse.createByErrorMessage("参数错误，token需要传递");
		}
		ServerResponse validResponse=this.checkValid(username, Const.USERNAME);
		if(validResponse.isSuccess()){
			//用户不存在
			return ServerResponse.createByErrorMessage("用户不存在");
		}
		String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX+username);
		if(org.apache.commons.lang3.StringUtils.isBlank(token)){
			return ServerResponse.createByErrorMessage("token无效或者过期");
		}
		if(org.apache.commons.lang3.StringUtils.equals(forgetToken, token)){
			String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
			int rowCount = userMapper.updatePasswordByUsername(username,md5Password);
			
			if(rowCount!=0){
	              return ServerResponse.createBySuccessMessage("修改密码成功");
	         }
		}else{
			 return ServerResponse.createByErrorMessage("token错误,请重新获取重置密码的token");
		}
		return ServerResponse.createByErrorMessage("修改密码失败");
	}

	public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
		 //防止横向越权，要校验一下这个用户的旧密码，一定要指定是这个用户，因为我们会查询一个
        //count(1)，如果不指定id，那么结果就是ture了，count>0
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("旧密码错误");
        }

        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if(updateCount != 0){
            return ServerResponse.createBySuccessMessage("密码更新成功");
        }
        return ServerResponse.createByErrorMessage("密码更新失败");
	}
	

    public ServerResponse<User> updateInformation(User user){
        //username是不能被更新的
        //email也要进行一个校验，检验新的email是不是已经存在，并且存在的email如果相同的话，不能是我们当前这个用户的。
       /* int resultCount = userMapper.checkEmailByUserId(user.getEmail(),user.getId());
        if(resultCount > 0){
            return ServerResponse.createByErrorMessage("email已存在，请更换email再尝试更新");
        }*/
        User updateUser= new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        updateUser.setCreateTime(user.getCreateTime());
        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(updateCount != 0){
            return ServerResponse.createBySuccess("更新个人信息成功",updateUser);

        }
        System.out.println("updateCount"+updateCount);
        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }
    
    public ServerResponse<User> getInformation(Integer userId){
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null){
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);

    }
	
  //backend

    public ServerResponse<String> checkAdminRole(User user){
        if(user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

	@Override
	public ServerResponse<String> activeCode(String username, String activeCode) {
		// TODO Auto-generated method stub
		
		int resultCount= userMapper.activeCode(username,activeCode);
		if(resultCount!=0){
			return ServerResponse.createBySuccessMessage("激活成功");
		}
		return ServerResponse.createBySuccessMessage("未激活");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> allUserMSGList() {
		// TODO Auto-generated method stub
		return  userMapper.selectByExample(null);
	}

	@Override
	public User selectByPrimaryKey(Integer userId) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(userId);
	}

	@Override
	public void deleteCommonUser(Integer id) {
		// TODO Auto-generated method stub
		 userMapper.deleteByPrimaryKey(id);
	}

	
}
