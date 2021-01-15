package com.space.repository;

import com.space.model.Ship;
import org.springframework.data.repository.CrudRepository;

//интерфейс, который расширяет определенный в Spring Data JPA интерфейс CrudRepository
public interface CRUD_repository extends CrudRepository<Ship, Long> {
    //С Spring Data JPA нам не нужно писать DAO (Java Data Acces Object) код.
    //Просто объявите интерфейс, расширяющий интерфейс CrudRepository, в котором определены такие методы CRUD как:
    //save(), findAll(), findById(), deleteById() и т.д. Во время выполнения Spring Data JPA автоматически сгенерирует
    //код.
    //
    //Обратите внимание, что мы должны указать тип класса модели и тип поля первичного ключа при расширении
    //CrudRepository: CrudRepository<Ship, Long>.
}
