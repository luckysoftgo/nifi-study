package com.application.base.kafka.test.util;

import com.alibaba.fastjson.JSON;
import com.application.base.kafka.test.bean.Logistics;
import com.application.base.kafka.test.bean.Score;
import com.application.base.kafka.test.bean.Student;
import com.application.base.kafka.test.bean.Teacher;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : 孤狼
 * @NAME: DataUtil
 * @DESC: 数据产生工具类
 **/
public class DataUtil {

	/**
	 * 老师集合
	 */
	static final String[] teachers = new String[]{"张三丰","觉远","灭绝","任我行","乔峰","风清扬"};
	
	/**
	 * 学生集合
	 */
	static final String[] students = new String[]{"张翠山","火工头陀","周芷若","令狐冲","段誉","岳不群"};
	/**
	 * 年级集合
	 */
	static final String[] grades = new String[]{"一年级","二年级","三年级","四年级","五年级","六年级"};
	/**
	 * 课程
	 */
	static final String[] classes = new String[]{"语文","数学","美术","音乐","英语"};
	/**
	 * 性别
	 */
	static final String[] genders = new String[]{"男","女"};

	/**
	 * 学生对象
	 */
	public static Student getStudent(){
		Student student = new Student();
		student.setName(students[(int) (Math.random() * students.length)]);
		student.setGender(genders[(int) (Math.random() * genders.length)]);
		student.setAge((int)(10+Math.random()*(20-10+1)));
		student.setGrade(grades[(int) (Math.random() * grades.length)]);
		student.setClasz(classes[(int) (Math.random() * classes.length)]);
		return student;
	}
	
	/**
	 * 学生对象
	 */
	public static Teacher getTeacher(){
		Teacher teacher = new Teacher();
		teacher.setName(teachers[(int) (Math.random() * teachers.length)]);
		teacher.setGender(genders[(int) (Math.random() * genders.length)]);
		teacher.setAge((int)(20+Math.random()*(80-20+1)));
		teacher.setMajor(classes[(int) (Math.random() * classes.length)]);
		teacher.setWorkyear((int)(1+Math.random()*(50-10+1)));
		return teacher;
	}
	
	/**
	 * 得分对象
	 */
	public static Score getScore(){
		Score score = new Score();
		score.setTeacher(teachers[(int) (Math.random() * teachers.length)]);
		score.setStudent(students[(int) (Math.random() * students.length)]);
		score.setCourseName(classes[(int) (Math.random() * classes.length)]);
		score.setCourseScore((int)(40+Math.random()*(120-20+1)));
		return score;
	}
	
	/**
	 * 轨迹数据.
	 */
	public static Logistics getLogistics(int index){
		Logistics logistics = new Logistics();
		logistics.setId("MSG陕EC813"+index);
		logistics.setEncrypt("0");
		logistics.setUp_date(DateFormatUtils.format(new Date(),DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.getPattern()));
		logistics.setLon("10960206"+index);
		logistics.setCreate_by("Admin");
		logistics.setCreate_dt(DateFormatUtils.format(new Date(),DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.getPattern()));
		logistics.setLat("3491543"+index);
		logistics.setVec1("10"+index);
		logistics.setVec2("20"+index);
		logistics.setVec3("22834"+index);
		logistics.setDirection("0");
		logistics.setAltitude("40"+index);
		logistics.setState(index+"");
		logistics.setAlarm("52428"+index);
		logistics.setVehicleno("陕EC8135"+index);
		logistics.setVehiclecolor(index+"");
		return logistics;
	}
	
	/**
	 * 学生对象
	 */
	public static List<Student> getStudents(){
		List<Student> list = new ArrayList<>();
		for (int i = 0; i < students.length; i++) {
			list.add(getStudent());
		}
		return list;
	}
	
	/**
	 * 学生对象
	 */
	public static List<Teacher> getTeachers(){
		List<Teacher> list = new ArrayList<>();
		for (int i = 0; i < teachers.length; i++) {
			list.add(getTeacher());
		}
		return list;
	}
	
	/**
	 * 得分对象
	 */
	public static List<Score> getScores(){
		List<Score> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			list.add(getScore());
		}
		return list;
	}
	
	/**
	 * 得分对象
	 */
	public static List<Logistics> getLogisticss(){
		List<Logistics> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			list.add(getLogistics(i));
		}
		return list;
	}
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			//System.out.println(JSON.toJSON(getStudent()));
			//System.out.println(JSON.toJSON(getTeacher()));
			//System.out.println(JSON.toJSON(getScore()));
			System.out.println(JSON.toJSON(getStudents()));
			System.out.println(JSON.toJSON(getTeachers()));
			System.out.println(JSON.toJSON(getScores()));
			System.out.println(JSON.toJSON(getLogisticss()));
			System.out.println("================================================================");
		}
	}
}
