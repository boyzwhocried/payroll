package com.lawencon.payroll.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;

@NoRepositoryBean
public interface BaseRepository <T,ID> extends JpaRepository<T, ID> {
    @NonNull
    Optional<T> findById(@NonNull ID id);

    @NonNull
    <S extends T> S save(@NonNull S entity);
}
