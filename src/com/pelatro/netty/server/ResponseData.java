package com.pelatro.netty.server;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ResponseData {

	private static SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMdd HH:mm:ss.SSS" );

	@Override
	public String toString() {
		return sdf.format( new Date() ) + " ResponseData [" + stringValue.length() + "]";
	}

	private int intValue;

	private String stringValue;

	//	private byte[] bytes;

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue( int intValue ) {
		this.intValue = intValue;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue( String stringValue ) {
		this.stringValue = stringValue;
	}

	//	public byte[] getBytes() {
	//		return bytes;
	//	}
	//
	//	public void setBytes( byte[] bytes ) {
	//		this.bytes = bytes;
	//	}
}