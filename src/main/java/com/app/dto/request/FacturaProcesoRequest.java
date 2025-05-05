package com.app.dto.request;

import jakarta.validation.Valid;

import java.util.List;

public record FacturaProcesoRequest(@Valid List<ProcesoRequest> procesoRequests) {
}
