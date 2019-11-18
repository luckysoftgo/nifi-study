package com.application.base.kafka.test.bean;

import java.io.Serializable;

/**
 * @author : 孤狼
 * @NAME: Basic
 * @DESC: 基础信息
 **/
public class Basic implements Serializable {
	
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 性別
	 */
	private String gender;
	/**
	 * 年齡
	 */
	private int age;

	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
}
