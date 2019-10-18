package br.com.rotciv.repository;

import br.com.rotciv.model.Fatura;
import org.springframework.data.repository.CrudRepository;

public interface FaturaRepositorio extends CrudRepository<Fatura, Long> {
    Fatura findByCartaoId(Long id);
}
