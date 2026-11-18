package org.owodigi.api;

import org.owodigi.api.model.Delivery;
import org.owodigi.api.service.DasherPayoutService;
import org.owodigi.api.service.PayoutService;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final PayoutService service = new DasherPayoutService();
        testEmptyList(service);
        testBasePay(service);
        testTip(service);
        testPeakHour(service);
        System.out.println("All tests passed.");
    }

    private static void testPeakHour(final PayoutService service) {
        final String deliveryId = "";
        final String dasherId = "";
        final BigDecimal basePay = BigDecimal.ONE;
        final BigDecimal tip = BigDecimal.ONE;
        final boolean peakHour = true;
        final Delivery delivery = new Delivery(deliveryId, dasherId, basePay, tip, peakHour);
        final List<Delivery> input = List.of(delivery);
        final BigDecimal expected = BigDecimal.valueOf(3);
        test(input, expected, service, "testTip");
    }

    private static void testTip(final PayoutService service) {
        final String deliveryId = "";
        final String dasherId = "";
        final BigDecimal basePay = BigDecimal.ONE;
        final BigDecimal tip = BigDecimal.ONE;
        final boolean peakHour = false;
        final Delivery delivery = new Delivery(deliveryId, dasherId, basePay, tip, peakHour);
        final List<Delivery> input = List.of(delivery);
        test(input, BigDecimal.TWO, service, "testTip");

    }

    private static void testBasePay(final PayoutService service) {
        final String deliveryId = "";
        final String dasherId = "";
        final BigDecimal basePay = BigDecimal.ONE;
        final BigDecimal tip = BigDecimal.ZERO;
        final boolean peakHour = false;
        final Delivery delivery = new Delivery(deliveryId, dasherId, basePay, tip, peakHour);
        final List<Delivery> input = List.of(delivery);
        test(input, BigDecimal.ONE, service, "testBasePay");
    }

    private static void testEmptyList(final PayoutService service) {
        final List<Delivery> input = List.of();
        final BigDecimal expected = BigDecimal.ZERO;
        test(input, expected, service, "testEmptyList");
    }

    private static void test(final List<Delivery> input, final BigDecimal expected, final PayoutService service, final String message) {
        System.out.println(message);
        final BigDecimal actual = service.calculatePayout(input);
        assertEquals(expected, actual, message);
    }

    private static void assertEquals(final BigDecimal expected, final BigDecimal actual, final String message) {
        if (!actual.equals(expected)) {
            throw new AssertionError(message + ": failure. Expected: " + expected + "; actual: " + actual);
        }
    }
}