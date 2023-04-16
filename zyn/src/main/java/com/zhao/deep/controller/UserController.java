package com.zhao.deep.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhao.deep.bean.Category;
import com.zhao.deep.bean.User;
import com.zhao.deep.common.Const;
import com.zhao.deep.common.ResponseCode;
import com.zhao.deep.common.ServerResponse;
import com.zhao.deep.service.IUserService;

@Controller
@RequestMapping("/user/")
public class UserController {

	@Autowired
	private IUserService iUserService;
	
	/*
	 * �û���¼
	 */
	@RequestMapping(value = "login",method = RequestMethod.POST)
	/*@ResponseBody*/
	    // @ResponseBody������ �ڷ��ص�ʱ���Զ�ͨ��springmvc��Jackson���������ֵ���л���json
	/*public ServerResponse<User> login(String username, String password, HttpSession session,Model model){*/
	public String login(String username, String password, HttpSession session,Model model){
        ServerResponse<User> response = iUserService.login(username, password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
            model.addAttribute("Response", response);
            return "redirect:/user/get_user_info";
        }
        model.addAttribute("message", response.getMsg());
        return "error";
      /*  return response;*/
    }
	/*
	 * �˳���½
	 */
	 @RequestMapping(value="logout",method= RequestMethod.POST)
	 @ResponseBody
	 public ServerResponse<String> logout(HttpSession session){
	        session.removeAttribute(Const.CURRENT_USER);
	        return ServerResponse.createBySuccess();
	 }
	 
	 /*
	  * ע��
	  */
	 @RequestMapping(value="register",method= RequestMethod.POST)
	 @ResponseBody
	 public ServerResponse<String> register(User user){
		
		 return iUserService.register(user);
	 }
	 
	 /*�����뼤��*/
	 @RequestMapping(value="toActiveCount",method= RequestMethod.GET)
	 @ResponseBody
	 public ServerResponse<String> activeCode(String username, String activeCode){
		
		 return iUserService.activeCode(username,activeCode);
	 }
	 
	 
	 /*
	  * У��
	  */
	 @RequestMapping(value="check_valid",method= RequestMethod.POST)
	 @ResponseBody
	 public ServerResponse<String> checkValid(String str,String type){
		 return iUserService.checkValid(str,type);
	 }
	 
	 /*
	  * ��ȡ�û���Ϣ
	  */
	 @RequestMapping(value="get_user_info",method= RequestMethod.GET)
	/* @ResponseBody
	 public ServerResponse<User> getUserInfo(HttpSession session,Model model){*/
		 public String getUserInfo(HttpSession session,Model model){
		 User user = (User) session.getAttribute(Const.CURRENT_USER);
		 if(user!=null){
			 model.addAttribute("userMessage", user);
			 return "index";
			/* return ServerResponse.createBySuccess(user);*/
		 }
		 /*return ServerResponse.createByErrorMessage("�û�δ��½���޷���ȡ��ǰ�û���Ϣ");*/
		 model.addAttribute("message", "�û�δ��½���޷���ȡ��ǰ�û���Ϣ");
		 return "error";
	 }
	 
	 /*
	  * 
	  */
	 @RequestMapping(value="forget_get_question",method= RequestMethod.POST)
	 /* @ResponseBody
	 public ServerResponse<String> forgetGetQuestion(String username,Model model){*/
	 public String forgetGetQuestion(String username,Model model,ModelMap modelMap){
		 ServerResponse<String> response = iUserService.selectQuestion(username);
		 model.addAttribute("question", response);
		 modelMap.addAttribute("username", username);
		 return "lostPasswordReset/lostPasswordReset2";
		/* return iUserService.selectQuestion(username);*/
	 }
	 
	 @RequestMapping(value="forget_check_answer",method= RequestMethod.POST)
	 /*@ResponseBody
	 public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer,Model model,ModelMap modelMap){*/
		 public String forgetCheckAnswer(String username,String question,String answer,Model model,ModelMap modelMap){
		 ServerResponse<String> response=iUserService.checkAnswer(username,question,answer);
		 model.addAttribute("forgetToken", response);
		 modelMap.addAttribute("username", username);
		 return "lostPasswordReset/lostPasswordReset3";
		 /*return iUserService.checkAnswer(username,question,answer);*/
	 }
	 
