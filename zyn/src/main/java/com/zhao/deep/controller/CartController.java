package com.zhao.deep.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhao.deep.bean.User;
import com.zhao.deep.common.Const;
import com.zhao.deep.common.ResponseCode;
import com.zhao.deep.common.ServerResponse;
import com.zhao.deep.service.ICartService;
import com.zhao.deep.utils.BigDecimalUtil;

import vo.CartProductVo;
import vo.CartVo;

@Controller
/*@RequestMapping("/user/cart/")*/
public class CartController {
	
	@Autowired
	private ICartService iCartService;
	
	@RequestMapping("/user/cart/add")
	public String add(HttpSession session,Model model,Integer count,Integer productId){
		//System.out.println("count"+count+"......."+"productId"+productId);
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if(user == null){
			model.addAttribute("message", "�û�δ��½���޷���ȡ��ǰ�û���Ϣ");
			return "error";
		}
		 iCartService.add(user.getId(), productId, count);
		 
		 return "redirect:/user/cart/list";
	}
	
	
	@RequestMapping("/user/cart/update")
	@ResponseBody
	public ServerResponse<CartVo> update(HttpSession session,Integer count,Integer productId){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if(user == null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
		}
		System.out.println(count+"+"+productId);
		return iCartService.update(user.getId(), productId, count);
	}
	
	
	@RequestMapping("/user/cart/delete_products")
    @ResponseBody
    public ServerResponse<CartVo> deleteProduct(HttpSession session,String productIds){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
         iCartService.deleteProduct(user.getId(),productIds);
         return ServerResponse.createBySuccessMessage("�Ѵӹ��ﳵ���Ƴ�");
    }
	
