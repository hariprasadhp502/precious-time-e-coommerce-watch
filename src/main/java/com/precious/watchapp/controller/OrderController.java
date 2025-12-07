package com.precious.watchapp.controller;

import com.precious.watchapp.model.Order;
import com.precious.watchapp.repository.OrderRepository;
import com.precious.watchapp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/orders") // Base path for all order-related endpoints
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EmailService emailService;

    private final Random random = new Random();

    // 1️⃣ Place order → generate OTP → send OTP email
    @PostMapping("/place")
    public ApiResponse placeOrder(@RequestBody Order order) {
        // Generate 6-digit OTP
        String otp = String.format("%06d", random.nextInt(900000) + 100000);
        order.setOtp(otp);
        order.setOtpVerified(false);

        orderRepository.save(order);

        // Send OTP email
        emailService.sendOtpEmail(order.getEmail(), otp);

        return new ApiResponse(true, "OTP sent to your email!");
    }

    // 2️⃣ Verify OTP → confirm order → send confirmation email
    @PostMapping("/verify")
    public ApiResponse verifyOtp(@RequestBody VerifyRequest request) {
        Optional<Order> optionalOrder = orderRepository.findByEmailAndOtp(request.getEmail(), request.getOtp());

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setOtpVerified(true);

            // Set delivery date 2 days from now
            LocalDate deliveryDate = LocalDate.now().plusDays(2);
            order.setDeliveryDate(deliveryDate);

            orderRepository.save(order);

            // Send confirmation email
            emailService.sendConfirmationEmail(order.getEmail(), order.getName(), deliveryDate.toString());

            return new ApiResponse(true, "OTP verified. Confirmation email sent!");
        } else {
            return new ApiResponse(false, "Incorrect OTP!");
        }
    }

    // Helper classes

    // Request body for verifying OTP
    public static class VerifyRequest {
        private String email;
        private String otp;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getOtp() { return otp; }
        public void setOtp(String otp) { this.otp = otp; }
    }

    // Standard API response
    public static class ApiResponse {
        private boolean success;
        private String message;

        public ApiResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
