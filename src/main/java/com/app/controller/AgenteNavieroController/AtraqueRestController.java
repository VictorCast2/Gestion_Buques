package com.app.controller.AgenteNavieroController;

import com.app.dto.request.AtraqueRequest;
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
    public ResponseEntity<AtraqueRequest> getSolicitudAtraque(@PathVariable String id) {
        return new ResponseEntity<>(this.atraqueService.getAtraqueRequestById(id), HttpStatus.OK);
    }
}
