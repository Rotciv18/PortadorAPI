package br.com.rotciv.repository;

import br.com.rotciv.model.Lancamento;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LancamentoRepositorio extends CrudRepository<Lancamento, Long> {
    List<Lancamento> findAllByCartaoId(Long id);
}
