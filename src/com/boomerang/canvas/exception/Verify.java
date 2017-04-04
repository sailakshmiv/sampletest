package com.boomerang.canvas.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;

public class Verify {

	private static Map<ITestResult, List<String>> verificationFailuresMap = new HashMap<ITestResult, List<String>>();

	public static void assertTrue(boolean condition) {
		Assert.assertTrue(condition);
	}

	public static void assertFalse(boolean condition) {
		Assert.assertFalse(condition);
	}

	public static void assertEquals(int actual, int expected) {
		Assert.assertEquals(actual, expected, "Assert occured. Mismatch found : ");
	}

	public static void assertEquals(Object actual, Object expected) {
		Assert.assertEquals(actual, expected, "Failed message printed : ");
	}

	public static void verifyTrue(boolean condition) {
		try {
			assertTrue(condition);
		} catch (Throwable e) {
			addVerificationFailure(e.getMessage());
		}
	}

	public static void verifyFalse(boolean condition) {
		try {
			assertFalse(condition);
		} catch (Throwable e) {
			addVerificationFailure(e.getMessage());
		}
	}

	public static void verifyEquals(Object actual, Object expected) {
		try {
			assertEquals(actual, expected);
		} catch (Throwable e) {
			addVerificationFailure(e.getMessage());
		}
	}

	public static void verifyEquals(Object actual, Object expected, String message) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (Throwable e) {
			addVerificationFailure(e.getMessage());
		}
	}

	public static void verifyNotEquals(Object actual, Object expected, String message) {
		try {
			Assert.assertNotEquals(actual, expected, message);
		} catch (Throwable e) {
			addVerificationFailure(e.getMessage());
		}
	}

	public static void verifyNotEquals(Object actual, Object expected) {
		try {
			Assert.assertNotEquals(actual, expected);
		} catch (Throwable e) {
			addVerificationFailure(e.getMessage());
		}
	}

	public static void verifyEquals(int actual, int expected, String message) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (Throwable e) {
			addVerificationFailure(e.getMessage());
		}
	}

	public static void verifyEquals(boolean actual, boolean expected, String message) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (Throwable e) {
			addVerificationFailure(e.getMessage());
		}
	}

	public static void fail(String message) {
		try {
			Assert.fail(message);
		} catch (Throwable e) {
			addVerificationFailure(e.getMessage());
		}
	}

	public static List<String> getVerificationFailures() {
		List<String> verificationFailures = verificationFailuresMap.get(Reporter.getCurrentTestResult());
		return verificationFailures == null ? new ArrayList<String>() : verificationFailures;
	}

	public static void addVerificationFailure(String e) {
		List<String> verificationFailures = getVerificationFailures();
		verificationFailuresMap.put(Reporter.getCurrentTestResult(), verificationFailures);
		verificationFailures.add(e);
	}

}
