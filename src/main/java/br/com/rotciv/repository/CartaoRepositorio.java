package br.com.rotciv.repository;

import br.com.rotciv.model.Cartao;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartaoRepositorio extends CrudRepository<Cartao, Long> {
    List<Cartao> findAllByPortadorId(Long id);
    void deleteAllByPortadorId(Long id);
}
