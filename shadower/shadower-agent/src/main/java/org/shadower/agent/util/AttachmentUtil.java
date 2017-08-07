package org.shadower.agent.util;

import java.util.Map;

import org.shadower.agent.entity.Attachment;



public class AttachmentUtil {
	public static void addAttachmentToMap(Attachment attachment, Map<String, String> map) {
		if (attachment == null || map == null) {
			throw new NullPointerException();
		}
		map.put("traceId", attachment.getTraceId()+"");
		map.put("id", attachment.getId()+"");
		Long parentId=attachment.getParentId();
		if(parentId!=null){
			map.put("parentId", attachment.getParentId()+"");
		}
	}

	public static Attachment getAttachmentFromMap(Map<String, String> map) {
		if (map == null) {
			throw new NullPointerException();
		}
		if (!map.containsKey("traceId") || !map.containsKey("id")) {
			return null;
		}
		String traceId = map.get("traceId");
		String id = map.get("id");
		String parentId = map.get("parentId");
		if(parentId==null){
			return  new Attachment(Long.valueOf(traceId), Long.valueOf(id));
		}
		return new Attachment(Long.valueOf(traceId), Long.valueOf(id), Long.valueOf(parentId));
	}
}
