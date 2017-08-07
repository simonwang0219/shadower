package org.shadower.server.web.vo;

import com.shadower.util.JsonUtil;

public class ShadowerResponse {
	private int code=200;
	private String state="OK";
	private Object result;

	public ShadowerResponse() {
		super();
	}

	public ShadowerResponse(int code, String state) {
		super();
		this.code = code;
		this.state = state;
	}

	public ShadowerResponse(Object result) {
		super();
		this.result = result;
	}

	public ShadowerResponse(int code, String state, Object result) {
		super();
		this.code = code;
		this.state = state;
		this.result = result;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}	
}
