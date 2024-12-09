package av2.labweb.uneb.av2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import av2.labweb.uneb.av2.model.Reserva;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findBySalaId(Long salaId);
    boolean existsBySalaIdAndDataHora(Long salaId, LocalDateTime dataHora);
    @Query("SELECT r.sala.nome AS nomeSala, COUNT(r) AS totalReservas FROM Reserva r GROUP BY r.sala.nome")
    List<Object[]> gerarRelatorio();
}
