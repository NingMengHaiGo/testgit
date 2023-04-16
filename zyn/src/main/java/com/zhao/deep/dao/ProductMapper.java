package com.zhao.deep.dao;

import com.zhao.deep.bean.Product;
import com.zhao.deep.bean.ProductExample;
import com.zhao.deep.bean.ProductWithBLOBs;
import com.zhao.deep.common.ServerResponse;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductMapper {
    long countByExample(ProductExample example);

    int deleteByExample(ProductExample example);

    int deleteByPrimaryKey(Integer id);

  /*  int insert(Product record);*/
    
    int insert(ProductWithBLOBs record);

    int insertSelective(ProductWithBLOBs record);

    List<ProductWithBLOBs> selectByExampleWithBLOBs(ProductExample example);

    List<Product> selectByExample(ProductExample example);
    
    List<Product> selectAll();

    ProductWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductWithBLOBs record, @Param("example") ProductExample example);

    int updateByExampleWithBLOBs(@Param("record") ProductWithBLOBs record, @Param("example") ProductExample example);

    int updateByExample(@Param("record") Product record, @Param("example") ProductExample example);

    int updateByPrimaryKeySelective(ProductWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ProductWithBLOBs product);

    int updateByPrimaryKey(@Param("id") Integer id);

    List<Product> selectByMists(@Param("subtitle") String subtitle,@Param("username") String username);
    
    int updateByPrimaryKeySelective(Product record);

    List<Product> selectProByCid(Integer cid);

	List<Product> getProductByUId(@Param("id") Integer id);
}