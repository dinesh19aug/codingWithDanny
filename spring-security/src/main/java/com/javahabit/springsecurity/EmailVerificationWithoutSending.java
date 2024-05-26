package com.javahabit.springsecurity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EmailVerificationWithoutSending {
    public static void main(String[] args) {
        String domain = "acninc.com"; // Change this to the domain you want to verify

        try {
            // Execute dig +short MX command to get the SMTP server names
            Process process = new ProcessBuilder("dig", "+short", "MX", domain).start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length >= 2) {
                    String smtpServer = parts[1]; // Extract the SMTP server name
                    System.out.println("Verifying email on SMTP server: " + smtpServer);
                    verifyEmailExistence(smtpServer);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void verifyEmailExistence(String smtpServer) {
        try {
            // Connect to the SMTP server using telnet
            Process process = new ProcessBuilder("telnet", smtpServer, "25").start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            // Issue the VRFY command to verify email existence
            process.getOutputStream().write("VRFY darora@acninc.com\r\n".getBytes());
            process.getOutputStream().flush();

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            process.destroy();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
