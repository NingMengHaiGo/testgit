package com.zhao.deep.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.zhao.deep.bean.Category;
import com.zhao.deep.bean.Product;
import com.zhao.deep.bean.ProductWithBLOBs;
import com.zhao.deep.bean.User;
import com.zhao.deep.common.Const;
import com.zhao.deep.common.ResponseCode;
import com.zhao.deep.common.ServerResponse;
import com.zhao.deep.service.ICartService;
import com.zhao.deep.service.ICategoryService;
import com.zhao.deep.service.IProductService;
import com.zhao.deep.service.IUserService;
import com.zhao.deep.utils.ConfigReader;
import com.zhao.deep.utils.EncodingUtil;
import com.zhao.deep.utils.UploadUtils;

import vo.productUserVo;

@Controller
/*@RequestMapping("/user/")*/
public class ProductManagerController {
	
	 @Autowired
	 private IUserService iUserService;
	
	 @Autowired
	 private IProductService iProductService;
	 
	 @Autowired
	 private ICategoryService iCategoryService;
	 
	 @Autowired
	 private ICartService iCartService;
	 
	 
	 /*通过类别查询*/
	 @RequestMapping(value="/user/category/showProducts",method =RequestMethod.GET)
	 @ResponseBody
	 public List<Product> selectByCid(Integer cid,Model model){
		 
		return iProductService.selectProByCid(cid);
		
	 }
	 
	 /*查询出商品类别*/
	 @RequestMapping("/user/select")
	 public String getAllCategory(HttpSession session,Model model){
		 
		 User user = (User)session.getAttribute(Const.CURRENT_USER);
	        if(user == null){
	        	model.addAttribute("message", "用户未登陆，无法获取当前用户信息");
				 return "error";
	        }
	         ServerResponse categorylist = iCategoryService.listCategory();
	         List category = (List) categorylist.getData();
	        model.addAttribute("listCategory",category);
		return "product/productsadd";
	 }
	 
	 /*模糊查询的action*/
	 @RequestMapping(value="/user/selectByMists",method=RequestMethod.POST)
	 @ResponseBody
	 public ServerResponse selectByMists(HttpSession session,String subtitle,String username,String categorymisname){
		 User user = (User)session.getAttribute(Const.CURRENT_USER);
	        if(user == null){
	            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");

	        }
	        List prolist = new ArrayList<>();
	       
	        //通过模糊商品名或者通过模糊用户名查询
	        List<Product> productlist = iProductService.selectByMists(subtitle,username);
	        prolist = productlist;
	        //模糊类别名查询
	        if(categorymisname != null){
	        	List<Category> categorylist =iCategoryService.getCategoryListByMis(categorymisname);
	        	List productlist2 = new ArrayList<>();
//	        	for(int i = 0;i<productlist.size();i++){
	        	for(Product productitem : productlist){
	        		for(Category categoryitem : categorylist){
	        			if(categoryitem.getParentId() == productitem.getCategoryId()){
	 	  	    	    	productlist2.add(productitem);
	 	  	    	    }
	        		}
	        	}
	        	/* for(Category categoryitem : categorylist){
	  	    	    if(categoryitem.getParentId() == productlist.get(4)){
	  	    	    	productlist2.add(categoryitem);
	  	    	    }
	  	       }*/
	        	 prolist=productlist2;
	        }
	       
	        
	     	return ServerResponse.createBySuccess(prolist);
		 
	 }
	
	 /*
	  * 添加与更新
	  */
	 @RequestMapping("/user/save")
	 @ResponseBody
	 public ServerResponse<String> productSave(HttpSession session,HttpServletRequest request){
		 User user = (User)session.getAttribute(Const.CURRENT_USER);
	        if(user == null){
	            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");

	        }
	       String username = user.getUsername();
	       String userid = user.getId().toString();
	       if(iUserService.checkAdminRole(user).isSuccess()){
	            //填充增加产品的业务逻辑
	        	String path = ConfigReader.getValue("upload.basedir");
	    		UploadUtils uploadUtils = new UploadUtils();
	    		List resultInfo = uploadUtils.uploadFile(request, path);
	    		
	    		String imagePath = (String) resultInfo.get(4);
	    		//System.out.println("imagePath"+imagePath);
	    		Map<String, String> formField = (Map<String, String>) resultInfo.get(6);
	    		formField.put("userName", username);
	    		formField.put("userId", userid);
	    		List<String> nameList = (List<String>) resultInfo.get(7);
	    		Product product = setProduct(formField, imagePath, nameList);
	    		
	    		
	            return iProductService.saveOrUpdateProduct(product);
	    	
	        }else{
	            return ServerResponse.createByErrorMessage("无权限操作");
	        }
	}


