package com.DoneProject.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Logger    logger = LoggerFactory.getLogger(ConfigReader.class);
    private static final Properties prop  = new Properties();

    // ✅ منع إنشاء instance
    private ConfigReader() {}

    static {
        // ✅ الأولوية: ClassLoader (من الـ classpath) — يشتغل في IntelliJ وMaven
        InputStream stream = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties");

        if (stream != null) {
            try {
                prop.load(stream);
                logger.info("✅ تم تحميل config.properties من الـ classpath");
            } catch (IOException e) {
                throw new RuntimeException("❌ فشل قراءة config.properties: " + e.getMessage());
            }
        } else {
            // ✅ fallback: دور عليه بمسارات نسبية لو مش موجود في الـ classpath
            String[] fallbackPaths = {
                    "src/test/resources/config.properties",
                    "selenium/src/test/resources/config.properties",
                    "config.properties"
            };

            boolean loaded = false;
            for (String path : fallbackPaths) {
                try (FileInputStream fis = new FileInputStream(path)) {
                    prop.load(fis);
                    logger.info("✅ تم تحميل config.properties من: {}", path);
                    loaded = true;
                    break;
                } catch (IOException ignored) {
                    // جرّب المسار التالي
                }
            }

            if (!loaded) {
                throw new RuntimeException(
                        "❌ لم يتم العثور على config.properties\n" +
                                "تأكد إنه موجود في: src/test/resources/config.properties"
                );
            }
        }
    }

    // ================= Get Property =================
    public static String getProperty(String key) {
        String value = prop.getProperty(key);
        if (value == null) {
            logger.warn("⚠️ المفتاح '{}' غير موجود في config.properties", key);
        }
        return value;
    }

    // ================= Get Property with Default =================
    public static String getProperty(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }
}