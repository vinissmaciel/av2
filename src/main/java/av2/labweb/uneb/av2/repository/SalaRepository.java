package av2.labweb.uneb.av2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import av2.labweb.uneb.av2.model.Sala;

public interface SalaRepository extends JpaRepository<Sala, Long> {
    boolean existsByNome(String nome);
}