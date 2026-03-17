package tests.DoneProject.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestListener implements ITestListener {

    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        logger.info("");
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.info("🚀 STARTING TEST: {}", testName);
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        logger.info("-----------------------------------------------------");
        logger.info("✅ TEST PASSED: {}", testName);
        logger.info("-----------------------------------------------------");
        logger.info("");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        logger.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        logger.error("❌ TEST FAILED: {}", testName);
        if (result.getThrowable() != null) {
            logger.error("❓ Reason: {}", result.getThrowable().getMessage());
        }
        logger.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        logger.error("");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        logger.warn("-----------------------------------------------------");
        logger.warn("⚠️ TEST SKIPPED: {}", testName);
        logger.warn("-----------------------------------------------------");
        logger.info("");
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("🏁 Test Suite Execution Started: {}", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("🏁 Test Suite Execution Finished: {}", context.getName());
        logger.info("📊 Summary: Passed={}, Failed={}, Skipped={}", 
            context.getPassedTests().size(), 
            context.getFailedTests().size(), 
            context.getSkippedTests().size());
    }
}
