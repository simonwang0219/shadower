package com.unionpay.shadower.server.storage;

import java.util.List;

import com.unionpay.shadower.api.Span;
import com.unionpay.shadower.server.entity.Tracker;

public interface StorageService {
	public boolean addSpan(Span span);

	public List<Boolean> addSpan(List<Span> spans);

	public Tracker getTracker(long traceId);
}
