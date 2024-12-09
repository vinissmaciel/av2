package av2.labweb.uneb.av2.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import av2.labweb.uneb.av2.exceptions.GlobalExceptionHandler;
import av2.labweb.uneb.av2.model.Reserva;
import av2.labweb.uneb.av2.repository.ReservaRepository;

@Service
public class ReservaService {
    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public Reserva criarReserva(Reserva novaReserva) {

        if(verificaConflitoDeHorario(novaReserva, novaReserva.getId())){
            throw new IllegalArgumentException("Horário indisponível.");
        }

        return this.reservaRepository.save(novaReserva);
    }

    public List<Reserva> listarPorSala(Long salaId) {
        List<Reserva> reservas = new ArrayList<>();
        reservas = this.reservaRepository.findBySalaId(salaId);

        if(reservas.isEmpty()){
            throw new IllegalArgumentException("Sala não encontrada");
        }

        return reservas;
    }

    public List<Reserva> buscarReservasPorSala(Long salaId) {
        return null;
    }

    public Map<String, Object> buscarReservaPorId(Long id) {
        return null;
    }

    public Reserva atualizarReserva(Long id, Reserva novaReserva) {
        return this.reservaRepository.findById(id)
            .map(recordFound -> {
                if(verificaConflitoDeHorario(novaReserva, id)){
                    throw new IllegalArgumentException("Horário indisponível.");
                }

                recordFound.setPeriodo(90);
                recordFound.setDataHora(novaReserva.getDataHora());
                recordFound.setResponsavel(novaReserva.getResponsavel());
                recordFound.setSala(novaReserva.getSala());
                return this.reservaRepository.save(recordFound);
            })
            .orElseThrow(() -> new IllegalArgumentException("Sala não encontrada"));
    }

    public List<Map<String, Object>> gerarRelatorio() {
        List<Object[]> r = this.reservaRepository.gerarRelatorio();
        List<Map<String, Object>> relatorio = new ArrayList<>();

        for(Object[] r1: r){
            relatorio.add(
                Map.of(
                    "nomeSala", r1[0],
                    "totalReservas", r1[1]
                )
            );
        }
        
        return relatorio;
    }

    public boolean verificaConflitoDeHorario(Reserva novaReserva, Long idAtual) {
        List<Reserva> reservasExistentes = reservaRepository.findBySalaId(novaReserva.getSala().getId());
    
        for (Reserva reserva : reservasExistentes) {
            if (reserva.getId().equals(idAtual) && idAtual != null) {
                continue;
            }
            
            LocalDateTime inicioExistente = reserva.getDataHora();
            LocalDateTime fimExistente = inicioExistente.plusMinutes(reserva.getPeriodo());
    
            LocalDateTime inicioNova = novaReserva.getDataHora();
            LocalDateTime fimNova = inicioNova.plusMinutes(novaReserva.getPeriodo());
    
            if ((inicioNova.isBefore(fimExistente) && inicioNova.isAfter(inicioExistente)) ||
                (fimNova.isAfter(inicioExistente) && fimNova.isBefore(fimExistente)) ||
                (inicioNova.isEqual(inicioExistente) || fimNova.isEqual(fimExistente))) {
                return true;
            }
        }
    
        return false;
    }
}