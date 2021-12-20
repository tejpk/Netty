package com.pelatro.netty.client;

import java.util.concurrent.atomic.AtomicBoolean;

import com.pelatro.netty.server.RequestData;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

	private static Bootstrap b = null;

	private static final int CONNECT_ONCE_AND_COMMUNICATE = 1;

	private static final int CONNECT_EVERYTIME_AND_COMMUNICATE = 2;

	public static void main(String[] args) throws Exception {
		String host = "localhost";
		int port = 8080;
		/*
		 * if ( args.length > 2 ) { host = args[0]; port = Integer.parseInt( args[1] );
		 * } int mode = Integer.parseInt( args[2] ); int kB = Integer.parseInt( args[3]
		 * );
		 */
		int mode = 2;
		int kB = 4;
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		AtomicBoolean bool = new AtomicBoolean(false);
		try {
			ClientHandler clientHandler = new ClientHandler(bool);
			b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new RequestDataEncoder(), new ResponseDataDecoder(), clientHandler);
				}
			});
			// ChannelFuture f = b.connect( host, port ).sync();
			// RequestData msg = new RequestData();
			// msg.setIntValue( 123 );
			// msg.setStringValue( "all work and no play makes jack a dull boy" );
			// f.channel().writeAndFlush( msg );
			// f.channel().closeFuture().sync();

			String stringValue = new String(new byte[kB * 1024]);
			if (mode == CONNECT_EVERYTIME_AND_COMMUNICATE) {
				RequestData msg = new RequestData();
				while (true) {
					Channel channel = b.connect(host, port).sync().channel();
					msg.setIntValue(123);
					msg.setStringValue(stringValue);
					msg.setMode(mode);
					clientHandler.send(channel, msg);
					 while (!bool.get()) {
					 }
					channel.disconnect();
				}
			} else if (mode == CONNECT_ONCE_AND_COMMUNICATE) {
				Channel channel = b.connect(host, port).sync().channel();
				RequestData msg = new RequestData();
				
				while (true) {
					msg.setIntValue(123);
					msg.setStringValue(stringValue);
					msg.setMode(mode);
					clientHandler.send(channel, msg);
				}
			}
		} finally {
			workerGroup.shutdownGracefully();
		}
	}
}