	private Product setProduct(Map<String, String> formField, String imagePath, List<String> nameList) {
		// TODO Auto-generated method stub
		ProductWithBLOBs product = new ProductWithBLOBs();
		EncodingUtil.mapEncodeUtf8(formField);
		String categoryId1 = formField.get("categoryId1");
		String categoryId2 = formField.get("categoryId2");
		String categoryId = formField.get("categoryId");
		String name = formField.get("name");
		String subtitle = formField.get("subtitle");
		String price = formField.get("price");
		String stock = formField.get("stock");
		String id = formField.get("id");
		String userName = formField.get("userName");
	    String userId = formField.get("userId");
		
		
		product = setProductImage(formField, imagePath, nameList);
		
		product.setMainImage(product.getMainImage());
		product.setSubImages(product.getSubImage());
		product.setDetail(product.getDetail());
		if(categoryId1!=null)
		{
			product.setCategoryId(Integer.parseInt(categoryId1));
		}
		if(categoryId2!=null)
		{
			product.setCategoryId(Integer.parseInt(categoryId2));
		}
		if(categoryId!=null)
		{
			product.setCategoryId(Integer.parseInt(categoryId));
		}
		product.setName(name);
		product.setSubtitle(subtitle);
		BigDecimal bd=new BigDecimal(price);
		product.setPrice(bd);
		//product.setPrice(decimal.parsedecimal(price));
		/*BigDecimal bd=new BigDecimal(price);*/
/*		product.setPrice(new DecimalFormat("0.00").format(price));*/
		product.setStock(Integer.parseInt(stock));
		product.setStatus(1);
		product.setUserId(Integer.parseInt(userId));
		product.setUserName(userName);
		if(id!=null && id!="" && id.length()!=0){
			product.setId(Integer.parseInt(id));
		}
		
		
		
		//往分类表里面添加数据
		/*if(categoryId2!=null)
		{
			iCategoryService.addCategory(subtitle, Integer.parseInt(categoryId2));
		}*/
		/*if(categoryId1!=null)
		{
			iCategoryService.addCategory(subtitle, Integer.parseInt(categoryId1));
		}*/
		return product;
	}


	private ProductWithBLOBs setProductImage(Map<String, String> formField, String imagePath, List<String> nameList) {
		// TODO Auto-generated method stub
		ProductWithBLOBs product = new ProductWithBLOBs();
		String mainImage=formField.get("mainImage");
		String subImage=formField.get("subImage");
		String detail = formField.get("detail");
		if (null != nameList && nameList.size() > 0) {
			if (StringUtils.isBlank(mainImage)) {
				mainImage = imagePath + nameList.get(0);
			}
			
			if (StringUtils.isBlank(subImage)) {
				subImage = imagePath + nameList.get(1);
			}
			
			if (StringUtils.isBlank(detail)) {
				detail = imagePath + nameList.get(2);
			}
		}
		product.setMainImage(mainImage);
		product.setSubImage(subImage);
		product.setDetail(detail);
		return product;
	}
	
	/*
	 * 搜索
	 */
	
	
	/*
	 * 分页显示
	 */
	
	@RequestMapping("/user/getProductMessage")
	public String getProducts(@RequestParam(value = "pn", defaultValue = "1") Integer pn,
			HttpSession session,Model model,ModelMap modelMap){
			User user = (User) session.getAttribute(Const.CURRENT_USER);
			if(user==null){
				model.addAttribute("message", "用户未登陆，无法获取当前用户信息");
				 return "error";
			 }
			/*List<Product> product = iProductService.getAll();
			modelMap.addAttribute("productMsg", product);*/
				// 这不是一个分页查询
				// 引入PageHelper分页插件
				// 在查询之前只需要调用，传入页码，以及每页的大小
				PageHelper.startPage(pn, 5);
				// startPage后面紧跟的这个查询就是一个分页查询
				List<Product> products = iProductService.getAll();
				System.out.println("products:"+products.get(0).getUserName());
				//List<productUserVo> products = iProductService.getProductWithUser();
				//System.out.println("products:"+products.size());
				//System.out.println("testusermsg:"+products.get(0).getUsername());
				// 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
				// 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
				
				PageInfo<Product> page = new PageInfo<Product>(products,5);
				model.addAttribute("pageInfo",page);
		return "product/listProduct";
		
	}
	
	@RequestMapping("/user/deleteProduct")
	public String deleteProduct(HttpSession session,int id ,Model model){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if(user==null){
			model.addAttribute("message", "用户未登陆，无法获取当前用户信息");
			 return "error";
		 }
		iProductService.deleteProductById(id);
		iCartService.deleteProduct(user.getId(), String.valueOf(id));
		model.addAttribute("message", "商品删除成功，并且已在用户购物车中移除");
		return "error";
		
	}
	
