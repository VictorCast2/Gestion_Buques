package com.app.controller;

import com.app.dto.request.MuelleRequest;
import com.app.service.MuelleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registro-muelle")
public class RegistroMuelleRestController {

    @Autowired
    private MuelleService muelleService;

    @GetMapping("/{id}")
    public ResponseEntity<MuelleRequest> getMuelleRequest(@PathVariable String id) {
        return new ResponseEntity<>(this.muelleService.getMuelleRequestById(id), HttpStatus.OK);
    }
}
