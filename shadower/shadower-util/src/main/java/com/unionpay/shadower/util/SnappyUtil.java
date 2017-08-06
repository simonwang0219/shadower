package com.unionpay.shadower.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xerial.snappy.Snappy;

public class SnappyUtil {

	private static final Logger logger = LoggerFactory.getLogger(Snappy.class);
	private static final String DEFAULT_CHARSET = "UTF-8";

	public static byte[] compree(String content) {
		return compree(content, DEFAULT_CHARSET);
	}

	public static byte[] compree(String content, String charset) {
		if (content == null) {
			return new byte[0];
		}
		byte[] bytes = null;
		try {
			bytes = Snappy.compress(content, charset);
		} catch (IOException e) {
			if (bytes == null) {
				bytes = new byte[0];
			}
			logger.error("compress error", e);
		}
		return bytes;
	}

	public static String uncompress(byte[] bytes) {
		return uncompress(bytes, DEFAULT_CHARSET);
	}

	public static String uncompress(byte[] bytes, String charset) {
		if (bytes == null) {
			return "";
		}
		String content = null;
		try {
			content = Snappy.uncompressString(bytes, charset);
		} catch (IOException e) {
			if (content == null) {
				content = "";
			}
			logger.error("uncompress error", e);
		}
		return content;
	}
}
