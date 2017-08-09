package org.shadower.agent.dubbo;

import java.util.Map;

import org.shadower.agent.context.ShadowerContext;
import org.shadower.agent.entity.Attachment;
import org.shadower.agent.util.AttachmentUtil;
import org.shadower.agent.util.InvocationUtil;
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

@Activate(group = Constants.CONSUMER, order = -900)
public class DubboConsumerSpanFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(DubboConsumerSpanFilter.class);

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

		Span span = null;
		int ipV4 = Ipv4Util.getLocalIPV4();

		try {
			// 首先从shadowerContext中获取附件，若为空，则从RpcContext中获取附件，也为空的话，生成附件
			Attachment attachment = ShadowerContext.getAttachment();
			Map<String, String> map = RpcContext.getContext().getAttachments();
			if (attachment != null) {
				// 添加追踪信息
				long parentId = attachment.getId();
				Attachment newAtta = new Attachment(attachment.getTraceId(), SpanUtil.generateSpanId(parentId),
						parentId);
				AttachmentUtil.addAttachmentToMap(newAtta, map);
				span = SpanUtil.generateSpan(newAtta, InvocationUtil.getServiceName(invoker, invocation));
			} else {
				attachment = AttachmentUtil.getAttachmentFromMap(map);
				if (attachment == null) {
					// 添加追踪信息
					long traceId = SpanUtil.generateTraceId();
					attachment = new Attachment(traceId, traceId);
					AttachmentUtil.addAttachmentToMap(attachment, map);
				}
				span = SpanUtil.generateSpan(attachment, InvocationUtil.getServiceName(invoker, invocation));
			}
			span.setCs(SpanUtil.generateAnnotation(ipV4));

			return invoker.invoke(invocation);
		} finally {
			span.setCr(SpanUtil.generateAnnotation(ipV4));
			SenderUtil.send(span);
			logger.info(JsonUtil.toJson(span));
		}
	}

}
