package com.unionpay.shadower.api;

public class EndPoint {
	private int ipV4;
	private String serviceName;

	public EndPoint() {
		super();
	}

	public EndPoint(int ipV4, String serviceName) {
		super();
		this.ipV4 = ipV4;
		this.serviceName = serviceName;
	}

	public int getIpV4() {
		return ipV4;
	}

	public void setIpV4(int ipV4) {
		this.ipV4 = ipV4;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

}
