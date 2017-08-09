package org.shadower.agent.http.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.shadower.agent.context.ShadowerContext;
import org.shadower.agent.entity.Attachment;
import org.shadower.agent.util.SenderUtil;
import org.shadower.agent.util.SpanUtil;
import org.shadower.api.Span;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shadower.util.Ipv4Util;
import com.shadower.util.JsonUtil;

public class ServletSpanFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(ServletSpanFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		int ipV4 = Ipv4Util.getLocalIPV4();
		Span span = beforeInvocation(ipV4, httpRequest.getRequestURI());

		chain.doFilter(request, response);

		afterInvocation(span, ipV4);
	}

	@Override
	public void destroy() {
	}

	private Span beforeInvocation(int ipV4, String spanName) {
		// 获取当前线程的附件
		Attachment attachment = ShadowerContext.getAttachment();
		if (attachment == null) {
			attachment = new Attachment();
			ShadowerContext.setAttachment(attachment);
		}
		long traceId = SpanUtil.generateTraceId();
		attachment.setTraceId(traceId);
		attachment.setId(traceId);

		Span span = SpanUtil.generateSpan(attachment, spanName);
		span.setSr(SpanUtil.generateAnnotation(ipV4));
		return span;
	}

	private void afterInvocation(Span span, int ipV4) {
		if (span == null) {
			return;
		}
		span.setSs(SpanUtil.generateAnnotation(ipV4));
		SenderUtil.send(span);
		logger.info(JsonUtil.toJson(span));
	}

}
