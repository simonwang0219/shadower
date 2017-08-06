package com.unionpay.shadower.agent.dubbo;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.unionpay.shadower.agent.entity.Attachment;
import com.unionpay.shadower.agent.util.AttachmentUtil;
import com.unionpay.shadower.agent.util.InvocationUtil;
import com.unionpay.shadower.agent.util.SenderUtil;
import com.unionpay.shadower.agent.util.SpanUtil;
import com.unionpay.shadower.api.Span;
import com.unionpay.shadower.util.Ipv4Util;
import com.unionpay.shadower.util.JsonUtil;

@Activate(group = Constants.PROVIDER, order = -900)
public class ProviderSpanFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(ConsumerSpanFilter.class);

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		Span span = null;
		int ipV4 = Ipv4Util.getLocalIPV4();
		String serviceName = InvocationUtil.getServiceName(invoker, invocation);
		try {
			Attachment oriAtta = AttachmentUtil.getAttachmentFromMap(invocation.getAttachments());
			if (oriAtta != null) {
				span = SpanUtil.generateSpan(oriAtta, invocation.getMethodName());
				span.setSr(SpanUtil.generateAnnotation(serviceName, ipV4));
				// 为子请求添加追踪信息
				long parentId = oriAtta.getId();
				Attachment newAtta = new Attachment(oriAtta.getTraceId(), SpanUtil.generateSpanId(parentId), parentId);
				Map<String, String> map = RpcContext.getContext().getAttachments();
				AttachmentUtil.addAttachmentToMap(newAtta, map);
			}
			return invoker.invoke(invocation);
		} finally {
			if (span != null) {
				span.setSs(SpanUtil.generateAnnotation(serviceName, ipV4));
				SenderUtil.send(span);
				logger.info(JsonUtil.toJson(span));
			}
		}
	}
}
