package org.shadower.server.web.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.shadower.api.Span;
import org.shadower.server.storage.StorageService;
import org.shadower.server.web.vo.ShadowerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shadower.util.JsonUtil;
import com.shadower.util.SnappyUtil;

@Controller
public class SpanController {
	private static final Logger logger = Logger.getLogger(SpanController.class);
	@Autowired
	private StorageService storageService;

	@RequestMapping(value = { "/upload" }, method = RequestMethod.POST)
	@ResponseBody
	public ShadowerResponse upload(@RequestBody List<Span> spans) {
		logger.info("request info" + JsonUtil.toJson(spans));
		return new ShadowerResponse();
	}

	@RequestMapping(value = { "/upload/snappy" }, method = RequestMethod.POST)
	@ResponseBody
	public ShadowerResponse upload(@RequestBody byte[] bytes) {

		String json = SnappyUtil.uncompress(bytes);
		List<Span> spans = JsonUtil.toList(json, Span.class);
		storageService.addSpan(spans);
		return new ShadowerResponse();
	}
}
