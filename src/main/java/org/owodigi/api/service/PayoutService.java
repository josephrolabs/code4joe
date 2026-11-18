package org.owodigi.api.service;

import org.owodigi.api.model.Delivery;

import java.math.BigDecimal;
import java.util.List;

public interface PayoutService {
    public BigDecimal calculatePayout(List<Delivery> deliveries);
}
