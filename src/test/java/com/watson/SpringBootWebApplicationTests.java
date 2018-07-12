package com.watson;

import com.watson.core.exception.BusinessException;
import com.watson.system.model.LoginRecord;
import com.watson.system.model.User;
import com.watson.system.service.LoginRecordService;
import com.watson.system.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootWebApplicationTests {
	@Autowired
	private LoginRecordService loginRecordService;
	@Autowired
	private UserService userService;

	@Test
	public void contextLoads() {
		// TestConfig bean = SpringUtil.getBean(TestConfig.class);
		// System.out.println("----------------------------->"+bean.getAllowOrigin());
	}

	// 测试事务
	@Test
	//@Transactional
	public void transactionTest() throws BusinessException {
//		loginRecordService.addLoginRecord(new LoginRecord(){{
//			setUserId("GzV5Qh3u");
//			setCreateTime(new Date());
//		}});

//		int s = 1/0;

		userService.addUser(new User(){{
			setUserAccount("ljf");
			setUserPassword("ljf");
			setUserNickname("ljf");
			setCreateTime(new Date());
			setRoleId("user");
		}});
	}

}
