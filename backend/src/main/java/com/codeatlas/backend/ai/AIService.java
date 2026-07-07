package com.codeatlas.backend.ai;

import com.codeatlas.backend.scanner.ScannerResult;

public interface AIService {

    String generateSummary(ScannerResult scannerResult);

}