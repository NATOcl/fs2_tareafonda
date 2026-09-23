package cl.dsy1104.fonda.service;

import cl.dsy1104.fonda.model.Bebida;
import cl.dsy1104.fonda.repository.BebidaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  
public class BebidaSevice {
    
    @Autowired 
    private BebidaRepository bebidaRepository;

    public List<Bebida> obtenerBebidas(){
        return bebidaRepository.findAll();
    }

    public Bebida guardarBebida(Bebida bebida) {
        return bebidaRepository.save(bebida);
    }

    public Bebida obtenerBebidaPorId(Long id){
        return bebidaRepository.findById(id).orElse(null);
    }

    public Bebida actualizaBebida(Bebida bebida){
        if(!bebidaRepository.existsById(bebida.getId())){
            return null;
        }

        return bebidaRepository.save(bebida);
    }

    public boolean eliminarBebida(Long id){
        if (bebidaRepository.existsById(id)){
            bebidaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    
}
