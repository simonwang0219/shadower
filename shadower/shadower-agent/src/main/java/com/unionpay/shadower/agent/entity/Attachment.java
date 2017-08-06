package com.unionpay.shadower.agent.entity;

public class Attachment {
	private long traceId;
	private long id;
	private Long parentId;

	public Attachment() {
		super();
	}
	
	public Attachment(long traceId, long id) {
		super();
		this.traceId = traceId;
		this.id = id;
	}

	public Attachment(long traceId, long id, Long parentId) {
		super();
		this.traceId = traceId;
		this.id = id;
		this.parentId = parentId;
	}

	public long getTraceId() {
		return traceId;
	}

	public void setTraceId(long traceId) {
		this.traceId = traceId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + (int) (traceId ^ (traceId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attachment other = (Attachment) obj;
		if (id != other.id)
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (traceId != other.traceId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Attachment [traceId=" + traceId + ", id=" + id + ", parentId=" + parentId + "]";
	}	
}
