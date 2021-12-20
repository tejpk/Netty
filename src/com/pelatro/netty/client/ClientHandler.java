package com.pelatro.netty.client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.pelatro.netty.server.ResponseData;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class ClientHandler extends ChannelInboundHandlerAdapter {

	private AtomicBoolean bool;

	private static SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMdd HH:mm:ss.SSS" );

	private AtomicLong integer = new AtomicLong( 0 );

	private long previousNumber = 0;

	private long startTime = 0l;
	
	private static double l =0;

	public ClientHandler( AtomicBoolean bool ) {
		this.bool = bool;
	}

	public void send( Channel channel, Object msg ) {
		bool.set( false );
		if(channel.isWritable())
		 channel.writeAndFlush( msg );
		
		if ( startTime == 0l )
			startTime = System.currentTimeMillis();
	}
	
	@Override
	public void channelRead( ChannelHandlerContext ctx, Object msg ) throws Exception {
		long incrementAndGet = integer.incrementAndGet();
		if ( incrementAndGet % 10000 == 0 ) { 
			l = ( System.currentTimeMillis() - startTime ) * 1.0 / 1000;
			long m = incrementAndGet - previousNumber;
			System.out.println( sdf.format( new Date() ) + " Responses recived so far " + incrementAndGet + " tps => " + ( int ) ( m / l ) + " " + m + " " + l );
			previousNumber = incrementAndGet;
			startTime = System.currentTimeMillis();
		}
		bool.set( true );
	}
}