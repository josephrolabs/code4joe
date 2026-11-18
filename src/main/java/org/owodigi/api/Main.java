package org.owodigi.api;

import org.owodigi.api.model.Delivery;
import org.owodigi.api.service.DasherPayoutService;
import org.owodigi.api.service.PayoutService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final PayoutService service = new DasherPayoutService();
        testEmptyList(service);
        testNullDeliveries(service);
        testNullDelivery(service);
        testBasePay(service);
        testTip(service);
        testPeakHour(service);
        testMultipleDeliveries(service);
        System.out.println("All tests passed.");
    }

    private static void testNullDelivery(final PayoutService service) {
        final List<Delivery> input = new ArrayList<>();
        input.add(null);
        input.add(new Delivery("", "", BigDecimal.ONE, BigDecimal.TEN, false));
        test(input, BigDecimal.valueOf(11), service, "testNullDelivery");
    }

    private static void testNullDeliveries(final PayoutService service) {
        test(null, null, service, "testNullDeliveries");
    }

    private static void testMultipleDeliveries(final PayoutService service) {
        final Delivery delivery1 = new Delivery("", "", BigDecimal.ONE, BigDecimal.TEN, false);
        final Delivery delivery2 = new Delivery("", "", BigDecimal.ONE, BigDecimal.TEN, false);
        final Delivery delivery3 = new Delivery("", "", BigDecimal.ONE, BigDecimal.TEN, false);
        final Delivery delivery4 = new Delivery("", "", BigDecimal.ONE, BigDecimal.TEN, false);
        final Delivery delivery5 = new Delivery("", "", BigDecimal.ONE, BigDecimal.TEN, false);
        final List<Delivery> input = List.of(delivery1, delivery2, delivery3, delivery4, delivery5);
        test(input, BigDecimal.valueOf(55), service, "testMultipleDeliveries");
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
        test(input, expected, service, "testPeakHour");
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
        try {
            final BigDecimal actual = service.calculatePayout(input);
            assertEquals(expected, actual, message);
        } catch (final Exception ex) {
            if (expected == null) {
                System.out.println("\tThrew expected exception " + ex);
            } else {
                throw new AssertionError("Threw unexpected exception " + ex);
            }
        }
    }

    private static void assertEquals(final BigDecimal expected, final BigDecimal actual, final String message) {
        if (actual.compareTo(expected) != 0) {
            throw new AssertionError(message + ": failure. Expected: " + expected + "; actual: " + actual);
        }
    }
}