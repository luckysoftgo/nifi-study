package com.application.base.kafka.test.bean;

/**
 * @author : 孤狼
 * @NAME: Teacher
 * @DESC: 老师实例
 **/
public class Teacher extends Basic {
	
	/**
	 * 工作年限
	 */
	private int workyear;
	/**
	 * 专业
	 */
	private String major;
	
	public int getWorkyear() {
		return workyear;
	}
	
	public void setWorkyear(int workyear) {
		this.workyear = workyear;
	}
	
	public String getMajor() {
		return major;
	}
	
	public void setMajor(String major) {
		this.major = major;
	}
}
