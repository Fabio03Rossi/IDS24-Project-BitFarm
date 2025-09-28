package com.github.fabio03rossi.bitfarm.controller;

import com.github.fabio03rossi.bitfarm.account.Utente;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class UserRepository implements JpaRepository<Utente, Integer> {

    @Override
    public void flush() {

    }

    @Override
    public <S extends Utente> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Utente> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Utente> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Utente getOne(Integer integer) {
        return null;
    }

    @Override
    public Utente getById(Integer integer) {
        return null;
    }

    @Override
    public Utente getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Utente> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Utente> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Utente> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Utente> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Utente> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Utente> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Utente, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Utente> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Utente> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Utente> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Utente> findAll() {
        return List.of();
    }

    @Override
    public List<Utente> findAllById(Iterable<Integer> integers) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Utente entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Utente> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Utente> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Utente> findAll(Pageable pageable) {
        return null;
    }
}
