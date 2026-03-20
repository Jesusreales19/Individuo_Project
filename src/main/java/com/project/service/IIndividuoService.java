package com.project.service;

import com.project.domain.Individuo;
import java.util.List;

public interface IIndividuoService {

    List<Individuo> listarIndividuos();

    List<Individuo> buscarIndividuos(String palabraClave);

    Individuo buscarIndividuoPorId(Integer id);

    void guardarIndividuo(Individuo individuo);

    public void eliminarIndividuo(Integer id);

    public void editarIndividuo(Integer id);
    
}
