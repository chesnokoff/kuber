package org.example.controller;

import java.util.Map;
import org.example.dto.LogRequest;
import org.example.service.LogService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {

  private final LogService logService;

  private final String welcomeMessage;

  public LogController(LogService logService, @Value("${message.welcome}") String welcomeMessage) {
    this.logService = logService;
    this.welcomeMessage = welcomeMessage;
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
    logService.writeLog(request.message());
  }

  @GetMapping("/logs")
  public ResponseEntity<String> readLog () {
    return ResponseEntity.ok(logService.readLogs());
  }

}