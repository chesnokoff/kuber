package org.example.controller;

import java.util.Map;
import org.example.dto.LogRequest;
import org.example.exception.LogServiceException;
import org.example.service.LogService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@RestController
public class LogController {

  private final Counter totalLogRequests;
  private final Counter successfulLogs;
  private final Counter failedLogs;

  private final LogService logService;

  private final String welcomeMessage;

  public LogController(LogService logService, MeterRegistry registry, @Value("${message.welcome}") String welcomeMessage) {
    this.logService = logService;
    this.welcomeMessage = welcomeMessage;

    this.totalLogRequests = registry.counter("log_requests_total");
    this.successfulLogs = registry.counter("log_success_total");
    this.failedLogs = registry.counter("log_failure_total");
  }

  @GetMapping("/")
  public String welcome() {
    return welcomeMessage;
  }

  @GetMapping("/status")
  public Map<String, String> status() {
    return Map.of("status", "ok");
  }

  @PostMapping("/log")
  public void writeLog(@RequestBody LogRequest request) {
    totalLogRequests.increment();
    try {
        logService.writeLog(request.message());
        successfulLogs.increment();
    } catch (LogServiceException e) {
        failedLogs.increment();
        throw e;
    }
  }

  @GetMapping("/logs")
  public ResponseEntity<String> readLog () {
    return ResponseEntity.ok(logService.readLogs());
  }

}