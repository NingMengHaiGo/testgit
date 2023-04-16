package com.zhao.deep.dao;

import com.zhao.deep.bean.Shipping;
import com.zhao.deep.bean.ShippingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShippingMapper {
    long countByExample(ShippingExample example);

    int deleteByExample(ShippingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    List<Shipping> selectByExample(ShippingExample example);

    Shipping selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Shipping record, @Param("example") ShippingExample example);

    int updateByExample(@Param("record") Shipping record, @Param("example") ShippingExample example);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);
    
    int deleteByShipingIdUserId(@Param("userId") Integer userId,@Param("shippingId") Integer shippingId);
    
    int updateByShipping(Shipping record);
    
    Shipping selectByuserIdShippingId(@Param("userId") Integer userId,@Param("shippingId") Integer shippingId);
    
    List<Shipping> selectByUserId(@Param("userId") Integer userId);
}