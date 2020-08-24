package com.github.savitoh.centralerroapi.evento_log;

import com.github.savitoh.centralerroapi.evento_log.tipologlevel.TipoLogLevel;
import io.jsonwebtoken.lang.Assert;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.Objects;

final class EventoLogFiltroSpecifications {

    private EventoLogFiltroSpecifications() {

    }

    private static Specification<EventoLog> filtrarLogLevel(@NonNull TipoLogLevel tipoLogLevel) {
        return (root, query, builder) -> builder.equal(root.get("level"), tipoLogLevel);
    }

    private static Specification<EventoLog> filtrarDescricao(@NonNull String descricao) {
        return (root, query, builder) -> builder.like(root.get("descricao"), descricao);
    }

    private static Specification<EventoLog> filtrarLog(@NonNull String log) {
        return (root, query, builder) -> builder.like(root.get("log"), log);
    }

    private static Specification<EventoLog> filtroQuantidade(@NonNull Integer quantidade) {
        return (root, query, builder) -> builder.equal(root.get("quantidade"), quantidade);
    }

    private static Specification<EventoLog> filtroDataGeracao(@NonNull LocalDateTime dataGeracao) {
        return (root, query, builder) -> builder.equal(root.get("dataGeracao"), dataGeracao);
    }

    public static Specification<EventoLog> of(@NonNull EventoLogFiltro filtro) {
        Assert.notNull(filtro);
        return Specification
                .where(Objects.isNull(filtro.getLevel()) ? null : filtrarLogLevel(filtro.getLevel()))
                .and(Objects.isNull(filtro.getDataGeracao()) ? null : filtroDataGeracao(filtro.getDataGeracao()))
                .and(Objects.isNull(filtro.getQuantidade()) ? null : filtroQuantidade(filtro.getQuantidade()))
                .and(Objects.isNull(filtro.getDescricao()) ? null : filtrarDescricao(filtro.getDescricao()))
                .and(Objects.isNull(filtro.getLog()) ? null : filtrarLog(filtro.getLog()));
    }
}
