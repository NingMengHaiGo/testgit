package com.zhao.deep.dao;

import com.zhao.deep.bean.Category;
import com.zhao.deep.bean.CategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CategoryMapper {
    long countByExample(CategoryExample example);

    int deleteByExample(CategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExample(CategoryExample example);

    Category selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByExample(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

	List<Category> selectCategoryChildrenByParentId(Integer parentId);

	List<Category> selectAllCategory();

	List<Category> selectCategoryListByMis(String categorymisname);

	/*新增客户端通过二级分类查询三级分类及其商品*/
	List<Category> getCateListByParentId(Integer id);
}