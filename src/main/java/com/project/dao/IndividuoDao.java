package com.project.dao;

import com.project.domain.Individuo;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface IndividuoDao extends CrudRepository<Individuo, Integer> {

    @Query("SELECT i FROM Individuo i WHERE i.nombre LIKE %?1% OR i.apellido LIKE %?1%")
    List<Individuo> buscarPorPalabraClave(String palabraClave);

}

