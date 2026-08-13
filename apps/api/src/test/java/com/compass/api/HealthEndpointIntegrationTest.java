package com.compass.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @SpringBootTest boots the FULL Spring application context — same as running
// the real app (real DataSource/Postgres connection, real Security filter chain,
// real Actuator endpoint registration). This is what makes this an integration
// test rather than a unit test: we're exercising how the pieces wire together,
// not testing a class in isolation.
@SpringBootTest
// MockMvc isn't auto-configured by @SpringBootTest alone — this annotation adds it,
// giving us a way to simulate HTTP requests against the app's controllers/endpoints
// WITHOUT actually binding to a real port/socket. Faster and simpler than spinning
// up a real server, while still going through the real Spring MVC request pipeline.
@AutoConfigureMockMvc
class HealthEndpointIntegrationTest {

    // Spring injects the configured MockMvc instance here — this is our entry
    // point for "sending" simulated requests in the test below.
    @Autowired
    private MockMvc mockMvc;

    @Test
    void healthEndpointReturnsUp() throws Exception {
        // .perform(get(...)) simulates an HTTP GET to /actuator/health, going through
        // the same Spring Security filters and DispatcherServlet routing a real
        // request would hit — this is what makes it more than a plain unit test.
        mockMvc.perform(get("/actuator/health"))
                // Confirms the endpoint responded with HTTP 200, not a 401 (Security
                // blocking it), 404 (wrong path), or 500 (something broke at startup).
                .andExpect(status().isOk())
                // Loose string-containment check on the raw response body. Actuator's
                // health response is JSON (e.g. {"status":"UP"}), but we're avoiding a
                // strict JSON assertion here since this is just a placeholder — once
                // real health indicators (DB connectivity, disk space, etc.) are wired
                // in, this should be upgraded to a proper jsonPath assertion instead,
                // e.g. jsonPath("$.status").value("UP").
                .andExpect(content().string(org.hamcrest.Matchers.containsString("UP")));
    }
}