-- Copiando estrutura do banco de dados para reserva_data
USE `reserva_data`;

-- Copiando estrutura para tabela reserva_data.setor
CREATE TABLE IF NOT EXISTS `setor` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Exportação de dados foi desmarcado.

-- Copiando estrutura para tabela reserva_data.recurso
CREATE TABLE IF NOT EXISTS `recurso` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Exportação de dados foi desmarcado.

-- Copiando estrutura para tabela reserva_data.local
CREATE TABLE IF NOT EXISTS `local` (
  `identificacao` varchar(255) DEFAULT NULL,
  `total_de_assento` int DEFAULT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK64ulxeq5yeaquultv3c2hjs7n` FOREIGN KEY (`id`) REFERENCES `recurso` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Exportação de dados foi desmarcado.

-- Copiando estrutura para tabela reserva_data.pessoa
CREATE TABLE IF NOT EXISTS `pessoa` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cpf` varchar(255) DEFAULT NULL,
  `data_nascimento` date DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `siape` varchar(255) DEFAULT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `tipo_perfil` varchar(255) DEFAULT NULL,
  `setor_id` bigint DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `codigo_ativacao` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKc2uiam562pt4rl1dnt0lmkrw5` (`setor_id`),
  CONSTRAINT `FKc2uiam562pt4rl1dnt0lmkrw5` FOREIGN KEY (`setor_id`) REFERENCES `setor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Exportação de dados foi desmarcado.


-- Copiando estrutura para tabela reserva_data.motorista
CREATE TABLE IF NOT EXISTS `motorista` (
  `numero_cnh` varchar(255) NOT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK17hbtyviuele056hlike77qnx` FOREIGN KEY (`id`) REFERENCES `pessoa` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Exportação de dados foi desmarcado.


-- Copiando estrutura para tabela reserva_data.permissao
CREATE TABLE IF NOT EXISTS `permissao` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Exportação de dados foi desmarcado.


-- Copiando estrutura para tabela reserva_data.equipamento
CREATE TABLE IF NOT EXISTS `equipamento` (
  `numero_patrimonio` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKkknkffoeulexqul5edpwjcssr` FOREIGN KEY (`id`) REFERENCES `recurso` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Copiando estrutura para tabela reserva_data.solicitacao
CREATE TABLE IF NOT EXISTS `solicitacao` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data_devolucao` datetime(6) DEFAULT NULL,
  `data_final` datetime(6) NOT NULL,
  `data_inicio` datetime(6) NOT NULL,
  `data_retirada` datetime(6) DEFAULT NULL,
  `data_solicitacao` datetime(6) DEFAULT NULL,
  `finalidade` varchar(255) DEFAULT NULL,
  `externo` varchar(255) DEFAULT NULL,
  `autorizacao` varchar(255) DEFAULT NULL,
  `justificativa` varchar(255) DEFAULT NULL,
  `solicitante_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKef4xqfy5wf4fqe8a3vjut0a04` (`solicitante_id`),
  CONSTRAINT `FKef4xqfy5wf4fqe8a3vjut0a04` FOREIGN KEY (`solicitante_id`) REFERENCES `pessoa` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
-- Exportação de dados foi desmarcado.

-- Copiando estrutura para tabela reserva_data.solicitacao_recurso
CREATE TABLE IF NOT EXISTS `solicitacao_recurso` (
  `solicitacao_id` bigint NOT NULL,
  `recurso_id` bigint NOT NULL,
  PRIMARY KEY (`solicitacao_id`,`recurso_id`),
  KEY `FK5t083p1yygnr33hbr7embhx05` (`recurso_id`),
  CONSTRAINT `FK5t083p1yygnr33hbr7embhx05` FOREIGN KEY (`recurso_id`) REFERENCES `recurso` (`id`),
  CONSTRAINT `FKft9r728s010tdo65t5mtbio9` FOREIGN KEY (`solicitacao_id`) REFERENCES `solicitacao` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Exportação de dados foi desmarcado.

-- Copiando estrutura para tabela reserva_data.transporte
CREATE TABLE IF NOT EXISTS `transporte` (
  `placa` varchar(255) DEFAULT NULL,
  `total_de_assentos` int DEFAULT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKsrypgm22oyhxa96ngt3vjlled` FOREIGN KEY (`id`) REFERENCES `recurso` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Exportação de dados foi desmarcado.

-- Copiando estrutura para tabela reserva_data.usuario_permissao
CREATE TABLE IF NOT EXISTS `usuario_permissao` (
  `id_usuario` bigint NOT NULL,
  `id_permissao` bigint NOT NULL,
  PRIMARY KEY (`id_usuario`,`id_permissao`),
  KEY `FKjvcxjnrmdhdv6eti5d7svm5xw` (`id_permissao`),
  CONSTRAINT `FKbo8hww1whbpxq8ancjokhnfds` FOREIGN KEY (`id_usuario`) REFERENCES `pessoa` (`id`),
  CONSTRAINT `FKjvcxjnrmdhdv6eti5d7svm5xw` FOREIGN KEY (`id_permissao`) REFERENCES `permissao` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Exportação de dados foi desmarcado.

-- Copiando estrutura para tabela reserva_data.viagem
CREATE TABLE IF NOT EXISTS `viagem` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `saida` varchar(255) DEFAULT NULL,
  `destino` varchar(255) DEFAULT NULL,
  `motorista_id` bigint DEFAULT NULL,
  `solicitacao_id` bigint DEFAULT NULL,
  `transporte_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk4vj43tm5w0b5v2b9xwl5h9x2` (`motorista_id`),
  KEY `FK2s41hukn0jbgxyaqstv7wi2j0` (`solicitacao_id`),
  KEY `FKnyofqlvlfpap4x4ybuw18d134` (`transporte_id`),
  CONSTRAINT `FK2s41hukn0jbgxyaqstv7wi2j0` FOREIGN KEY (`solicitacao_id`) REFERENCES `solicitacao` (`id`),
  CONSTRAINT `FKk4vj43tm5w0b5v2b9xwl5h9x2` FOREIGN KEY (`motorista_id`) REFERENCES `motorista` (`id`),
  CONSTRAINT `FKnyofqlvlfpap4x4ybuw18d134` FOREIGN KEY (`transporte_id`) REFERENCES `transporte` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Copiando estrutura para tabela reserva_data.passageiro
CREATE TABLE IF NOT EXISTS `passageiro` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `viagem_id` bigint NOT NULL,
  `cpf` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKgl77bbt9lacqbqdx3ctud58ax` FOREIGN KEY (`viagem_id`) REFERENCES `viagem` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Exportação de dados foi desmarcado.