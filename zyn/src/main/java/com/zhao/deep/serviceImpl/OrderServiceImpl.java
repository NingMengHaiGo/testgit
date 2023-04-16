package com.zhao.deep.serviceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import com.zhao.deep.bean.Cart;
import com.zhao.deep.bean.Order;
import com.zhao.deep.bean.OrderItem;
import com.zhao.deep.bean.Product;
import com.zhao.deep.common.Const;
import com.zhao.deep.common.ServerResponse;
import com.zhao.deep.dao.CartMapper;
import com.zhao.deep.dao.OrderItemMapper;
import com.zhao.deep.dao.OrderMapper;
import com.zhao.deep.dao.ProductMapper;
import com.zhao.deep.dao.ShippingMapper;
import com.zhao.deep.service.IOrderService;
import com.zhao.deep.utils.BigDecimalUtil;
import com.zhao.deep.utils.ExportExcelUtils;

@Service("iOrderService")
public class OrderServiceImpl implements IOrderService{
	
	
	@Autowired
	private CartMapper cartMapper;
	
	@Autowired
    private ProductMapper productMapper;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
    private OrderItemMapper orderItemMapper;
	
	@Override
	public ServerResponse PayOrder(Integer userId, int shippingId,int status) {
	
		 //�ӹ��ﳵ�л�ȡ����
        List<Cart> cartList = cartMapper.selectCheckedCartByUserId(userId);

        //��������������ܼ�
        ServerResponse serverResponse = this.getCartOrderItem(userId,cartList);
        if(!serverResponse.isSuccess()){
            return serverResponse;
        }
        List<OrderItem> orderItemList = (List<OrderItem>)serverResponse.getData();
        BigDecimal payment = this.getOrderTotalPrice(orderItemList);
        
        //���ɶ���
        Order order = this.assembleOrder(userId,shippingId,payment);
        
        if(order == null){
            return ServerResponse.createByErrorMessage("���ɶ�������");
        }
        if(CollectionUtils.isEmpty(orderItemList)){
            return ServerResponse.createByErrorMessage("���ﳵΪ��");
        }
        for(OrderItem orderItem : orderItemList){
            orderItem.setOrderNo(order.getOrderNo());
        }
      //mybatis ��������
        orderItemMapper.batchInsert(orderItemList);
      
      //���ɳɹ�,����Ҫ�������ǲ�Ʒ�Ŀ��
        this.reduceProductStock(orderItemList);
        //���һ�¹��ﳵ
        this.cleanCart(cartList);
		return serverResponse.createBySuccess();
	}
	

	private ServerResponse getCartOrderItem(Integer userId, List<Cart> cartList) {
		 List<OrderItem> orderItemList = Lists.newArrayList();
	        if(CollectionUtils.isEmpty(cartList)){
	            return ServerResponse.createByErrorMessage("���ﳵΪ��");
	        }

	        //У�鹺�ﳵ������,������Ʒ��״̬������
	        for(Cart cartItem : cartList){
	            OrderItem orderItem = new OrderItem();
	            Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
	           if(product.getStatus()!=1){
	                return ServerResponse.createByErrorMessage("��Ʒ"+product.getName()+"������������״̬");
	            }

	            //У����
	            if(cartItem.getQuantity() > product.getStock()){
	                return ServerResponse.createByErrorMessage("��Ʒ"+product.getName()+"��治��");
	            }

	            orderItem.setUserId(userId);
	            orderItem.setProductId(product.getId());
	            orderItem.setProductName(product.getName());
	            orderItem.setProductImage(product.getMainImage());
	            orderItem.setCurrentUnitPrice(product.getPrice());
	            orderItem.setQuantity(cartItem.getQuantity());
	            orderItem.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartItem.getQuantity()));
	            orderItemList.add(orderItem);
	        }
	        return ServerResponse.createBySuccess(orderItemList);
	}
	
	private BigDecimal getOrderTotalPrice(List<OrderItem> orderItemList){
        BigDecimal payment = new BigDecimal("0");
        for(OrderItem orderItem : orderItemList){
            payment = BigDecimalUtil.add(payment.doubleValue(),orderItem.getTotalPrice().doubleValue());
        }
        return payment;
    }
	
	 private Order assembleOrder(Integer userId,Integer shippingId,BigDecimal payment){
	        Order order = new Order();
	        long orderNo = this.generateOrderNo();
	        order.setOrderNo(orderNo);
	        order.setStatus(1);
	        order.setPostage(0);
	        order.setPaymentType(1);
	        order.setPayment(payment);

	        order.setUserId(userId);
	        order.setShippingId(shippingId);
	        //����ʱ��ȵ�
	        //����ʱ��ȵ�
	        int rowCount = orderMapper.insert(order);
	        if(rowCount != 0){
	            return order;
	        }
	        return null;
	    }
	 
	 private long generateOrderNo(){
	        long currentTime =System.currentTimeMillis();
	        return currentTime+new Random().nextInt(100);
	    }
	 
	 private void reduceProductStock(List<OrderItem> orderItemList){
	        for(OrderItem orderItem : orderItemList){
	            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
	            product.setStock(product.getStock()-orderItem.getQuantity());
	            productMapper.updateByPrimaryKeySelective(product);
	        }
	    }
	 
	 private void cleanCart(List<Cart> cartList){
	        for(Cart cart : cartList){
	            cartMapper.deleteByPrimaryKey(cart.getId());
	        }
	    }

	 public List<OrderItem> listPayedOrder(){
		return orderItemMapper.selectByExample(null);
		 
	 }


	@Override
	public List<Order> listOrder() {
		// TODO Auto-generated method stub
		return orderMapper.selectByExample(null);
	}


	@Override
	public void exportOrder(HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ExportExcelUtils excelUtils = new ExportExcelUtils(orderMapper);
		excelUtils.buildExcelDocument(response);
		
	}

	//�����ͻ�������
	@Override
	public List<Order> listMyOrderById(Integer id) {
		// TODO Auto-generated method stub
		return  orderMapper.selectByUserId(id);
	}


	@Override
	public List<OrderItem> custPayedOrder(Integer id) {
		// TODO Auto-generated method stub
		return orderItemMapper.custSelectOrder(id);
	} 
	 

}
