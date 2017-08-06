package com.unionpay.shadower.server.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.unionpay.shadower.api.Annotation;
import com.unionpay.shadower.api.Span;

public class Tracker {
	private long traceId;
	private List<Span> spans = new ArrayList<Span>();
	private Map<Long, Span> map = new HashMap<Long, Span>();

	public Tracker(long traceId) {
		super();
		this.traceId = traceId;
	}

	public long getTraceId() {
		return traceId;
	}

	public void setTraceId(long traceId) {
		this.traceId = traceId;
	}

	public List<Span> getSpans() {
		return spans;
	}

	public void add(Span span) {
		if (span == null) {
			return;
		}
		long id = span.getId();
		// 多线程,双重检查，减少锁范围
		if (!map.containsKey(id)) {
			synchronized (map) {
				if (!map.containsKey(id)) {
					map.put(id, span);
					spans.add(span);
				}
			}
		}

		Span existedSpan = map.get(id);
		if (existedSpan != span) {
			mergeSpan(existedSpan, span);
		}
	}

	private void mergeSpan(Span existedSpan, Span span) {
		Annotation cr = existedSpan.getCr();
		Annotation cs = existedSpan.getCs();
		Annotation sr = existedSpan.getSr();
		Annotation ss = existedSpan.getSs();

		if (cr == null) {
			cr = span.getCr();
			existedSpan.setCr(cr);
		}
		if (cs == null) {
			cs = span.getCs();
			existedSpan.setCs(cs);
		}
		if (sr == null) {
			sr = span.getSr();
			existedSpan.setSr(sr);
		}
		if (ss == null) {
			ss = span.getSs();
			existedSpan.setSs(ss);
		}
		
		if (cs != null && cr != null) {
			existedSpan.setDuration(cr.getTimestamp() - cs.getTimestamp());
		} else if (cs == null && cr == null) {
			if (sr != null && ss != null) {
				existedSpan.setDuration(ss.getTimestamp() - sr.getTimestamp());
			}
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Tracker other = (Tracker) obj;
		if (traceId != other.traceId)
			return false;
		return true;
	}
}
