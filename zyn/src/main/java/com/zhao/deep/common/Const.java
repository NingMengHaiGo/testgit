package com.zhao.deep.common;

public class Const {

	 public static final String CURRENT_USER="current_user";

	    public static final String EMAIL="email";
	    public static final String USERNAME="username";
	    public interface Role{
	        int ROLE_CUSTOMER = 0;//��ͨ�û�
	        int ROLE_ADMIN = 1;//����Ա
	    }
	    
	    public interface Cart{
	        int CHECKED = 1;//�����ﳵѡ��״̬
	        int UN_CHECKED = 0;//���ﳵ��δѡ��״̬

	        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
	        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
	    }
}
