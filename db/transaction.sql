-- `flip.id`.`transaction` definition

CREATE TABLE `transaction` (
  `trx_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user1` varchar(12) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user2` varchar(12) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trx_type` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `category` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nominal` decimal(17,0) DEFAULT NULL,
  `notes` text COLLATE utf8mb4_unicode_ci,
  `created_date` datetime DEFAULT NULL,
  PRIMARY KEY (`trx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;