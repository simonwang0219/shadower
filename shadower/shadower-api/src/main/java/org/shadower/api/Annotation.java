package org.shadower.api;

public class Annotation {
	private long timestamp;
	private int ipV4;

	public Annotation() {
		super();
	}

	public Annotation(long timestamp, int ipV4) {
		super();
		this.timestamp = timestamp;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getIpV4() {
		return ipV4;
	}

	public void setIpV4(int ipV4) {
		this.ipV4 = ipV4;
	}

}
