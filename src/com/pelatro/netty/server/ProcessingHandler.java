package com.pelatro.netty.server;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class ProcessingHandler extends ChannelInboundHandlerAdapter {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");

	private AtomicLong integer = new AtomicLong(0);

	private long previousNumber = 0;

	private long startTime = 0l;
	static int counter = 1;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (startTime == 0l)
			startTime = System.currentTimeMillis();
		RequestData requestData = (RequestData) msg;
		ResponseData responseData = new ResponseData();
		responseData.setIntValue(requestData.getIntValue());
		responseData.setStringValue(requestData.getStringValue());
		System.out.println(requestData + " " + counter);
		ctx.writeAndFlush(responseData);
		responseData=null;
		counter++;
		//ctx.close(); }
		
		long incrementAndGet = integer.incrementAndGet();
		if (incrementAndGet % 10000 == 0) {
			double l = (System.currentTimeMillis() - startTime) * 1.0 / 1000;
			long m = incrementAndGet - previousNumber;
			System.out.println(sdf.format(new Date()) + " Requests recived so far " + incrementAndGet + " tps => "
					+ (int) (m / l) + " " + m + " " + l);
			previousNumber = incrementAndGet;
			startTime = System.currentTimeMillis();
		}
	}

}