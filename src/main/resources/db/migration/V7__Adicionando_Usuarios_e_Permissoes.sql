INSERT INTO `reserva_data`.`permissao` (`descricao`) VALUES ('ROLE_ADMINISTRADOR');
INSERT INTO `reserva_data`.`permissao` (`descricao`) VALUES ('ROLE_GERENTE');
INSERT INTO `reserva_data`.`permissao` (`descricao`) VALUES ('ROLE_MOTORISTA');
INSERT INTO `reserva_data`.`permissao` (`descricao`) VALUES ('ROLE_USUARIO');

INSERT INTO `reserva_data`.`usuario_permissao` (`id_usuario`, `id_permissao`) VALUES 
(1, 1),
(2, 2),
(3, 4),
(4, 3);	