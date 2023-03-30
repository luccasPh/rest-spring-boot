CREATE TABLE
  `person` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `first_name` varchar(255) NOT NULL,
    `last_name` varchar(255) NOT NULL,
    `address` varchar(100) NOT NULL,
    `gender` varchar(6) NOT NULL,
    `birth_date` date DEFAULT NULL,
    PRIMARY KEY (`id`)
  )