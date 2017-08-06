package com.unionpay.shadower.server.web.controller;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unionpay.shadower.api.Span;
import com.unionpay.shadower.server.entity.Tracker;
import com.unionpay.shadower.server.storage.StorageService;
import com.unionpay.shadower.server.web.vo.ShadowerResponse;

@Controller
public class TrackerController {

	private static final Logger logger = Logger.getLogger(SpanController.class);
	@Autowired
	private StorageService storageService;

	@RequestMapping(value = { "/tracker/{traceId}" }, method = RequestMethod.GET)
	@ResponseBody
	public ShadowerResponse getTracker(@PathVariable long traceId) {
		logger.info("request traceId : " + traceId);
		Tracker tracker = storageService.getTracker(traceId);
		if (tracker == null) {
			return new ShadowerResponse(new ArrayList<Span>());
		}
		return new ShadowerResponse(tracker.getSpans());
	}
}
