package com.lucas.agendadortarefas.infrastructure.repository;

import com.lucas.agendadortarefas.infrastructure.entity.Tarefa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TarefaRepository extends MongoRepository<Tarefa, String> {

    List<Tarefa> findByUsuarioEmail(String usuarioEmail);

    Optional<Tarefa> findById(String id);

    void deleteById(String id);
}
