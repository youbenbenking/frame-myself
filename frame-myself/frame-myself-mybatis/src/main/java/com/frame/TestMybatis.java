package com.frame;

import com.frame.mapper.UserMapper;
import com.frame.pojo.User;
import com.frame.sqlSession.MySqlsession;

public class TestMybatis {

	public static void main(String[] args) {  
	       MySqlsession sqlsession=new MySqlsession();  
	       UserMapper mapper = sqlsession.getMapper(UserMapper.class);  
	       User user = mapper.getUserById(1L);  
	       System.out.println(user);
	   }
}
