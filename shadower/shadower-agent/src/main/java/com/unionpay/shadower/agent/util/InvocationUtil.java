package com.unionpay.shadower.agent.util;

import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;

public class InvocationUtil {
	public static String getServiceName(Invoker<?>invoker,Invocation invocation){
		return invoker.getInterface().getSimpleName()+"."+invocation.getMethodName();
	}
}
