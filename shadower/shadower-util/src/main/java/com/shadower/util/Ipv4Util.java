package com.shadower.util;

import java.net.Inet4Address;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ipv4Util {

	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	private static byte[] ipToBytes(String ipAddr) {
		byte[] ret = new byte[4];
		try {
			String[] ipArr = ipAddr.split("\\.");
			ret[0] = (byte) (Integer.parseInt(ipArr[0]) & 0xFF);
			ret[1] = (byte) (Integer.parseInt(ipArr[1]) & 0xFF);
			ret[2] = (byte) (Integer.parseInt(ipArr[2]) & 0xFF);
			ret[3] = (byte) (Integer.parseInt(ipArr[3]) & 0xFF);
			return ret;
		} catch (Exception e) {
			throw new IllegalArgumentException(ipAddr + " is invalid IP");
		}
	}

	private static int bytesToInt(byte[] bytes) {
		int addr = bytes[3] & 0xFF;
		addr |= ((bytes[2] << 8) & 0xFF00);
		addr |= ((bytes[1] << 16) & 0xFF0000);
		addr |= ((bytes[0] << 24) & 0xFF000000);
		return addr;
	}

	public static int ipToInt(String ipAddr) {
		try {
			return bytesToInt(ipToBytes(ipAddr));
		} catch (Exception e) {
			throw new IllegalArgumentException(ipAddr + " is invalid IP");
		}
	}

	public static String intToIp(int ipInt) {
		return new StringBuilder().append(((ipInt >> 24) & 0xff)).append('.').append((ipInt >> 16) & 0xff).append('.')
				.append((ipInt >> 8) & 0xff).append('.').append((ipInt & 0xff)).toString();
	}

	public static int getLocalIPV4() {
		int ipV4=0;
		try {
			ipV4 = ipToInt(Inet4Address.getLocalHost().getHostAddress());
		} catch (Exception e) {
			logger.error("parse ip to int error",e);
		}
		return ipV4;
	}
}
