package com.tmb.reports;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.tmb.enums.ConfigProperties;
import com.tmb.enums.LogType;
import com.tmb.utils.PropertyUtils;
import com.tmb.utils.ScreenshotUtils;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

public final class FrameworkLogger {

    private FrameworkLogger(){}

    private static final Consumer<String> PASS = (message)->ExtentManager.getExtentTest().pass(message);
    private static final Consumer<String> FAIL = (message)->ExtentManager.getExtentTest().fail(message);
    private static final Consumer<String> SKIP = (message)->ExtentManager.getExtentTest().skip(message);
    private static final Consumer<String> INFO = (message)->ExtentManager.getExtentTest().info(message);
    private static final Consumer<String> CONSOLE = (message) -> System.out.println("INFO---->"+message);
    private static final Consumer<String> EXTENTANDCONSOLE = PASS.andThen(CONSOLE);
    private static final Consumer<String> TAKESCREENSHOT = (message)-> ExtentManager.getExtentTest().info("",
            MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());

    private static final Map<LogType,Consumer<String>> MAP = new EnumMap<>(LogType.class);
    private static final Map<LogType,Consumer<String>> SCREENSHOTMAP = new EnumMap<>(LogType.class);

    static {
        MAP.put(LogType.PASS, PASS);
        MAP.put(LogType.FAIL, FAIL.andThen(TAKESCREENSHOT));
        MAP.put(LogType.SKIP, SKIP);
        MAP.put(LogType.INFO, INFO);
        MAP.put(LogType.CONSOLE, CONSOLE);
        MAP.put(LogType.EXTENTANDCONSOLE, EXTENTANDCONSOLE);
        SCREENSHOTMAP.put(LogType.PASS, PASS.andThen(TAKESCREENSHOT));
        SCREENSHOTMAP.put(LogType.FAIL, FAIL.andThen(TAKESCREENSHOT));
        SCREENSHOTMAP.put(LogType.SKIP, SKIP.andThen(TAKESCREENSHOT));
        SCREENSHOTMAP.put(LogType.INFO, INFO);
        SCREENSHOTMAP.put(LogType.CONSOLE, CONSOLE);
        SCREENSHOTMAP.put(LogType.EXTENTANDCONSOLE, EXTENTANDCONSOLE.andThen(TAKESCREENSHOT));
    }

    public static void log(LogType status, String message) {
        if(!PropertyUtils.get(ConfigProperties.PASSEDSTEPSSCREENSHOTS).equalsIgnoreCase("yes")) {
            MAP.getOrDefault(status,EXTENTANDCONSOLE).accept(message);
        }
        else{
            SCREENSHOTMAP.getOrDefault(status,EXTENTANDCONSOLE).accept(message);
        }


    }

}