	@RequestMapping(value="/user/toUpdateProduct",method=RequestMethod.GET)
	public String toUpdateProduct(int id,Model model){
		
		model.addAttribute("product",iProductService.selectProductById(id));
		return "product/updateProduct";
		
	}
	
	@RequestMapping("/user/listProductwithDetail")
	public String tolistProductDetail(int id,Model model,HttpSession session){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		Product product =(Product) iProductService.selectProductById(id);
		model.addAttribute("product",product);
		model.addAttribute("user",user);
		return "product/listProductDetail";
		
	}
	
	@RequestMapping("/user/toAddMsg")
	public String toAddMsg(){
		return "redirect:select.action";
	}
	
	@RequestMapping("/user/home")
	public String home(){
		return "home";
	}
	
	
	/*新增客户端查询所有商品*/
	 @RequestMapping(value="/user/listProductAll",method =RequestMethod.POST)
	 @ResponseBody
	 public ServerResponse<List<Product>> listProductAll(){
		 
		return ServerResponse.createBySuccess(iProductService.getProductAll());
		
	 }
	 
	 /*新增客户端通过类别查询*/
	 @RequestMapping(value="/user/category/getProductsByCid",method =RequestMethod.GET)
	 @ResponseBody
	 public Map<Object, Object> getProductsByCid(Integer cid){
		List<Product> pList = iProductService.selectProByCid(cid);
		Map<Object, Object> data = new HashMap<>();
		data.put("data", pList);
		return data;
		
	 }
	 
	 /*新增客户端管理员查询所有商品返回MAP*/
	 @RequestMapping(value="/user/listProductAllRMAP",method =RequestMethod.GET)
	 @ResponseBody
	 public Map<Object, Object> listProductAllRMAP(HttpSession session){
		 User user = (User) session.getAttribute(Const.CURRENT_USER);
		 Map<Object, Object> data = new HashMap<>();
		 if(user.getRole() == 1) {
			 List<Product> pList = iProductService.getProductAll();
			 data.put("data", pList);
			 return data;
	        }else{
	         data.put("data", "无权限操作,需要管理员权限");
	         return data;
	        } 
	 }
	 
	 /*新增客户端查询我发布的所有商品返回MAP*/
	 @RequestMapping(value="/user/cust_get_ownProduct",method =RequestMethod.GET)
	 @ResponseBody
	 public Map<Object, Object> cust_get_ownProduct(HttpSession session){
		 User user = (User) session.getAttribute(Const.CURRENT_USER);
		 Map<Object, Object> data = new HashMap<>();
		 if(user == null) {
			 data.put("data", "用户未登录，不能做此操作");
			 return data;
	      }else{
	        List<Product> pList = iProductService.getProductByUId(user.getId());
			data.put("data", pList);
			return data;
	        } 
	 }
	 
	 @RequestMapping("/user/custDeleteStuff")
	 @ResponseBody
		public ServerResponse custDeleteStuff(HttpSession session,int id ){
			User user = (User) session.getAttribute(Const.CURRENT_USER);
			if(user==null){
			   return ServerResponse.createByErrorMessage("用户未登陆，无法获取当前用户信息");
			 }
			iProductService.deleteProductById(id);
			iCartService.deleteProduct(user.getId(), String.valueOf(id));
			return ServerResponse.createBySuccessMessage("商品删除成功，并且已在用户购物车中移除");
			
		}
	 
	 /*
	  * 添加与更新
	  */
	 @RequestMapping("custSaveAndUpdate")
	 public String custSaveAndUpdate(HttpSession session,HttpServletRequest request){
		 User user = (User)session.getAttribute(Const.CURRENT_USER);
	       String username = user.getUsername();
	       String userid = user.getId().toString();
	            //填充增加产品的业务逻辑
	        	String path = ConfigReader.getValue("upload.basedir");
	    		UploadUtils uploadUtils = new UploadUtils();
	    		List resultInfo = uploadUtils.uploadFile(request, path);
	    		
	    		String imagePath = (String) resultInfo.get(4);
	    		//System.out.println("imagePath"+imagePath);
	    		Map<String, String> formField = (Map<String, String>) resultInfo.get(6);
	    		formField.put("userName", username);
	    		formField.put("userId", userid);
	    		List<String> nameList = (List<String>) resultInfo.get(7);
	    		Product product = setProduct(formField, imagePath, nameList);
	    		
	    		iProductService.saveOrUpdateProduct(product);
	           /* return iProductService.saveOrUpdateProduct(product);*/
	    		return "cartAndUpload.html";
	    	
	}
}
