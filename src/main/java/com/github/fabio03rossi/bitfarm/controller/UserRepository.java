package com.github.fabio03rossi.bitfarm.controller;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.fabio03rossi.bitfarm.account.Utente;

public class UserRepository extends JpaRepository<Utente, int>{

}
