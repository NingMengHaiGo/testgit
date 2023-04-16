package com.zhao.deep.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhao.deep.bean.Category;
import com.zhao.deep.bean.User;
import com.zhao.deep.common.Const;
import com.zhao.deep.common.ResponseCode;
import com.zhao.deep.common.ServerResponse;
import com.zhao.deep.service.ICategoryService;
import com.zhao.deep.service.IUserService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/category/")
public class CategoryManageController {


    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;
    
       /*列出商品类，即商品的根节点*/
    @RequestMapping("list")
    @ResponseBody
    public ServerResponse listCategory(HttpSession session){
    	User user =(User) session.getAttribute(Const.CURRENT_USER);
    	if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆，请登陆");
        }
    	
		return iCategoryService.listCategory();	
    }
    
    

    @RequestMapping(value="add_category",method=RequestMethod.POST)
    @ResponseBody
    public ServerResponse addCategory(HttpSession session,String categoryName,@RequestParam(value = "parentId",defaultValue = "0") int parentId){

    	System.out.println("categoryName"+categoryName);
    	System.out.println("parentId"+parentId);
        User user= (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆，请登陆");
        }
        //校验一下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //是管理员
            //增加我们处理分类的逻辑
            return iCategoryService.addCategory(categoryName,parentId);

        }else{
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }

    }

    @RequestMapping("set_category_name")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session,Integer categoryId,String categoryName) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆，请登陆");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //更新categoryName
            return iCategoryService.updateCategoryName(categoryId,categoryName);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }

    }

    @RequestMapping(value="get_category",method=RequestMethod.GET)
    public String getChildrenParallelCategory(HttpSession session,Model model,@RequestParam(value = "categoryId" ,defaultValue = "0") 
    Integer categoryId){
    	
    	User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
        	model.addAttribute("message", "用户未登陆，无法获取当前用户信息");
			 return "error";
        }
        
	    if(iUserService.checkAdminRole(user).isSuccess()){
	            //查询子节点的category信息,并且不递归,保持平级
	        ServerResponse<List<Category>> Categorylist = iCategoryService.getChildrenParallelCategory(categoryId);
	        		//System.out.println("..............................."+Categorylist.getData().get(0).getId());
	        		System.out.println("...............................");
	        		/*Boolean b= Categorylist.getData().isEmpty();
	        		System.out.println(b);*/
		    if(Categorylist.getData().isEmpty()){
		    	model.addAttribute("message", "暂无任何商品分类");
	        	return "error";
			 		
		     }else
		     	{
		         int categoryid=Categorylist.getData().get(0).getParentId();
		        /* int categoryid2=Categorylist.getData().get(0).getId();
		         System.out.println("..............................."+categoryid);*/
		     
			     model.addAttribute("categoryId", categoryid);
			   
			     model.addAttribute("categoryMsg", Categorylist.getData());
			     return "category/listCategory";
		        }
	       }else
	       		{
		        	model.addAttribute("message", "无权限操作,需要管理员权限");
		            return "error";
	        	}
    
    }
    
    
     @RequestMapping(value="get_category2",method=RequestMethod.GET)
     @ResponseBody
     public ServerResponse<?> getChildrenParallelCategory(HttpSession session,@RequestParam(value = "categoryId" ,defaultValue = "0") 
     Integer categoryId){
    	
    	User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
        	 return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆，请登陆");
        }
       /* System.out.println("....................+++++"+categoryId);
        System.out.println("....................+++++"+parentId);*/
       
        return  iCategoryService.getChildrenParallelCategory(categoryId);
       
    }
    

    
    @RequestMapping("get_deep_category")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId" ,defaultValue = "0")
    Integer categoryId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            //查询当前节点的id和递归子节点的id
//            0->10000->100000
            return iCategoryService.selectCategoryAndChildrenById(categoryId);

        }else{
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }
    
    /*新增客户端通过二级分类查询三级分类*/
	 @RequestMapping(value="listSelProByCid",method =RequestMethod.GET)
	 @ResponseBody
	 public Map<Object, Object> listSelProByCid(Integer id){
		List<Category> categoryList = iCategoryService.getCateBySecCate(id);
		Map<Object, Object> data = new HashMap<>();
		data.put("data", categoryList);
		return data;
		//return ServerResponse.createBySuccess(categoryList);
		
	 }
	
	
    
}
