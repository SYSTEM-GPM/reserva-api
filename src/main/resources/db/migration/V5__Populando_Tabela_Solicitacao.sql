
INSERT INTO `solicitacao` (`id`, `data_devolucao`, `data_final`, `data_inicio`, `data_retirada`, `data_solicitacao`, `finalidade`, `autorizacao`, `solicitante_id`) VALUES
	(1, NULL, '2023-04-25 12:00:00.000000', '2023-04-25 10:00:00.000000', NULL, '2023-04-21 00:15:12.552827', ' Viagem para tabalho de campo.', 'SOLICITADO', 1),
	(2, '2023-05-02 18:00:00.000000', '2023-05-02 08:00:00.000000', '2023-05-02 07:00:00.000000', '2023-05-02 07:30:00.000000', '2023-04-21 12:30:00.000000', 'Participação em congresso fora da cidade.', 'DEVOLVIDO', 2),
	(3, NULL, '2023-05-06 22:00:00.000000', '2023-05-06 18:00:00.000000', NULL, '2023-04-22 09:00:00.000000', 'Viagem de negócios.', 'SOLICITADO', 3),
	(4, '2023-05-08 16:00:00.000000', '2023-05-08 08:00:00.000000', '2023-05-08 07:00:00.000000', '2023-05-08 07:30:00.000000', '2023-04-22 15:45:12.000000', 'Visita técnica a cliente fora da cidade.', 'DEVOLVIDO', 4),
	(5, NULL, '2023-05-12 12:00:00.000000', '2023-05-12 10:00:00.000000', NULL, '2023-04-23 09:13:21.123456', 'Participação em workshop.', 'SOLICITADO', 1),
	(6, NULL, '2023-05-14 12:00:00.000000', '2023-05-14 10:00:00.000000', NULL, '2023-04-23 10:30:00.000000', 'Participação em seminário.', 'SOLICITADO', 2),
	(7, '2023-05-15 18:00:00.000000', '2023-05-15 08:00:00.000000', '2023-05-15 07:00:00.000000', '2023-05-15 07:30:00.000000', '2023-04-23 14:30:00.000000', 'Visita a cliente fora da cidade.', 'DEVOLVIDO', 3),
	(8, NULL, '2023-05-20 12:00:00.000000', '2023-05-20 10:00:00.000000', NULL, '2023-04-24 16:40:12.987654', 'Participação em curso.', 'SOLICITADO', 4),
	(9, NULL, '2023-05-25 12:00:00.000000', '2023-05-25 10:00:00.000000', NULL, '2023-04-25 08:00:00.000000', 'Participação em conferência.', 'SOLICITADO', 1),
	(10, NULL, '2023-06-01 12:00:00.000000', '2023-06-01 10:00:00.000000', NULL, '2023-04-25 14:30:00.000000', 'Participação em reunião com fornecedor.', 'SOLICITADO', 2),
	(11, '2023-06-05 16:00:00.000000', '2023-06-05 08:00:00.000000', '2023-06-05 07:00:00.000000', '2023-06-05 07:30:00.000000', '2023-04-26 11:45:00.000000', 'Viagem para atender cliente fora da cidade.', 'DEVOLVIDO', 3),
	(12, NULL, '2023-06-08 12:00:00.000000', '2023-06-08 10:00:00.000000', NULL, '2023-04-26 17:00:00.000000', 'Participação em treinamento.', 'SOLICITADO', 4),
	(13, NULL, '2023-06-12 12:00:00.000000', '2023-06-12 10:00:00.000000', NULL, '2023-04-27 08:15:00.000000', 'Participação em workshop.', 'SOLICITADO', 1),
	(14, NULL, '2023-06-15 12:00:00.000000', '2023-06-15 10:00:00.000000', NULL, '2023-04-27 14:30:00.000000', 'Participação em palestra.', 'SOLICITADO', 2),
	(15, '2023-06-20 16:00:00.000000', '2023-06-20 08:00:00.000000', '2023-06-20 07:00:00.000000', '2023-06-20 07:30:00.000000', '2023-04-28 10:00:00.000000', 'Viagem para fechar negócio com fornecedor.', 'DEVOLVIDO', 3),
	(16, NULL, '2023-06-24 12:00:00.000000', '2023-06-24 10:00:00.000000', NULL, '2023-04-29 09:20:00.000000', 'Participação em curso.', 'SOLICITADO', 4),
	(17, NULL, '2023-06-28 12:00:00.000000', '2023-06-28 10:00:00.000000', NULL, '2023-04-29 15:00:00.000000', 'Participação em seminário.', 'SOLICITADO', 1),
	(18, NULL, '2023-05-20 12:00:00.000000', '2023-05-20 10:00:00.000000', NULL, '2023-04-28 15:00:00.000000', 'Participação em feira de negócios.', 'SOLICITADO', 3),
	(19, '2023-06-10 18:00:00.000000', '2023-06-10 08:00:00.000000', '2023-06-10 07:00:00.000000', '2023-06-10 07:30:00.000000', '2023-04-29 12:30:00.000000', 'Visita técnica a fornecedor fora da cidade.', 'DEVOLVIDO', 4),
	(20, NULL, '2023-06-15 12:00:00.000000', '2023-06-15 10:00:00.000000', NULL, '2023-04-30 09:45:00.000000', 'Participação em conferência.', 'SOLICITADO', 2);

