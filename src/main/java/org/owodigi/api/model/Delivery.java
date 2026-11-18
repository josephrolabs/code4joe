package org.owodigi.api.model;

import java.math.BigDecimal;

public record Delivery(String deliveryId, String dasherId, BigDecimal basePay, BigDecimal tip, boolean peakHour) { }
