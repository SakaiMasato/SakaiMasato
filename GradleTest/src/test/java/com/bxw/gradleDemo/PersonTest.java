package com.bxw.gradleDemo;

import org.junit.Assert;
import org.junit.Test;

/**
 * java -classpath xxx.jar com.bxw.gradleDemo.PersonTest
 * @author Admin
 *
 */
public class PersonTest {

	@Test
	public void getName(){
		Person p = new Person();
		p.setName("xxx");
		Assert.assertNotNull(p.getName());
		//Assert.assertNull(p.getName());
	}
}
