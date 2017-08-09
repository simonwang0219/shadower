package org.shadower.agent.dubbo;

import java.util.Map;

import org.shadower.agent.entity.Attachment;
import org.shadower.agent.util.AttachmentUtil;
import org.shadower.agent.util.SenderUtil;
import org.shadower.agent.util.SpanUtil;
import org.shadower.api.Span;
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
import com.shadower.util.Ipv4Util;
import com.shadower.util.JsonUtil;

@Activate(group = Constants.PROVIDER, order = -900)
public class DubboProviderSpanFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(DubboConsumerSpanFilter.class);

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		Span span = null;
		int ipV4 = Ipv4Util.getLocalIPV4();
		try {
			Attachment oriAtta = AttachmentUtil.getAttachmentFromMap(invocation.getAttachments());
			if (oriAtta != null) {
				span = SpanUtil.generateSpan(oriAtta, invocation.getMethodName());
				span.setSr(SpanUtil.generateAnnotation(ipV4));
				// 为子请求添加追踪信息
				long parentId = oriAtta.getId();
				Attachment newAtta = new Attachment(oriAtta.getTraceId(), SpanUtil.generateSpanId(parentId), parentId);
				Map<String, String> map = RpcContext.getContext().getAttachments();
				AttachmentUtil.addAttachmentToMap(newAtta, map);
			} else {
				// 生成追踪信息

			}
			return invoker.invoke(invocation);
		} finally {
			if (span != null) {
				span.setSs(SpanUtil.generateAnnotation(ipV4));
				SenderUtil.send(span);
				logger.info(JsonUtil.toJson(span));
			}
		}
	}
}
