package org.shadower.agent.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.shadower.api.Span;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shadower.util.JsonUtil;
import com.shadower.util.SnappyUtil;

public class SenderUtil {

	private static final Logger logger = LoggerFactory.getLogger(SenderUtil.class);
	private static final CloseableHttpClient httpClient;
	private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	private static final ConcurrentLinkedQueue<Span> queue = new ConcurrentLinkedQueue<Span>();

	private static String  shadowServerUrl = "http://localhost:8080/shadower/";
	private static boolean isCompressed=true;
	
	private static final int MAX_QUEUE_SIZE = 1024 * 60;
	private static final int BATCH_SIZE = 1024;
	private static final int DELAYED = 30;
	private static final int FIXED_PERIOD = 10;
	private static final int MAX_HTTP_CONNECTION_SIZE = 1;
	private static final int MAX_HTTP_RESPONSE_LENGTH = 1024;

	/***
	 * 初始化
	 */
	static {
		
		//读取配置文件
		readConfig();
		if(!shadowServerUrl.endsWith("/")){
			shadowServerUrl+="/";
		}
		httpClient = getHttpClient();
		executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				sendBatch();
			}
		}, DELAYED, FIXED_PERIOD, TimeUnit.SECONDS);

		/***
		 * 虚拟机关闭，释放资源
		 */
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//补偿机制
					sendBatch();
					executor.shutdown();
					httpClient.close();
				} catch (IOException e) {
					logger.error("close httpclient error", e);
				}
			}
		}));
	}
	
	
	/**
	 * 读取配置文件
	 */
	private static void readConfig() {
		
	}
	

	/***
	 * 池化
	 * 
	 * @return
	 */
	private static CloseableHttpClient getHttpClient() {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(MAX_HTTP_CONNECTION_SIZE);
		cm.setDefaultMaxPerRoute(MAX_HTTP_CONNECTION_SIZE);
		return HttpClients.custom().setConnectionManager(cm).build();
	}



	/**
	 * 暂存至queue，每隔10秒批量发送
	 * 
	 * @param span
	 */
	public static void send(final Span span) {
		if (span == null) {
			return;
		}
		if (queue.size() >= MAX_QUEUE_SIZE) {
			logger.error("the span queue size > " + MAX_QUEUE_SIZE);
			return;
		}
		queue.offer(span);
	}

	/**
	 * 批量发送（发送失败，补偿机制）
	 */
	private static void sendBatch() {
		if (queue.size() == 0) {
			return;
		}
		int size = queue.size() > BATCH_SIZE ? BATCH_SIZE : queue.size();
		List<Span> spans = new ArrayList<Span>(size);
		for (int i = 0; i < size; i++) {
			spans.add(queue.poll());
		}
		CloseableHttpResponse response = null;
		try {
			if(isCompressed){
				response=httpClient.execute(getHttpPostWithCompress(spans));
			}else {
				response = httpClient.execute(getHttpPost(spans));
			}
		} catch (IOException e) {
			logger.error("send span error", e);
		}
		if (response != null) {
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream instream = null;
					try {
						instream = entity.getContent();
						int len = MAX_HTTP_RESPONSE_LENGTH;
						byte[] bytes = new byte[len];
						instream.read(bytes, 0, len);
						String content = new String(bytes, "UTF-8");
						logger.info("response content : " + content);

					} catch (Exception e) {
						logger.error("read response entity error", e);
					} finally {
						try {
							if (instream != null) {
								instream.close();
							}
						} catch (Exception e) {
							logger.error("close the response stream error", e);
						}
					}
				}
			} else {
				logger.error("response info : " + response.getStatusLine());
			}
		}
	}

	private static HttpPost getHttpPost(List<Span> spans) {
		HttpPost post = new HttpPost(shadowServerUrl+"upload/");
		post.addHeader("Content-type", "application/json; charset=utf-8");
		post.setEntity(new StringEntity(JsonUtil.toJson(spans),"UTF-8"));
		return post;
	}
	
	private static HttpPost getHttpPostWithCompress(List<Span> spans) {
		HttpPost post = new HttpPost(shadowServerUrl+"upload/snappy/");
		String json = JsonUtil.toJson(spans);
		byte[] bytes = SnappyUtil.compree(json);
		post.setEntity( new ByteArrayEntity(bytes, ContentType.create("application/snappy", "UTF-8")));
		
		return post;
	}

}
