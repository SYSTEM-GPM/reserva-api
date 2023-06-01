CREATE TABLE IF NOT EXISTS `comunicacao_interna` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `assunto` varchar(255) DEFAULT NULL,
  `mensagem` varchar(255) DEFAULT NULL,
  `data_envio` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `pessoa_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`pessoa_id`) REFERENCES `pessoa` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;