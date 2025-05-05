package com.app.controller;

import com.app.collections.Atraque.Atraque;
import com.app.service.AtraqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/solicitud-atraque")
public class AtraqueRestController {

    @Autowired
    private AtraqueService atraqueService;

    @GetMapping("/{id}")
    public ResponseEntity<Atraque> getSolicitudAtraque(@PathVariable String id) {
        return new ResponseEntity<>(this.atraqueService.getAtraqueById(id), HttpStatus.OK);
    }
}
