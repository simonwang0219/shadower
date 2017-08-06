package com.unionpay.shadower.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class JsonUtil<T> {
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	private static final ObjectMapper mapper = new ObjectMapper();;
	static {
		mapper.setSerializationInclusion(Include.NON_EMPTY);
	}

	public static String toJson(Object obj) {
		String json = "";
		try {
			json = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error("serialize to json error", e);
		}
		return json;
	}

	public static <T> T toObject(String json, Class<T> clazz) {
		T t = null;
		try {
			t = mapper.readValue(json, clazz);
		} catch (IOException e) {
			logger.error("unserialize to object error,json info : "+json, e);
		}
		return t;
	}

	public static <T> List<T> toList(String json, Class<T> clazz) {
		
		CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
		List<T> list = null;
		try {
			list = mapper.readValue(json, listType);
		} catch (IOException e) {
			logger.error("unserialize to list error,json info : "+json, e);
		}
		return list;
	}

}
