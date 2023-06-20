package reserva_api.dtos.projection;

import reserva_api.models.enums.StatusSolicitacao;

import java.time.LocalDateTime;

public interface SolicitacaoLocalProjection {

    Long getsolicitacao_id();
    LocalDateTime getdata_final();
    LocalDateTime getdata_inicio();
    LocalDateTime getdata_solicitacao();
    String getfinalidade();
    String getexterno();
    StatusSolicitacao getautorizacao();
    Long getlocal_id();
    String getlocal();
    String getidentificacao();
    String gettotal_de_assento();
    Long getsolicitante_id();
    String getsolicitante();
    String getcpf();
    String getsiape();
    String gettelefone();
}
