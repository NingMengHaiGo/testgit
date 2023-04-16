package com.zhao.deep.test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zhao.deep.bean.Cart;
import com.zhao.deep.dao.CartMapper;


/**
 * 测试dao层的工作
 * @author lfy
 *推荐Spring的项目就可以使用Spring的单元测试，可以自动注入我们需要的组件
 *1、导入SpringTest模块
 *2、@ContextConfiguration指定Spring配置文件的位置
 *3、直接autowired要使用的组件即可
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class MapperTest {
	
	@Autowired
	CartMapper cartMapper;
	
	/**
	 * 测试
	 */
	@Test
	public void testCRUD(){
	
		System.out.println(cartMapper);
		
		cartMapper.insertSelective( new Cart(10,10,10,10,10,null,null));
		
		//测试插入
		//userMapper.insertSelective(new User(1,"song","123321","863455198","15038706691","问题","答案","用户",null,null));
		/*goodsMapper.insertSelective(new Goods(null,1001,"外套",null,null));*/
		
	
		//批量插入多个；批量，使用可以执行批量操作的sqlSession。
		
//		for(){
//			employeeMapper.insertSelective(new Employee(null, , "M", "Jerry@atguigu.com", 1));
//		}
		
	/*	EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
		for(int i = 0;i<1000;i++){
			String uid = UUID.randomUUID().toString().substring(0,5)+i;
			mapper.insertSelective(new Employee(null,uid, "M", uid+"@atguigu.com", 1));
		}
		System.out.println("批量完成");*/
		/*GoodsMapper mapper = sqlSession.getMapper(GoodsMapper.class);
		for(int i = 0;i<10;i++){
			String uid = UUID.randomUUID().toString().substring(0,5)+i;
			mapper.insertSelective(new Goods(i+1,null,"中长款",null,null));
		}
		System.out.println("批量完成");
	}*/
		}

}
