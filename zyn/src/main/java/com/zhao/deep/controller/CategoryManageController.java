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
    
       /*�г���Ʒ�࣬����Ʒ�ĸ��ڵ�*/
    @RequestMapping("list")
    @ResponseBody
    public ServerResponse listCategory(HttpSession session){
    	User user =(User) session.getAttribute(Const.CURRENT_USER);
    	if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"�û�δ��½�����½");
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
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"�û�δ��½�����½");
        }
        //У��һ���Ƿ��ǹ���Ա
        if(iUserService.checkAdminRole(user).isSuccess()){
            //�ǹ���Ա
            //�������Ǵ��������߼�
            return iCategoryService.addCategory(categoryName,parentId);

        }else{
            return ServerResponse.createByErrorMessage("��Ȩ�޲���,��Ҫ����ԱȨ��");
        }

    }

    @RequestMapping("set_category_name")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session,Integer categoryId,String categoryName) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "�û�δ��½�����½");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //����categoryName
            return iCategoryService.updateCategoryName(categoryId,categoryName);
        } else {
            return ServerResponse.createByErrorMessage("��Ȩ�޲���,��Ҫ����ԱȨ��");
        }

    }

    @RequestMapping(value="get_category",method=RequestMethod.GET)
    public String getChildrenParallelCategory(HttpSession session,Model model,@RequestParam(value = "categoryId" ,defaultValue = "0") 
    Integer categoryId){
    	
    	User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
        	model.addAttribute("message", "�û�δ��½���޷���ȡ��ǰ�û���Ϣ");
			 return "error";
        }
        
	    if(iUserService.checkAdminRole(user).isSuccess()){
	            //��ѯ�ӽڵ��category��Ϣ,���Ҳ��ݹ�,����ƽ��
	        ServerResponse<List<Category>> Categorylist = iCategoryService.getChildrenParallelCategory(categoryId);
	        		//System.out.println("..............................."+Categorylist.getData().get(0).getId());
	        		System.out.println("...............................");
	        		/*Boolean b= Categorylist.getData().isEmpty();
	        		System.out.println(b);*/
		    if(Categorylist.getData().isEmpty()){
		    	model.addAttribute("message", "�����κ���Ʒ����");
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
		        	model.addAttribute("message", "��Ȩ�޲���,��Ҫ����ԱȨ��");
		            return "error";
	        	}
    
    }
    
    
     @RequestMapping(value="get_category2",method=RequestMethod.GET)
     @ResponseBody
     public ServerResponse<?> getChildrenParallelCategory(HttpSession session,@RequestParam(value = "categoryId" ,defaultValue = "0") 
     Integer categoryId){
    	
    	User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
        	 return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "�û�δ��½�����½");
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
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"�û�δ��¼,���¼");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            //��ѯ��ǰ�ڵ��id�͵ݹ��ӽڵ��id
//            0->10000->100000
            return iCategoryService.selectCategoryAndChildrenById(categoryId);

        }else{
            return ServerResponse.createByErrorMessage("��Ȩ�޲���,��Ҫ����ԱȨ��");
        }
    }
    
    /*�����ͻ���ͨ�����������ѯ��������*/
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
