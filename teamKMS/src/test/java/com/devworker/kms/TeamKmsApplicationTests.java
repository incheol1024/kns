package com.devworker.kms;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import com.devworker.kms.dao.GroupDao;
import com.devworker.kms.dao.MenuDao;
import com.devworker.kms.dic.MenuType;
import com.devworker.kms.dto.MessageDetailDto;
import com.devworker.kms.dto.MessageDto;
import com.devworker.kms.ftsdao.PostFTSDao;
import com.devworker.kms.service.FTSService;
import com.devworker.kms.service.GroupService;
import com.devworker.kms.service.MenuService;
import com.devworker.kms.service.MessageService;
import com.devworker.kms.service.UserService;
import com.devworker.kms.util.CommonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamKmsApplicationTests {
	@Autowired
	MenuService menuService;
	@Autowired
	MessageService messageService;
	@Autowired
	UserService userService;
	@Autowired
	GroupService groupService;
	@Autowired
	FTSService ftsService;
	@Autowired
	PasswordEncoder encoder;

	@WithMockUser(value = "ADMIN")
	@Test
	public void getCurrentUserTest() throws Exception {
		String userName = CommonUtil.getCurrentUser();
		System.out.println(userName);
		assertThat(userName, notNullValue());
	}
	
	@Test
	public void passwordEncdoerTest() {
		String value = "USER";
		String encode = encoder.encode(value);
		System.out.println(encode);
		assertThat(encode, notNullValue());
	}

	@Test
	public void sendMessageTest() throws JsonProcessingException, InterruptedException, ExecutionException {
		Gson mapper = new Gson();
		MessageDto dto = new MessageDto();
		dto.setText("ddd");
		dto.addAttach(new MessageDetailDto());
		System.out.println(mapper.toJson(dto));
		String sendMessage = messageService.sendMessage(dto);
		System.out.println(sendMessage);
	}

	@Test
	public void getMenuListTest() {
		List<MenuDao> menuList = menuService.getMenuList(MenuType.SOL);
		assertThat(menuList, not(IsEmptyCollection.empty()));
	}

	@Test
	public void userTest() {
		userService.countUser();
	}

	@Test
	public void groupTest() {
		groupService.countGroup();
		GroupDao dao = new GroupDao();
		dao.setName("Test");
		dao.setParentid(0);
		List<GroupDao> list = groupService.getGroupChild(dao);
		assertThat(list, not(IsEmptyCollection.empty()));
	}

	@Test
	public void addGroupTest() {
		GroupDao dao = new GroupDao();
		dao.setName("Test");
		dao.setParentid(0);
		groupService.addGroup(dao);
	}

	@Test
	public void ftsgetCountTest() {
		System.out.println(ftsService.getCount());
		PostFTSDao dto = new PostFTSDao();
		dto.setId(1);
		dto.setCategory(1);
		dto.setName("aaa");
		dto.setUser("bbb");
		ftsService.save(dto);
		List<PostFTSDao> findbyuser = ftsService.findbyuser("bbb");
		System.out.println(findbyuser);
	}

	@Test
	public void ftsSaveTest() {
		PostFTSDao dto = new PostFTSDao();
		dto.setId(1);
		dto.setCategory(1);
		dto.setName("aaa");
		dto.setUser("bbb");
		ftsService.save(dto);
	}

	@Test
	public void ftsSearchTest() {
		List<PostFTSDao> findbyuser = ftsService.findbyuser("bbb");
		assertThat(findbyuser, not(findbyuser.isEmpty()));
	}
}
