package com.github.savitoh.centralerroapi.usuario;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{

    Optional<Usuario> findByLogin(String login);
}