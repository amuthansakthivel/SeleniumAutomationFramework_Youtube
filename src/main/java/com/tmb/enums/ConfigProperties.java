package com.tmb.enums;

/**
 * Enums to restrict the values used on Property files. Without using enums there can be null pointer exceptions happening
 * because of typos.
 * <p>
 * Whenever a new value is added to property file, corressponding enum should be created here.
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
 * @see com.tmb.utils.PropertyUtils
 */
public enum ConfigProperties {
	
	URL,
	OVERRIDEREPORTS,
	PASSEDSTEPSSCREENSHOTS,
	FAILEDSTEPSSCREENSHOTS,
	RETRYFAILEDTESTS,
	RUNMODE,
	SENDRESULTTOELK,
	SELENIUMGRIDURL,
	ELASTICURL,
	BROWSER
	
}
