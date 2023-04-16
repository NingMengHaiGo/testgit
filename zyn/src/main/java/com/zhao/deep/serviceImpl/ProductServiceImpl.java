package com.zhao.deep.serviceImpl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.zhao.deep.bean.Cart;
import com.zhao.deep.bean.Product;
import com.zhao.deep.bean.ProductWithBLOBs;
import com.zhao.deep.bean.User;
import com.zhao.deep.common.ServerResponse;
import com.zhao.deep.dao.ProductMapper;
import com.zhao.deep.dao.UserMapper;
import com.zhao.deep.service.IProductService;

import vo.productUserVo;

@Service("iProductService")
public class ProductServiceImpl implements IProductService{
	
	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public ServerResponse<String> saveOrUpdateProduct(Product product) {
		// TODO Auto-generated method stub
		if(product!=null){
			if(product.getId() !=null){
				/*int rowCount = productMapper.updateByPrimaryKey(product.getId());*/
				int rowCount = productMapper.updateByPrimaryKeyWithBLOBs((ProductWithBLOBs) product);
				if(rowCount != 0 ){
					return ServerResponse.createBySuccess("更新成功");
				}
				return ServerResponse.createBySuccess("更新产品失败");
			}
			int rowCount = productMapper.insert((ProductWithBLOBs) product);
			if(rowCount != 0 ){
				return ServerResponse.createBySuccess("新增产品成功");
			}
			 return ServerResponse.createByErrorMessage("新增产品失败");
			
		}
		return ServerResponse.createByErrorMessage("新增或更新产品参数不正确");
	}
	
	
	 public List<productUserVo> getProductWithUser(){
		 List<Product> proList = productMapper.selectByExample(null);
		 System.out.println("proList:"+proList.size());
		 List<productUserVo> productUserVoList = Lists.newArrayList();
		 if(CollectionUtils.isNotEmpty(proList)){
			 for(Product productItem : proList){
				 productUserVo proUserVo = new productUserVo();
				 proUserVo.setId(productItem.getId());
				 proUserVo.setUserId(productItem.getUserId());
				 proUserVo.setCategoryId(productItem.getCategoryId());
				 proUserVo.setName(productItem.getName());
				 proUserVo.setSubtitle(productItem.getSubtitle());
				 proUserVo.setMainImage(productItem.getMainImage());
				 proUserVo.setSubImage(productItem.getMainImage());
				 proUserVo.setDetail(productItem.getDetail());
				 proUserVo.setPrice(productItem.getPrice());
				 proUserVo.setStock(productItem.getStock());
				 proUserVo.setStatus(productItem.getStatus());
				 proUserVo.setCreateTime(productItem.getCreateTime());
				 proUserVo.setUpdateTime(productItem.getUpdateTime());
				 User userMsg = userMapper.selectByPrimaryKey(proUserVo.getUserId());
				 if(userMsg!=null){
					 proUserVo.setUsername(userMsg.getUsername());
					 proUserVo.setEmail(userMsg.getEmail());
					 proUserVo.setPhone(userMsg.getPhone());
					 proUserVo.setActivecode(userMsg.getActivecode());
					 proUserVo.setState(userMsg.getState());
					 proUserVo.setUcreateTime(userMsg.getCreateTime());
					 
				 }
				 
				 productUserVoList.add(proUserVo);
			 }
			 
			 
		 }
		 System.out.println("productUserVoList:"+productUserVoList.size());
	        return productUserVoList;

	    }


	@Override
	public Object deleteProductById(int id) {
		// TODO Auto-generated method stub
		return productMapper.deleteByPrimaryKey(id);
	}


	@Override
	public Object selectProductById(int id) {
		// TODO Auto-generated method stub
		return productMapper.selectByPrimaryKey(id);
	}


	@Override
	public List<Product> selectProByCid(Integer cid) {
		// TODO Auto-generated method stub
		return productMapper.selectProByCid(cid);
	}


	@Override
	public List<Product> getAll() {
		// TODO Auto-generated method stub
		return productMapper.selectByExample(null);
	}


	@Override
	public List<Product> selectByMists(String subtitle, String username) {
		// TODO Auto-generated method stub
		return productMapper.selectByMists(subtitle,username);
	}


	@Override
	public List<Product> getProductAll() {
		// TODO Auto-generated method stub
		return productMapper.selectAll();
	}


	@Override
	public List<Product> getProductByUId(Integer id) {
		// TODO Auto-generated method stub
		return productMapper.getProductByUId(id);
	}

}
