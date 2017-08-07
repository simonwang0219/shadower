package org.shadower.api;

public class Annotation {
	private long timestamp; 
	private EndPoint endPoint;

	public Annotation() {
		super();
	}

	public Annotation(long timestamp) {
		super();
		this.timestamp = timestamp;
	}

	public Annotation(long timestamp, EndPoint endPoint) {
		super();
		this.timestamp = timestamp;
		this.endPoint = endPoint;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public EndPoint getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(EndPoint endPoint) {
		this.endPoint = endPoint;
	}

}
