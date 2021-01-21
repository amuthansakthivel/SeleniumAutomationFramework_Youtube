package com.tmb.constants;

import com.tmb.enums.ConfigProperties;
import com.tmb.utils.PropertyUtils;

public final class FrameworkConstants {
	
	private FrameworkConstants() {}
	
	private static final int EXPLICITWAIT = 10;
	private static final String RESOURCESPATH = System.getProperty("user.dir")+"/src/test/resources";
	private static final String CHROMEDRIVERPATH = RESOURCESPATH+"/executables/chromedriver.exe";
	private static final String GECKODRIVERPATH = RESOURCESPATH+"/executables/geckodriver.exe";
	
	private static final String CONFIGFILEPATH = RESOURCESPATH+"/config/confi.properties";
	private static final String JSONCONFIGFILEPATH = RESOURCESPATH + "/config/config.json";
	private static final String EXCELPATH = RESOURCESPATH+"/excel/testdata.xlsx";
	private static final String RUNMANGERSHEET = "RUNMANAGER";
	private static final String ITERATIONDATASHEET = "DATA";
	private static final String EXTENTREPORTFOLDERPATH = System.getProperty("user.dir")+"/extent-test-output/";
	private static String extentReportFilePath = "";
	

	public static String getExtentReportFilePath()  {
		if(extentReportFilePath.isEmpty()) {
			extentReportFilePath = createReportPath();
		}
		return extentReportFilePath;
	}
	private static String createReportPath()  {
		if(PropertyUtils.get(ConfigProperties.OVERRIDEREPORTS).equalsIgnoreCase("no")) {
			return EXTENTREPORTFOLDERPATH+System.currentTimeMillis()+"/index.html";
		}
		else {
			return EXTENTREPORTFOLDERPATH+"/index.html";
		}
	}
	
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
