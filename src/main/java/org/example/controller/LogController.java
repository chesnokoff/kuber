package org.example.controller;

import java.util.Map;
import org.example.dto.LogRequest;
import org.example.service.LogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {

  private final LogService logService;

  public LogController(LogService logService) {
    this.logService = logService;
  }

  @GetMapping("/")
  public String welcome() {
    return "Welcome to the custom app";
  }

  @GetMapping("/status")
  public Map<String, String> status() {
    return Map.of("status", "ok");
  }

  @PostMapping("/logs")
  public void writeLog(@RequestBody LogRequest request) {
    logService.writeLog(request.message());
  }

  @GetMapping("/logs")
  public ResponseEntity<String> readLog () {
    return ResponseEntity.ok(logService.readLogs());
  }

}