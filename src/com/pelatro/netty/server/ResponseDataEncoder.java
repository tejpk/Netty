package com.pelatro.netty.server;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ResponseDataEncoder extends MessageToByteEncoder<ResponseData> {

	private final Charset charset = Charset.forName( "UTF-8" );
	@Override
	protected void encode( ChannelHandlerContext ctx, ResponseData msg, ByteBuf out ) throws Exception {
		out.writeInt( msg.getIntValue() );
		out.writeInt( msg.getStringValue().length() );
		out.writeCharSequence( msg.getStringValue(), charset );
	}

}