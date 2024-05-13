package com.wust.advanced.web;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class WorkflowTest {

	@Test
	void workflowTest() {
		assertEquals(4, 2+2);
	}

}