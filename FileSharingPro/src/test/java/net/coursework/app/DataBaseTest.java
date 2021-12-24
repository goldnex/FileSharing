package net.coursework.app;

import org.junit.Assert;
import org.junit.Test;
import org.junit.BeforeClass;

import java.util.ArrayList;
import java.util.List;

import net.coursework.app.DataBase;
import net.coursework.app.User;
import net.coursework.app.Package;
import net.coursework.app.Chat;
import net.coursework.app.ChatObject;

public class DataBaseTest {
	@Test
	public void dataBaseTestNotNull() {
		DataBase database = new DataBase("DataBaseTest.xml");
		Assert.assertNotNull(database);
	}
	
	@Test
	public void userTest() {
		List<User> expected = new ArrayList<User>();
		expected.add(new User(12, "Vasya", "1234"));
		expected.add(new User(14, "Misha", "qweasd"));
		
		Assert.assertEquals(expected.get(0).getId(), 12);
		Assert.assertEquals(expected.get(0).getName(), "Vasya");
		Assert.assertEquals(expected.get(0).getPassword(), "1234");
		
		Assert.assertEquals(expected.get(1).getId(), 14);
		Assert.assertEquals(expected.get(1).getName(), "Misha");
		Assert.assertEquals(expected.get(1).getPassword(), "qweasd");
	}
	
	@Test
	public void packageTest() {
		List<Package> expected = new ArrayList<Package>();
		expected.add(new Package(12, "documents/"));
		expected.add(new Package(12, "pictures/"));
		expected.add(new Package(14, "coursework/"));
		
		Assert.assertEquals(expected.get(0).getId(), 12);
		Assert.assertEquals(expected.get(0).getPath(), "documents/");
		
		Assert.assertEquals(expected.get(1).getId(), 12);
		Assert.assertEquals(expected.get(1).getPath(), "pictures/");
		
		Assert.assertEquals(expected.get(2).getId(), 14);
		Assert.assertEquals(expected.get(2).getPath(), "coursework/");
	}
	
	@Test
	public void chatObjectTest() {
		List<ChatObject> expected = new ArrayList<ChatObject>();
		expected.add(new ChatObject(12, "Hello, Misha", "11 pm"));
		
		Assert.assertEquals(expected.get(0).getId(), 12);
		Assert.assertEquals(expected.get(0).getMessage(), "Hello, Misha");
		Assert.assertEquals(expected.get(0).getTime(), "11 pm");
	}
}
