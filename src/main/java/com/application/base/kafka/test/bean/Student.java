package com.application.base.kafka.test.bean;

/**
 * @author : 孤狼
 * @NAME: Student
 * @DESC: 学生实例
 **/
public class Student extends Basic {
	
	/**
	 * 年级
	 */
	private String grade;
	/**
	 *班级
	 */
	private String clasz;
	
	public String getGrade() {
		return grade;
	}
	
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public String getClasz() {
		return clasz;
	}
	
	public void setClasz(String clasz) {
		this.clasz = clasz;
	}
}
