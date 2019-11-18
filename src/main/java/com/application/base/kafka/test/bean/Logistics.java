package com.application.base.kafka.test.bean;

import java.io.Serializable;

/**
 * @author : 孤狼
 * @NAME: Logistics
 * @DESC: 车辆轨迹数据.
 **/
public class Logistics implements Serializable {
	
	/*
		ID	VARCHAR2	32	0	FALSE		ID
		ENCRYPT	VARCHAR2	10	0	FALSE		加密标识
		UP_DATE	VARCHAR2	20	0	FALSE		时间
		LON	VARCHAR2	32	0	FALSE		经度
		CREATE_BY	VARCHAR2	32	0	FALSE		创建人
		CREATE_DT	VARCHAR2	19	0	FALSE		创建时间
		LAT	VARCHAR2	32	0	FALSE		纬度
		VEC1	VARCHAR2	32	0	FALSE		GPS速度
		VEC2	VARCHAR2	32	0	FALSE		行驶记录速度
		VEC3	VARCHAR2	32	0	FALSE		车辆当前总里程数
		DIRECTION	VARCHAR2	32	0	FALSE		方向
		ALTITUDE	VARCHAR2	32	0	FALSE		海拔高度
		STATE	VARCHAR2	32	0	FALSE		车辆状态
		ALARM	VARCHAR2	32	0	FALSE		报警状态
		VEHICLENO	VARCHAR2	32	0	FALSE		车牌号码
		VEHICLECOLOR	VARCHAR2	32	0	FALSE		车身颜色
	*/
	
	/**
	 * id
	 */
	private String id;
	/**
	 * 加密标识
	 */
	private String encrypt;
	/**
	 * 时间
	 */
	private String up_date;
	/**
	 * 经度
	 */
	private String lon;
	/**
	 * 创建人
	 */
	private String create_by;
	/**
	 * 创建时间
	 */
	private String create_dt;
	/**
	 *纬度
	 */
	private String lat;
	/**
	 *GPS速度
	 */
	private String vec1;
	/**
	 *行驶记录速度
	 */
	private String vec2;
	/**
	 * 车辆当前总里程数
	 */
	private String vec3;
	/**
	 * 方向
	 */
	private String direction;
	/**
	 * 海拔高度
	 */
	private String altitude;
	/**
	 * 车辆状态
	 */
	private String state;
	/**
	 * 报警状态
	 */
	private String alarm;
	/**
	 * 车牌号码
	 */
	private String vehicleno;
	/**
	 *车身颜色
	 */
	private String vehiclecolor;
	
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getEncrypt() {
		return encrypt;
	}
	
	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}
	
	public String getUp_date() {
		return up_date;
	}
	
	public void setUp_date(String up_date) {
		this.up_date = up_date;
	}
	
	public String getLon() {
		return lon;
	}
	
	public void setLon(String lon) {
		this.lon = lon;
	}
	
	public String getCreate_by() {
		return create_by;
	}
	
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	
	public String getCreate_dt() {
		return create_dt;
	}
	
	public void setCreate_dt(String create_dt) {
		this.create_dt = create_dt;
	}
	
	public String getLat() {
		return lat;
	}
	
	public void setLat(String lat) {
		this.lat = lat;
	}
	
	public String getVec1() {
		return vec1;
	}
	
	public void setVec1(String vec1) {
		this.vec1 = vec1;
	}
	
	public String getVec2() {
		return vec2;
	}
	
	public void setVec2(String vec2) {
		this.vec2 = vec2;
	}
	
	public String getVec3() {
		return vec3;
	}
	
	public void setVec3(String vec3) {
		this.vec3 = vec3;
	}
	
	public String getDirection() {
		return direction;
	}
	
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public String getAltitude() {
		return altitude;
	}
	
	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getAlarm() {
		return alarm;
	}
	
	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}
	
	public String getVehicleno() {
		return vehicleno;
	}
	
	public void setVehicleno(String vehicleno) {
		this.vehicleno = vehicleno;
	}
	
	public String getVehiclecolor() {
		return vehiclecolor;
	}
	
	public void setVehiclecolor(String vehiclecolor) {
		this.vehiclecolor = vehiclecolor;
	}
}
