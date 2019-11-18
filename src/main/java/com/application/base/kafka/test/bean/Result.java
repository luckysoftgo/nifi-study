package com.application.base.kafka.test.bean;

/**
 * @author : 孤狼
 * @NAME: Result
 * @DESC: 结果
 **/
public class Result {
	
	/**
	 * 返回码
	 */
	private int code;
	/**
	 * 消息
	 */
	private String msg;
	/**
	 * 返回数据
	 */
	private Object data;
	
	public static Result success(Object data){
		return new Result(200,"success",data);
	}
	
	public static Result success(String msg,Object data){
		return new Result(200,msg,data);
	}
	
	public static Result success(int code,String msg,Object data){
		return new Result(code,msg,data);
	}
	
	public Result(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
}
