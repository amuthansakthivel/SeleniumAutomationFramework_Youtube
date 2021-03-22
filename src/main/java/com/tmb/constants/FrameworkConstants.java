package com.tmb.constants;

import com.tmb.enums.ConfigProperties;
import com.tmb.utils.PropertyUtils;

/**
 * Framework Constants holds all the constant values used within the framework. If some value needs to be changed
 * or modified often, then it should be stored in the property files
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
public final class FrameworkConstants {
	
	/**
	 * Private constructor to avoid external instantiation
	 */
	private FrameworkConstants() {}
	
	private static final int EXPLICITWAIT = 10;
	private static final String RESOURCESPATH = System.getProperty("user.dir")+"/src/test/resources";
	private static final String CHROMEDRIVERPATH = RESOURCESPATH+"/executables/chromedriver.exe";
	private static final String GECKODRIVERPATH = RESOURCESPATH+"/executables/geckodriver.exe";
	private static final String CONFIGFILEPATH = RESOURCESPATH+"/config/config.properties";
	private static final String JSONCONFIGFILEPATH = RESOURCESPATH + "/config/config.json";
	private static final String EXCELPATH = RESOURCESPATH+"/excel/testdata.xlsx";
	private static final String RUNMANGERSHEET = "RUNMANAGER";
	private static final String ITERATIONDATASHEET = "DATA";
	private static final String EXTENTREPORTFOLDERPATH = System.getProperty("user.dir")+"/extent-test-output/";
	private static String extentReportFilePath = "";
	
	/**
	 * 
	 * @author Amuthan Sakthivel
	 * Jan 21, 2021
	 * @return Extent Report path where the index.html file will be generated.
	 */
	public static String getExtentReportFilePath()  {
		if(extentReportFilePath.isEmpty()) {
			extentReportFilePath = createReportPath();
		}
		return extentReportFilePath;
	}
	/**
	 * 
	 * @author Amuthan Sakthivel
	 * Jan 21, 2021
	 * @return  If Override reports value in the 
	 * property file is no,then the timestamp will be appended
	 */
	private static String createReportPath()  {
		if(PropertyUtils.get(ConfigProperties.OVERRIDEREPORTS).equalsIgnoreCase("no")) {
			return EXTENTREPORTFOLDERPATH+System.currentTimeMillis()+"/index.html";
		}
		else {
			return EXTENTREPORTFOLDERPATH+"/index.html";
		}
	}
	
	/* 
	 * TODO Lombak Plugin to remove the boiler plate code
	 */
	public static String getGeckoDriverPath() {
		return GECKODRIVERPATH;
	}

	
	public static String getExcelpath() {
		return EXCELPATH;
	}

	public static String getJsonconfigfilepath() {
		return JSONCONFIGFILEPATH;
	}

	public static int getExplicitwait() {
		return EXPLICITWAIT;
	}
	
	public static String getRunmangerDatasheet() {
		return RUNMANGERSHEET;
	}
	
	public static String getIterationDatasheet() {
		return ITERATIONDATASHEET;
	}

	public static String getConfigFilePath() {
		return CONFIGFILEPATH;
	}

	public static String getChromeDriverPath() {
		return CHROMEDRIVERPATH;
	}

}
