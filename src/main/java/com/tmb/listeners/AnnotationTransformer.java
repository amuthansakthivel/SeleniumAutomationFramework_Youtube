package com.tmb.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import com.tmb.utils.DataProviderUtils;

/**
 * Implements {@link org.testng.IAnnotationTransformer} to leverage certain functionality like updating the annotations of test
 * methods at runtime.
 * <pre>Please make sure to add the listener details in the testng.xml file</pre>
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
 * @see com.tmb.utils.DataProviderUtils
 */
public class AnnotationTransformer implements IAnnotationTransformer{

	/**
	 * Helps in setting the dataprovider, dataprovider class and retry analyser annotation to all the test methods
	 * at run time. 
	 */
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		annotation.setDataProvider("getData");
		annotation.setDataProviderClass(DataProviderUtils.class);
		annotation.setRetryAnalyzer(RetryFailedTests.class);
	}

	

}
