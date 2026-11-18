package org.owodigi.api.service;

import org.owodigi.api.model.Delivery;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            if (delivery == null) {
                System.out.println("\tskipping...delivery is null");
                continue;
            }
            final BigDecimal basePay = delivery.peakHour() ? delivery.basePay().multiply(BigDecimal.TWO) : delivery.basePay();
            final BigDecimal deliveryPay = basePay.add(delivery.tip());
            totalPay = totalPay.add(deliveryPay);
        }
        return totalPay;
    }

    @Override
    public Map<String, BigDecimal> calculatePayoutByDasher(List<Delivery> deliveries) {
        if (deliveries == null) {
            throw new IllegalArgumentException("deliveries is null");
        }
        Map<String, BigDecimal> payouts = new HashMap<>();
        if (deliveries.isEmpty()) {
            return Map.of();
        }
        for (final Delivery delivery : deliveries) {
            if (delivery == null) {
                System.out.println("\tskipping...delivery is null");
                continue;
            }
            BigDecimal dasherPayout = payouts.get(delivery.dasherId());
            if (dasherPayout == null) {
                dasherPayout = BigDecimal.ZERO;
                payouts.put(delivery.dasherId(), dasherPayout);
            }
            dasherPayout = dasherPayout.add(calculatePayout(delivery));
            payouts.put(delivery.dasherId(), dasherPayout);
        }
        return payouts;
    }

    private BigDecimal calculatePayout(final Delivery delivery) {
        final BigDecimal basePay = delivery.peakHour() ? delivery.basePay().multiply(BigDecimal.TWO) : delivery.basePay();
        return basePay.add(delivery.tip());
    }
}
