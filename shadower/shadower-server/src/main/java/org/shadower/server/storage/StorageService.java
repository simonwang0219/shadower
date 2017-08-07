package org.shadower.server.storage;

import java.util.List;

import org.shadower.api.Span;
import org.shadower.server.entity.Tracker;

public interface StorageService {
	public boolean addSpan(Span span);

	public List<Boolean> addSpan(List<Span> spans);

	public Tracker getTracker(long traceId);
}
