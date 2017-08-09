package org.shadower.agent.context;

import org.shadower.agent.entity.Attachment;

public class ShadowerContext {
	private static final ThreadLocal<Attachment> threadLocal=new ThreadLocal<Attachment>();
	
	public static Attachment getAttachment(){
		// 返回当前线程内共享的attachment  
        return threadLocal.get();  
	}
	public static void setAttachment(Attachment attachment){
		if(attachment==null){
			return;
		}
		threadLocal.set(attachment);
	}
	
}
