package org.owodigi.api.service;

import org.owodigi.api.model.Delivery;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface PayoutService {
    public BigDecimal calculatePayout(List<Delivery> deliveries);
    public Map<String, BigDecimal> calculatePayoutByDasher(List<Delivery> deliveries);
}
