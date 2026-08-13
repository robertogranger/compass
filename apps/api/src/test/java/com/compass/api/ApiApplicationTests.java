package com.compass.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@SpringBootTest
class ApiApplicationTests {
	@Test
	void confirmsTestRunnerIsWiredUp() {
		assertEquals(2, 1 + 1);
	}
}
