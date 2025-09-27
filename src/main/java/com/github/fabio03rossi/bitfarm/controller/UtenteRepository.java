package com.github.fabio03rossi.bitfarm.controller;

import com.github.fabio03rossi.bitfarm.account.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {



}
