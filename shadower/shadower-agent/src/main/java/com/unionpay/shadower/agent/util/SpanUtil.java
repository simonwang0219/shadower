package com.unionpay.shadower.agent.util;


import java.util.concurrent.atomic.AtomicInteger;

import com.unionpay.shadower.agent.entity.Attachment;
import com.unionpay.shadower.api.Annotation;
import com.unionpay.shadower.api.EndPoint;
import com.unionpay.shadower.api.Span;


public final class SpanUtil {
	private static final AtomicInteger traceIdGenerator=new AtomicInteger(1);
	private static final AtomicInteger spanIdGenerator=new AtomicInteger(1);
	
	public static long generateTraceId(){
		return traceIdGenerator.getAndIncrement();
	}
	
	public static long generateSpanId(long parentId){
		//数据溢出
		return spanIdGenerator.getAndIncrement()+parentId;
	}
	
	public static Annotation generateAnnotation(String serviceName,int ipV4){
		Annotation annotation=new Annotation();
		annotation.setTimestamp(System.currentTimeMillis());
		annotation.setEndPoint(new EndPoint(ipV4, serviceName));
		return annotation;
	}
	
	public static Span generateSpan(Attachment attachment,String name){
		Span span=new Span();
		span.setTraceId(attachment.getTraceId());
		span.setId(attachment.getId());
		span.setParentId(attachment.getParentId());
		span.setName(name);
		return span;
	}
	
	
}
