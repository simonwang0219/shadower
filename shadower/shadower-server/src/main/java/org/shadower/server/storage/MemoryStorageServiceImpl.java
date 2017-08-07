package org.shadower.server.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shadower.api.Span;
import org.shadower.server.entity.Tracker;
import org.springframework.stereotype.Service;

@Service("storeService")
public class MemoryStorageServiceImpl implements StorageService {
	private final static Map<Long, Tracker> shadower = new HashMap<Long, Tracker>();

	@Override
	public boolean addSpan(Span span) {
		if (span == null) {
			return false;
		}
		long traceId = span.getTraceId();
		Tracker tracker = null;
		// 多线程写,ConcurrentHashMap会存在弱一致性
		if (!shadower.containsKey(traceId)) {
			synchronized (shadower) {
				if (!shadower.containsKey(traceId)) {
					tracker = new Tracker(traceId);
					shadower.put(traceId, tracker);
				}
			}
		}
		if (tracker == null) {
			tracker = shadower.get(traceId);
		}
		tracker.add(span);
		return true;
	}

	@Override
	public List<Boolean> addSpan(List<Span> spans) {

		if (spans == null) {
			return new ArrayList<Boolean>(0);
		}
		List<Boolean> result = new ArrayList<Boolean>(spans.size());
		for (Span span : spans) {
			if (addSpan(span)) {
				result.add(true);
			} else {
				result.add(false);
			}
		}
		return result;
	}

	@Override
	public Tracker getTracker(long traceId) {
		return shadower.get(traceId);
	}

}
