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
	 * 用户登录
	 */
	@RequestMapping(value = "login",method = RequestMethod.POST)
	/*@ResponseBody*/
	    // @ResponseBody作用是 在返回的时候自动通过springmvc的Jackson插件将返回值序列化成json
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
	 * 退出登陆
	 */
	 @RequestMapping(value="logout",method= RequestMethod.POST)
	 @ResponseBody
	 public ServerResponse<String> logout(HttpSession session){
	        session.removeAttribute(Const.CURRENT_USER);
	        return ServerResponse.createBySuccess();
	 }
	 
	 /*
	  * 注册
	  */
	 @RequestMapping(value="register",method= RequestMethod.POST)
	 @ResponseBody
	 public ServerResponse<String> register(User user){
		
		 return iUserService.register(user);
	 }
	 
	 /*激活码激活*/
	 @RequestMapping(value="toActiveCount",method= RequestMethod.GET)
	 @ResponseBody
	 public ServerResponse<String> activeCode(String username, String activeCode){
		
		 return iUserService.activeCode(username,activeCode);
	 }
	 
	 
	 /*
	  * 校验
	  */
	 @RequestMapping(value="check_valid",method= RequestMethod.POST)
	 @ResponseBody
	 public ServerResponse<String> checkValid(String str,String type){
		 return iUserService.checkValid(str,type);
	 }
	 
	 /*
	  * 获取用户信息
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
		 /*return ServerResponse.createByErrorMessage("用户未登陆，无法获取当前用户信息");*/
		 model.addAttribute("message", "用户未登陆，无法获取当前用户信息");
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
	 
	 //忘记密码中的重置密码
	 @RequestMapping(value="forget_reset_password",method= RequestMethod.POST)
	 @ResponseBody
	 public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
		 return iUserService.forgetResetPassword(username,passwordNew,forgetToken);
	 }
	 
	 @RequestMapping(value="toResetPassWord",method= RequestMethod.GET)
	 public String toResetPassWord(HttpSession session,Model model){
		 User user=(User)session.getAttribute(Const.CURRENT_USER);
	      if(user == null){
	         model.addAttribute("message", "用户未登陆！");
	         return "error";
	      }
	      model.addAttribute("userMsg",user);
		 return "lostPasswordReset/resetPwd";
	 }
	 
	 
	//登录状态的重置密码
	  @RequestMapping(value="reset_password",method= RequestMethod.POST)
	  @ResponseBody
	  public ServerResponse<String> resetPassword(HttpSession session,String passwordOld,String passwordNew){
	      User user=(User)session.getAttribute(Const.CURRENT_USER);
	      if(user == null){
	          return ServerResponse.createByErrorMessage("用户未登陆");
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
	            return ServerResponse.createByErrorMessage("用户未登陆");
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
	           /* return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录,需要强制登录status=10");*/
	        	model.addAttribute("message", "未登录,需要强制登录");
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
	    
	    //管理员查询所有用户信息
	    @RequestMapping(value="getAllUserMSGList",method=RequestMethod.GET)
	    @ResponseBody
	     public  Map<Object, Object> allUserMSGList(HttpSession session){
	    	Map<Object, Object> data = new HashMap<>();
	    	User user = (User)session.getAttribute(Const.CURRENT_USER);
	        if(user == null){
	        	data.put("data", "用户尚未登陆");
	        	return data;
	        }
	        if(iUserService.checkAdminRole(user).isSuccess()){
	        	
	        	List<User> uList = iUserService.allUserMSGList();
	        	data.put("data", uList);
	        	return data;
	           // return (List<User>) iUserService.allUserMSGList();
	        }else{
	        	data.put("data", "无权限操作,需要管理员权限");
	        	return data;
	        }
	       
	       
	    }
	    
	    /*
		 * 检验用户是否登录
		 */
		@RequestMapping(value = "checkUserLogin",method = RequestMethod.POST)
		@ResponseBody
		public ServerResponse<User> checkUserLogin( HttpSession session){
			 //此方法用来检查是否登陆
	        	User user = (User)session.getAttribute(Const.CURRENT_USER);
	        	if(user == null){
	        		 return ServerResponse.createByErrorCodeMessage(1, "用户尚未登陆");
	        	}
	            return ServerResponse.createBySuccess(user);
	    
	    }

		 /*
		 * 用户登录
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
		 * 注销用户
		 */
		@RequestMapping(value = "deleteUser",method = RequestMethod.POST)
		@ResponseBody
		public ServerResponse<User> deleteUser(Integer id, HttpSession session){
			User user = (User)session.getAttribute(Const.CURRENT_USER);
	        if(user == null){
	        	return ServerResponse.createByErrorMessage("用户尚未登陆");
	        }
	        if(iUserService.checkAdminRole(user).isSuccess()){
	        	iUserService.deleteCommonUser(id);
	        	/*if(response.getStatus() == 0){*/
	        		return ServerResponse.createBySuccessMessage("该用户已删除");
	        	/*}
	        	return ServerResponse.createByErrorMessage("用户删除失败");*/
	        }else{
	        	return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
	        }
	     
	    }
		
		/*
		  * 注册
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