	 //���������е���������
	 @RequestMapping(value="forget_reset_password",method= RequestMethod.POST)
	 @ResponseBody
	 public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
		 return iUserService.forgetResetPassword(username,passwordNew,forgetToken);
	 }
	 
	 @RequestMapping(value="toResetPassWord",method= RequestMethod.GET)
	 public String toResetPassWord(HttpSession session,Model model){
		 User user=(User)session.getAttribute(Const.CURRENT_USER);
	      if(user == null){
	         model.addAttribute("message", "�û�δ��½��");
	         return "error";
	      }
	      model.addAttribute("userMsg",user);
		 return "lostPasswordReset/resetPwd";
	 }
	 
	 
	//��¼״̬����������
	  @RequestMapping(value="reset_password",method= RequestMethod.POST)
	  @ResponseBody
	  public ServerResponse<String> resetPassword(HttpSession session,String passwordOld,String passwordNew){
	      User user=(User)session.getAttribute(Const.CURRENT_USER);
	      if(user == null){
	          return ServerResponse.createByErrorMessage("�û�δ��½");
	      }
	      return iUserService.resetPassword(passwordOld,passwordNew,user);
	  }
	  

	    @RequestMapping(value="update_information",method= RequestMethod.POST)
	    @ResponseBody
	   /* public ServerResponse<User> update_information(HttpSession session,User user){*/
	    public ServerResponse<User> update_information(HttpSession session, HttpServletRequest request,
	    		@RequestParam String email,
	    		@RequestParam String phone,
	    		@RequestParam String question,
	    		@RequestParam String answer
	    		){
	        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
	        if(currentUser==null){
	            return ServerResponse.createByErrorMessage("�û�δ��½");
	        }
	        User user = new User();
	        user.setId(currentUser.getId());
	        user.setUsername(currentUser.getUsername());
	        user.setEmail(String.valueOf(request.getParameter("email")));
	        user.setPhone(request.getParameter("phone"));
	        user.setQuestion(request.getParameter("question"));
	        user.setAnswer(request.getParameter("answer"));
	        user.setCreateTime(currentUser.getCreateTime());
	        ServerResponse<User> response = iUserService.updateInformation(user);
	        if(response.isSuccess()){
	            response.getData().setUsername(currentUser.getUsername());
	            session.setAttribute(Const.CURRENT_USER,response.getData());
	        }
	        return response;
	    }
	    

	    @RequestMapping(value = "get_information",method=RequestMethod.GET)
	   /* @ResponseBody
	    public ServerResponse<User> get_information(HttpSession session) {*/
	    public String get_information(HttpSession session,Model model) {
	        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
	        if (currentUser == null) {
	           /* return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "δ��¼,��Ҫǿ�Ƶ�¼status=10");*/
	        	model.addAttribute("message", "δ��¼,��Ҫǿ�Ƶ�¼");
	        	return "error";
	        }
	        ServerResponse<User> response=iUserService.getInformation(currentUser.getId());
	        model.addAttribute("response", response);
	       /* return iUserService.getInformation(currentUser.getId());*/
			return "ownMessage";
	    }
	    
	    @RequestMapping(value = "tologin",method = RequestMethod.POST)
	    public String toLogin(){
	    	return "login";
	    }
	    
	    //����Ա��ѯ�����û���Ϣ
	    @RequestMapping(value="getAllUserMSGList",method=RequestMethod.GET)
	    @ResponseBody
	     public  Map<Object, Object> allUserMSGList(HttpSession session){
	    	Map<Object, Object> data = new HashMap<>();
	    	User user = (User)session.getAttribute(Const.CURRENT_USER);
	        if(user == null){
	        	data.put("data", "�û���δ��½");
	        	return data;
	        }
	        if(iUserService.checkAdminRole(user).isSuccess()){
	        	
	        	List<User> uList = iUserService.allUserMSGList();
	        	data.put("data", uList);
	        	return data;
	           // return (List<User>) iUserService.allUserMSGList();
	        }else{
	        	data.put("data", "��Ȩ�޲���,��Ҫ����ԱȨ��");
	        	return data;
	        }
	       
	       
	    }
	    
	    /*
		 * �����û��Ƿ��¼
		 */
		@RequestMapping(value = "checkUserLogin",method = RequestMethod.POST)
		@ResponseBody
		public ServerResponse<User> checkUserLogin( HttpSession session){
			 //�˷�����������Ƿ��½
	        	User user = (User)session.getAttribute(Const.CURRENT_USER);
	        	if(user == null){
	        		 return ServerResponse.createByErrorCodeMessage(1, "�û���δ��½");
	        	}
	            return ServerResponse.createBySuccess(user);
	    
	    }

		 /*
		 * �û���¼
		 */
		@RequestMapping(value = "customerLogin",method = RequestMethod.POST)
		@ResponseBody
		public ServerResponse<User> userLogin(String username, String password, HttpSession session){
			
			ServerResponse<User> response = iUserService.login(username, password);
	        if(response.isSuccess()){
	            session.setAttribute(Const.CURRENT_USER,response.getData());
	            return ServerResponse.createBySuccess(response.getData());
	        }
	        return ServerResponse.createByErrorMessage(response.getMsg());
	     
	    }
		
		

		 /*
		 * ע���û�
		 */
		@RequestMapping(value = "deleteUser",method = RequestMethod.POST)
		@ResponseBody
		public ServerResponse<User> deleteUser(Integer id, HttpSession session){
			User user = (User)session.getAttribute(Const.CURRENT_USER);
	        if(user == null){
	        	return ServerResponse.createByErrorMessage("�û���δ��½");
	        }
	        if(iUserService.checkAdminRole(user).isSuccess()){
	        	iUserService.deleteCommonUser(id);
	        	/*if(response.getStatus() == 0){*/
	        		return ServerResponse.createBySuccessMessage("���û���ɾ��");
	        	/*}
	        	return ServerResponse.createByErrorMessage("�û�ɾ��ʧ��");*/
	        }else{
	        	return ServerResponse.createByErrorMessage("��Ȩ�޲���,��Ҫ����ԱȨ��");
	        }
	     
	    }
		
		/*
		  * ע��
		  */
		 @RequestMapping(value="custRegister",method= RequestMethod.POST)
		 @ResponseBody
		 public ServerResponse<String> custRegister(String username, String password,
				 									String email, String phone){
			 User user = new User();
			 user.setUsername(username);
			 user.setPassword(password);
			 user.setEmail(email);
			 user.setPhone(phone);
			
			 return iUserService.register(user);
		 }
		
}
