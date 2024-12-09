package av2.labweb.uneb.av2.controler;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import av2.labweb.uneb.av2.model.Sala;
import av2.labweb.uneb.av2.services.SalaService;

@RestController
@RequestMapping("/api/salas")
public class SalaController {
    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @GetMapping
    public List<Sala> list(){
      return salaService.list();
   }

    @GetMapping("/{id}")
    public ResponseEntity<Sala> findById(Long id){
      return salaService.findById(id);
    }
    

    @PostMapping
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public Sala criar(@RequestBody Sala sala) {
        return salaService.salvar(sala);
    }
}