	@RequestMapping("/user/cart/list")
   /* @ResponseBody
    public ServerResponse<CartVo> list(HttpSession session ,Model model){*/
	 public String list(HttpSession session ,Model model){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
        	 model.addAttribute("userMessage", user);
			 return "index";
           /* return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());*/
        }
        ServerResponse<CartVo> CartMessage = iCartService.list(user.getId());
        List<CartProductVo> listCartProductVo=CartMessage.getData().getCartProductVoList();
        model.addAttribute("cartMsg", listCartProductVo);
        /*cartAllTotalPrice = listCartProductVo.cart*/
        model.addAttribute("cartMsg2", CartMessage);
		return "cart/listCart";
      /* return iCartService.list(user.getId());elemenData*/
    }
	
	//ȫѡ
	 @RequestMapping("select_all")
	    @ResponseBody
	    public ServerResponse<CartVo> selectAll(HttpSession session){
	        User user = (User)session.getAttribute(Const.CURRENT_USER);
	        if(user ==null){
	            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
	        }
	        return iCartService.selectOrUnSelect(user.getId(),null,Const.Cart.CHECKED);
	    }
	 
	 //ȫ��ѡ
	 
	 @RequestMapping("un_select_all")
	    @ResponseBody
	    public ServerResponse<CartVo> unSelectAll(HttpSession session){
	        User user = (User)session.getAttribute(Const.CURRENT_USER);
	        if(user ==null){
	            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
	        }
	        return iCartService.selectOrUnSelect(user.getId(),null,Const.Cart.UN_CHECKED);
	    }
	 
	 
	    //����ѡ
	    //������ѡ
	    @RequestMapping(value="/user/cart/select",method=RequestMethod.POST)
	    @ResponseBody
	    public ServerResponse<CartVo> select(HttpSession session,Integer productId,Integer checked){
	        User user = (User)session.getAttribute(Const.CURRENT_USER);
	        if(user ==null){
	            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
	        }
	        System.out.println(productId+".........."+checked);
	        return iCartService.selectOrUnSelect(user.getId(),productId,checked);
	    }

	    @RequestMapping("un_select")
	    @ResponseBody
	    public ServerResponse<CartVo> unSelect(HttpSession session,Integer productId){
	        User user = (User)session.getAttribute(Const.CURRENT_USER);
	        if(user ==null){
	            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
	        }
	        return iCartService.selectOrUnSelect(user.getId(),productId,Const.Cart.UN_CHECKED);
	    }
	    
	  //��ѯ��ǰ�û��Ĺ��ﳵ����Ĳ�Ʒ����,���һ����Ʒ��10��,��ô��������10.
	    
	    @RequestMapping("get_cart_product_count")
	    @ResponseBody
	    public ServerResponse<Integer> getCartProductCount(HttpSession session){
	        User user = (User)session.getAttribute(Const.CURRENT_USER);
	        if(user ==null){
	            return ServerResponse.createBySuccess(0);
	        }
	        return iCartService.getCartProductCount(user.getId());
	    }
	    
	  //�����ͻ�������
	    @RequestMapping("/cart/getMyCartList")
	    @ResponseBody
	 	public Map<Object, Object> listMyCart(HttpSession session ,Model model){
	         User user = (User)session.getAttribute(Const.CURRENT_USER);
	         Map<Object, Object> data = new HashMap<>();
	         if(user==null){
	 			data.put("errorMSG","�û�δ��½���޷���ȡ��ǰ�û���Ϣ");
	 			return data;
	 		}
	         ServerResponse<CartVo> CartMessage = iCartService.list(user.getId());
	         List<CartProductVo> listCartProductVo=CartMessage.getData().getCartProductVoList();
	         data.put("data", listCartProductVo);
	 		return data;
	     }
	    //�����ͻ�������
	    @RequestMapping(value = "/user/cart/custAddPro",method=RequestMethod.POST)
	    @ResponseBody
		public ServerResponse custAddPro(HttpSession session,@RequestParam(value = "count",defaultValue = "1")Integer count,Integer productId){
			User user = (User) session.getAttribute(Const.CURRENT_USER);
			if(user == null){
				return ServerResponse.createByErrorMessage("�û�δ��½���޷���ȡ��ǰ�û���Ϣ");
			}
			 return  iCartService.add(user.getId(), productId, count);
		}
	    
	    //�����ͻ�������
	    @RequestMapping("/cart/custListCart")
	    @ResponseBody
	     public ServerResponse<CartVo> list(HttpSession session){
	         User user = (User)session.getAttribute(Const.CURRENT_USER);
	         if(user ==null){
	             return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
	         }
	         return iCartService.list(user.getId());
	     }
	    
	    //�����ͻ�������
	    @RequestMapping("/cart/getMyCartCheckedList")
	    @ResponseBody
	 	public Map<Object, Object> getMyCartCheckedList(HttpSession session ,Model model){
	         User user = (User)session.getAttribute(Const.CURRENT_USER);
	         Map<Object, Object> data = new HashMap<>();
	         if(user==null){
	 			data.put("errorMSG","�û�δ��½���޷���ȡ��ǰ�û���Ϣ");
	 			return data;
	 		}
	         ServerResponse<CartVo> CartMessage = iCartService.list(user.getId());
	         List<CartProductVo> listCartProductVo=CartMessage.getData().getCartProductVoList();
	         List<CartProductVo> listCartChecked = new ArrayList();
	         BigDecimal totalpayPrice = new BigDecimal("0.1");;
	         for(CartProductVo CartCheckedItem : listCartProductVo){
	        	 if(CartCheckedItem.getProductChecked() == 1){
	        		 listCartChecked.add(CartCheckedItem);
	        		 totalpayPrice = BigDecimalUtil.add(totalpayPrice.doubleValue(),CartCheckedItem.getProductPrice().doubleValue());
	        	 }
	         }
	         data.put("data", listCartChecked);
	 		return data;
	     }
	  //�����ͻ�������
	    @RequestMapping("/cart/myCartCheckedPriceAndCount")
	    @ResponseBody
	 	public Map<String, Object> myCartCheckedPriceAndCount(HttpSession session ,Model model){
	         User user = (User)session.getAttribute(Const.CURRENT_USER);
	         Map<String, Object> map=new HashMap<String, Object>();
	         if(user==null){
	        	 map.put("errorMSG","�û�δ��½���޷���ȡ��ǰ�û���Ϣ");
	 			return map;
	 		}
	         ServerResponse<CartVo> CartMessage = iCartService.list(user.getId());
	         List<CartProductVo> listCartProductVo=CartMessage.getData().getCartProductVoList();
	       /*  List<CartProductVo> listCartChecked = new ArrayList();*/
	         BigDecimal totalpayPrice = new BigDecimal("0.0");
	         int count = 0;
	         for(CartProductVo CartCheckedItem : listCartProductVo){
	        	 if(CartCheckedItem.getProductChecked() == 1){
	        		 count = count + CartCheckedItem.getQuantity();
	        		 /*listCartChecked.add(CartCheckedItem);*/
	        		 BigDecimal oneTotalPrice = BigDecimalUtil.mul(CartCheckedItem.getProductPrice().doubleValue(),CartCheckedItem.getQuantity());
	        		 totalpayPrice = BigDecimalUtil.add(totalpayPrice.doubleValue(),oneTotalPrice.doubleValue());
	        	 }
	         }
	         map.put("count", count);
	         map.put("totalpayPrice", totalpayPrice);
	        
	 		return map;
	     }

}
