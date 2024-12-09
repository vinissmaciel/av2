package av2.labweb.uneb.av2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import av2.labweb.uneb.av2.exceptions.SalaDuplicadaException;
import av2.labweb.uneb.av2.model.Sala;
import av2.labweb.uneb.av2.repository.SalaRepository;
import jakarta.validation.Valid;
@Service
@Validated
public class SalaService {
    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public Sala salvar(Sala sala) {
        List<Sala> salas = this.list();

        for(Sala s: salas){
            if(s.getNome().equals(sala.getNome())){
                throw new SalaDuplicadaException("Sala já existente");
            }
        }

        return this.salaRepository.save(sala);
    }

    public List<Sala> list(){
        return this.salaRepository.findAll();
    }

    public ResponseEntity<Sala> findById(@PathVariable Long id){
        return null;
    }

}
