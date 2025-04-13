package org.example.service;

import static java.nio.file.StandardOpenOption.*;
import static java.util.concurrent.TimeUnit.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.example.exception.LogServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LogService {

  private final Path filePath;

  ReentrantReadWriteLock reentrantLock = new ReentrantReadWriteLock();

  public LogService(@Value("${file.path}") Path filePath) {
    this.filePath = filePath;
  }

  public void writeLog(String message) {
    try {
      if (!reentrantLock.writeLock().tryLock(3, SECONDS)) {
        throw new LogServiceException("Timeout acquiring write lock");
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();

      throw new LogServiceException("Thread was interrupted while waiting for lock", e);
    }

    try {
      Files.writeString(filePath, message + "\n", CREATE, APPEND);
    } catch (IOException e) {
      throw new LogServiceException("Failed to write log: ", e);
    } finally {
      reentrantLock.writeLock().unlock();
    }
  }

  public String readLogs() {
    reentrantLock.readLock().lock();
    try {
      return Files.readString(filePath);
    } catch (IOException e) {
      throw new LogServiceException("Failed to read logs: ", e);
    } finally {
      reentrantLock.readLock().unlock();
    }
  }
}
