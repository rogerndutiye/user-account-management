-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 14, 2023 at 01:24 PM
-- Server version: 8.0.32-0ubuntu0.20.04.2
-- PHP Version: 8.1.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `paymentapi`
--

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `callback_url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `callback_token` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `email_verified_at`, `username`, `password`, `callback_url`, `callback_token`, `remember_token`, `created_at`, `updated_at`) VALUES
(1, 'admin', 'admin@gpay.com', NULL, '$2y$10$OyANve7YQMBHpttn4UQQJOkAldhA4gCDXZnkI0N04/t0aS5eQyEem', '$2y$10$ba.Qkte98KBm2LINfC8sNOQMJ99B12LL9lG8UuH0bsNh1EKrX8BGK', 'http://api.koipay.co:3000/api/v1/mobile/daily-transactions/callback', 'Gbiprwwv3WGNrha487jBp5e2Pb9d5DRutf3NeB5SlY', NULL, '2023-03-17 09:26:19', '2023-03-17 09:26:19'),
(4, 'test', 'test@gmail.com', NULL, '$2y$10$qiu9/KUBDC3jAEsa74eQa.t4InXFWpe7fi8YoLGaAhnMl9nX9nF.q', '$2y$10$aEqmpWxVt.1PTY8OwQ1E3.xssUIniUX28MSy9OW7BEMGb1KfrmBIC', 'http://pay-api.test/api/v1/test_callBack', 'Gbiprwwv3WGNrha487jBp5e2Pb9d5DRutf3NeB5SlY', NULL, '2023-04-07 22:30:03', '2023-04-07 22:30:03'),
(5, 'matewusi', 'nostalgie.patrice@gmail.com', NULL, '$2y$10$XeGdaqNrHcg/6ITg0VIavO1GU7A317l5L8hoJBMG2GYUHo.cPsOLS', '$2y$10$TRXXsg38XGJq0QtNYLrD7ulIpqM/RGcYJ9grlG4dJodYKQVhlK/M2', 'http://pay-api.test/api/v1/test_callBack', 'Gbiprwwv3WGNrha487jBp5e2Pb9d5DRutf3NeB5SlY', NULL, '2023-04-10 13:27:04', '2023-04-10 13:27:04'),
(6, 'Sawa', 'nostalgie@hviewtech.com', NULL, '$2y$10$oAfJ7s10LQBETvMQFETiSuI4/mzhI5BuU8bY49wuoK3UXLX3jWS3y', '$2y$10$vzoE8LlRQEVpdpGogHhl2O9G9mBR.2QR90YKSuavKPPT/Q0l.A7P2', 'http://pay-api.test/api/v1/test_callBack', 'Gbiprwwv3WGNrha487jBp5e2Pb9d5DRutf3NeB5SlY', NULL, '2023-04-10 13:37:27', '2023-04-10 13:37:27');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_email_unique` (`email`),
  ADD UNIQUE KEY `users_username_unique` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