INSERT INTO `solicitacao_recurso` (`solicitacao_id`, `recurso_id`) VALUES
	(1, 1);

INSERT INTO `solicitacao_recurso` (`solicitacao_id`, `recurso_id`) VALUES
	(2, 1);

INSERT INTO `solicitacao_recurso` (`solicitacao_id`, `recurso_id`) VALUES
	(3, 2);

INSERT INTO `solicitacao_recurso` (`solicitacao_id`, `recurso_id`) VALUES
	(4, 1);

INSERT INTO `solicitacao_recurso` (`solicitacao_id`, `recurso_id`) VALUES
	(5, 1);
	
INSERT INTO `solicitacao_recurso` (`solicitacao_id`, `recurso_id`) VALUES
	(6, 2);
	

INSERT INTO `solicitacao_recurso` (`solicitacao_id`, `recurso_id`) VALUES
	(7, 3),
	(7, 6);

INSERT INTO `solicitacao_recurso` (`solicitacao_id`, `recurso_id`) VALUES
	(8, 3),
	(8, 7);

INSERT INTO `solicitacao_recurso` (`solicitacao_id`, `recurso_id`) VALUES
	(9, 3),
	(9, 7);

INSERT INTO `solicitacao_recurso` (`solicitacao_id`, `recurso_id`) VALUES
	(10, 3),
	(10, 7);

INSERT INTO `solicitacao_recurso` (`solicitacao_id`, `recurso_id`) VALUES
	(11, 3),
	(11, 7);

INSERT INTO `solicitacao_recurso` (`solicitacao_id`, `recurso_id`) VALUES
	(12, 3),
	(12, 7);

INSERT INTO `solicitacao_recurso` (`solicitacao_id`, `recurso_id`) VALUES
	(13, 3),
	(13, 7);

INSERT INTO `solicitacao_recurso` (`solicitacao_id`, `recurso_id`) VALUES
	(14, 3),
	(14, 7);

INSERT INTO `solicitacao_recurso` (`solicitacao_id`, `recurso_id`) VALUES
	(15, 3),
	(15, 7);

INSERT INTO `solicitacao_recurso` (`solicitacao_id`, `recurso_id`) VALUES
	(16, 3),
	(16, 7);

INSERT INTO `solicitacao_recurso` (`solicitacao_id`, `recurso_id`) VALUES
	(17, 3),
	(17, 7);	

INSERT INTO `solicitacao_recurso` (`solicitacao_id`, `recurso_id`) VALUES
	(18, 3),
	(18, 7);

INSERT INTO `solicitacao_recurso` (`solicitacao_id`, `recurso_id`) VALUES
	(19, 3),
	(19, 7);

INSERT INTO `solicitacao_recurso` (`solicitacao_id`, `recurso_id`) VALUES
	(20, 3),
	(20, 8),
	(20, 7);	
	