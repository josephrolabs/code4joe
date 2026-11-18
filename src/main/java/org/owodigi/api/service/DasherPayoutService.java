package org.owodigi.api.service;

import org.owodigi.api.model.Delivery;

import java.math.BigDecimal;
import java.util.List;

public class DasherPayoutService implements PayoutService {

    @Override
    public BigDecimal calculatePayout(final List<Delivery> deliveries) {
        BigDecimal totalPay = new BigDecimal(0);
        for (final Delivery delivery : deliveries) {
            final BigDecimal basePay = delivery.peakHour() ? delivery.basePay().multiply(BigDecimal.TWO) : delivery.basePay();
            final BigDecimal deliveryPay = basePay.add(delivery.tip());
            totalPay = totalPay.add(deliveryPay);
        }
        return totalPay;
    }
}
