package com.zhao.deep.dao;

import com.zhao.deep.bean.Cart;
import com.zhao.deep.bean.CartExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CartMapper {
    long countByExample(CartExample example);

    int deleteByExample(CartExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    List<Cart> selectByExample(CartExample example);

    Cart selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Cart record, @Param("example") CartExample example);

    int updateByExample(@Param("record") Cart record, @Param("example") CartExample example);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

	Cart selectCartByUserIdProductId(@Param("userId") Integer userId,@Param("productId") Integer productId);
	
	List<Cart> selectCartByUserId(Integer userId);

	int selectCartProductCheckedStatusByUserId(Integer userId);
	
	int deleteByUserIdProductIds(@Param("userId") Integer userId,@Param("productIdList")List<String> productIdList);
	
	int checkedOrUncheckedProduct(@Param("userId") Integer userId,@Param("productId")Integer productId,@Param("checked") Integer checked);
	
	int selectCartProductCount(@Param("userId") Integer userId);

	List<Cart> selectCheckedCartByUserId(Integer userId);
	
	
	
}