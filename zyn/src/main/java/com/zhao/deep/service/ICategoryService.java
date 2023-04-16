package com.zhao.deep.service;

import java.util.List;

import com.zhao.deep.bean.Category;
import com.zhao.deep.common.ServerResponse;

public interface ICategoryService {
	

    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId,String categoryName);

    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    ServerResponse selectCategoryAndChildrenById(Integer categoryId);
    
    ServerResponse listCategory();

    List<Category> getCategoryListByMis(String categorymisname);

    /*�����ͻ���ͨ�����������ѯ�������༰����Ʒ*/
	List<Category> getCateBySecCate(Integer id);


}
