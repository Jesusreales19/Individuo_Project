package com.project.service;

import com.project.dao.IndividuoDao;
import com.project.domain.Individuo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndividuoService implements IIndividuoService {

    @Autowired
    private IndividuoDao individuoDao;

    @Override
    public List<Individuo> listarIndividuos() {
        return (List<Individuo>) individuoDao.findAll();
    }
    
    @Override
    public Individuo buscarIndividuoPorId(Integer id) {
        return individuoDao.findById(id).orElse(null);
    }
    
    @Override
    public void guardarIndividuo(Individuo individuo) {
        individuoDao.save(individuo);
    }

    @Override
    public List<Individuo> buscarIndividuos(String palabraClave) {
        return individuoDao.buscarPorPalabraClave(palabraClave);
    }
    @Override
    public void eliminarIndividuo(Integer id) {
        individuoDao.deleteById(id);
    }
    @Override
    public void editarIndividuo(Integer id) {
        individuoDao.findById(id);
    }

}
