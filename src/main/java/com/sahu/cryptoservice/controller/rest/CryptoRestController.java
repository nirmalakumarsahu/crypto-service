package com.sahu.cryptoservice.controller.rest;

import com.sahu.cryptoservice.dto.ResourceResponse;
import com.sahu.cryptoservice.service.CryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/crypto")
@RequiredArgsConstructor
public class CryptoRestController {

    public final CryptoService cryptoService;

    @PostMapping("/encrypt")
    public ResponseEntity<Resource> zipFiles(@RequestParam("file") MultipartFile file) {
        ResourceResponse response = cryptoService.generateEncryptedFile(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + response.fileName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(response.resource());
    }

}
