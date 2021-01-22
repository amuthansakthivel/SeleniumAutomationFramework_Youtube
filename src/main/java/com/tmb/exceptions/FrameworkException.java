package com.tmb.exceptions;

/**
 * BaseException for the framework. Extends Runtime Exception and can be thrown anywhere to terminate a program
 * 
 * 
 * <pre>
 * <b>
 * <a href="https://www.youtube.com/channel/UC6PTXUHb6j4Oxf0ccdRI11A">Testing Mini Bytes Youtube channel</a>
 * </b>
 * </pre>
 * 
 * Jan 21, 2021 
 * @author Amuthan Sakthivel
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public class FrameworkException extends RuntimeException{
	
	/**
	 * Pass the message that needs to be appended to the stacktrace
	 * @param message Details about the exception or custom message
	 */
	public FrameworkException(String message) {
		super(message);
	}
	/**
	 * 
	 * @param message Details about the exception or custom message
	 * @param cause Pass the enriched stacktrace or customised stacktrace
	 */
	public FrameworkException(String message,Throwable cause) {
		super(message,cause);
	}

}
