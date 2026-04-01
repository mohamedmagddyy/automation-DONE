package com.DoneProject.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtils {

    // ✅ منع إنشاء instance — Utility class
    private LogUtils() {}

    public static final Logger log = LogManager.getLogger(LogUtils.class);
}
