package com.unionpay.shadower.api;

import java.util.ArrayList;
import java.util.List;

public class Span {
	private long traceId;  //require
	private long id;       //require
	private Long parentId;
	private String name;   //require
	private Long timestamp;
	private Long duration;
	private Annotation cs;
	private Annotation cr;
	private Annotation ss;
	private Annotation sr;

	private List<BusinessAnnotation> businessAnnotations= new ArrayList<BusinessAnnotation>();

	public Span() {
		super();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Annotation getCs() {
		return cs;
	}

	public void setCs(Annotation cs) {
		this.cs = cs;
	}

	public Annotation getCr() {
		return cr;
	}

	public void setCr(Annotation cr) {
		this.cr = cr;
	}

	public Annotation getSs() {
		return ss;
	}

	public void setSs(Annotation ss) {
		this.ss = ss;
	}

	public Annotation getSr() {
		return sr;
	}

	public void setSr(Annotation sr) {
		this.sr = sr;
	}


	
	public List<BusinessAnnotation> getBusinessAnnotations() {
		return businessAnnotations;
	}

	public void setBusinessAnnotations(List<BusinessAnnotation> businessAnnotations) {
		this.businessAnnotations = businessAnnotations;
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
		Span other = (Span) obj;
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

}
