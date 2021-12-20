package com.pelatro.netty.client;

import java.nio.charset.Charset;

import com.pelatro.netty.server.RequestData;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RequestDataEncoder extends MessageToByteEncoder<RequestData> {

	private final Charset charset = Charset.forName( "UTF-8" );
	
	@Override
	protected void encode( ChannelHandlerContext ctx, RequestData msg, ByteBuf out ) throws Exception {
		out.writeInt( msg.getIntValue() );
		out.writeInt( msg.getStringValue().length() );
		out.writeCharSequence( msg.getStringValue(), charset );
	}
}