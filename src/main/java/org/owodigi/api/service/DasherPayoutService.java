package org.owodigi.api.service;

import org.owodigi.api.model.Delivery;

import java.math.BigDecimal;
import java.util.List;

public class DasherPayoutService implements PayoutService {

    @Override
    public BigDecimal calculatePayout(final List<Delivery> deliveries) {
        if (deliveries == null) {
            throw new IllegalArgumentException("deliveries is null");
        }
        BigDecimal totalPay = BigDecimal.ZERO;
        if (deliveries.isEmpty()) {
            return totalPay;
        }
        for (final Delivery delivery : deliveries) {
            final BigDecimal basePay = delivery.peakHour() ? delivery.basePay().multiply(BigDecimal.TWO) : delivery.basePay();
            final BigDecimal deliveryPay = basePay.add(delivery.tip());
            totalPay = totalPay.add(deliveryPay);
        }
        return totalPay;
    }
}
