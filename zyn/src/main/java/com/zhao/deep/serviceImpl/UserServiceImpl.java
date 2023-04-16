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
	            return ServerResponse.createByErrorMessage("�û���������");
	        }
	        // // TODO: �����½MD5
	        String md5Password = MD5Util.MD5EncodeUtf8(password);
	        User user = userMapper.selectLogin(username,md5Password);
	        if(user == null){
	            return ServerResponse.createByErrorMessage("�������");
	        }
	        //�鿴�Ƿ񼤻�
	        
	       /* if(user.getState()!=1){
	        	 return ServerResponse.createByErrorMessage("���û�δ����");
	        }*/
	        //User user1 = userMapper.select();
	        //�������ÿգ������ͻ���
	        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
	        return ServerResponse.createBySuccess("��½�ɹ�",user);
	}

	public ServerResponse<String> register(User user) {
		// TODO Auto-generated method stub
		//У���û��Ƿ����
		ServerResponse<String> validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
		
		if(!validResponse.isSuccess()){
			return validResponse;
		}
		//��������
		validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
		if(!validResponse.isSuccess()){
			return validResponse;
		}
		user.setRole(Const.Role.ROLE_CUSTOMER);
		
		//����״̬����Ϊδ����
		user.setState(1);
		//���ü�����
		String activecode = this.generateActiveCode();
		user.setActivecode(activecode);
		//MD5����
		user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
		
		int resultCount = userMapper.insert(user);
		//���伤��
		//sendMail(user.getUsername(),user.getEmail(),activecode);
		if(resultCount==0){
			return ServerResponse.createByErrorMessage("ע��ʧ��");
		}
		return ServerResponse.createBySuccessMessage("ע��ɹ�");
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
			//��ʼУ��
			if(Const.USERNAME.equals(type)){
				int resultCount = userMapper.checkUsername(str);
				if(resultCount>0){
					return ServerResponse.createByErrorMessage("�û����Ѵ���");
				}
			}
			if(Const.EMAIL.equals(type)){
				int resultCount =userMapper.checkEmail(str);
				if(resultCount>0){
					return ServerResponse.createByErrorMessage("�����Ѵ���");
				}
			}
		}else{
			return ServerResponse.createByErrorMessage("��������");
		}
		return ServerResponse.createBySuccessMessage("У��ɹ�");
		
	}

	public ServerResponse<String> selectQuestion(String username) {
		// TODO Auto-generated method stub
		ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
		if(validResponse.isSuccess()){
			//�û�������
			return ServerResponse.createByErrorMessage("�û�������");
		}
		String question=userMapper.selectQuestionByUsername(username);
		if(org.apache.commons.lang3.StringUtils.isNotBlank(question)){
			return ServerResponse.createBySuccess(question);
		}
		
		return ServerResponse.createByErrorMessage("�һ�����������ǿյ�");
	}

	public ServerResponse<String> checkAnswer(String username, String question, String answer) {
		// TODO Auto-generated method stub
		
		int resultCount = userMapper.checkAnswer(username,question,answer);
		if(resultCount>0){
			String forgetToken = UUID.randomUUID().toString();
			TokenCache.setKey(TokenCache.TOKEN_PREFIX+username, forgetToken);
			return ServerResponse.createBySuccess(forgetToken);
		}
		return ServerResponse.createByErrorMessage("����𰸴���");
	}

	public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
		// TODO Auto-generated method stub
		
		if(org.apache.commons.lang3.StringUtils.isBlank(forgetToken)){
			return ServerResponse.createByErrorMessage("��������token��Ҫ����");
		}
		ServerResponse validResponse=this.checkValid(username, Const.USERNAME);
		if(validResponse.isSuccess()){
			//�û�������
			return ServerResponse.createByErrorMessage("�û�������");
		}
		String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX+username);
		if(org.apache.commons.lang3.StringUtils.isBlank(token)){
			return ServerResponse.createByErrorMessage("token��Ч���߹���");
		}
		if(org.apache.commons.lang3.StringUtils.equals(forgetToken, token)){
			String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
			int rowCount = userMapper.updatePasswordByUsername(username,md5Password);
			
			if(rowCount!=0){
	              return ServerResponse.createBySuccessMessage("�޸�����ɹ�");
	         }
		}else{
			 return ServerResponse.createByErrorMessage("token����,�����»�ȡ���������token");
		}
		return ServerResponse.createByErrorMessage("�޸�����ʧ��");
	}

	public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
		 //��ֹ����ԽȨ��ҪУ��һ������û��ľ����룬һ��Ҫָ��������û�����Ϊ���ǻ��ѯһ��
        //count(1)�������ָ��id����ô�������ture�ˣ�count>0
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("���������");
        }

        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if(updateCount != 0){
            return ServerResponse.createBySuccessMessage("������³ɹ�");
        }
        return ServerResponse.createByErrorMessage("�������ʧ��");
	}
	

    public ServerResponse<User> updateInformation(User user){
        //username�ǲ��ܱ����µ�
        //emailҲҪ����һ��У�飬�����µ�email�ǲ����Ѿ����ڣ����Ҵ��ڵ�email�����ͬ�Ļ������������ǵ�ǰ����û��ġ�
       /* int resultCount = userMapper.checkEmailByUserId(user.getEmail(),user.getId());
        if(resultCount > 0){
            return ServerResponse.createByErrorMessage("email�Ѵ��ڣ������email�ٳ��Ը���");
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
            return ServerResponse.createBySuccess("���¸�����Ϣ�ɹ�",updateUser);

        }
        System.out.println("updateCount"+updateCount);
        return ServerResponse.createByErrorMessage("���¸�����Ϣʧ��");
    }
    
    public ServerResponse<User> getInformation(Integer userId){
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null){
            return ServerResponse.createByErrorMessage("�Ҳ�����ǰ�û�");
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
			return ServerResponse.createBySuccessMessage("����ɹ�");
		}
		return ServerResponse.createBySuccessMessage("δ����");
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
