package com.application.base.kafka.test.bean;

import java.io.Serializable;

/**
 * @author : 孤狼
 * @NAME: Score
 * @DESC: 得分实例
 **/
public class Score implements Serializable {
	
	/**
	 * 课程名称.
	 */
	private String courseName;
	/**
	 * 课程得分.
	 */
	private int courseScore;
	/**
	 * 教课老师.
	 */
	private String teacher;
	/**
	 * 等分学生.
	 */
	private String student;
	
	public String getCourseName() {
		return courseName;
	}
	
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public int getCourseScore() {
		return courseScore;
	}
	
	public void setCourseScore(int courseScore) {
		this.courseScore = courseScore;
	}
	
	public String getTeacher() {
		return teacher;
	}
	
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	
	public String getStudent() {
		return student;
	}
	
	public void setStudent(String student) {
		this.student = student;
	}
}
