package testcases;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listeners implements ITestListener {
    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println(iTestResult.getName() + " test case started");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("The name of the testcase passed is :" + iTestResult.getName());

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        System.out.println("The name of the testcase failed is :" + iTestResult.getName());

    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("The name of the testcase Skipped is :"+iTestResult.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }
}
