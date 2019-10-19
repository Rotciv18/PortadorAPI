package br.com.rotciv.endpoint;

import br.com.rotciv.throwableResponses.ResourceNotFoundException;
import br.com.rotciv.model.Cartao;
import br.com.rotciv.model.Fatura;
import br.com.rotciv.model.Lancamento;
import br.com.rotciv.repository.CartaoRepositorio;
import br.com.rotciv.repository.FaturaRepositorio;
import br.com.rotciv.repository.LancamentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("portadores/cartoes")
public class LancamentoEndpoint {
    private final CartaoRepositorio cartaoDAO;
    private final LancamentoRepositorio lancamentoDAO;
    private final FaturaRepositorio faturaDAO;

    @Autowired
    public LancamentoEndpoint(CartaoRepositorio cartaoDAO, LancamentoRepositorio lancamentoDAO, FaturaRepositorio faturaDAO) {
        this.cartaoDAO = cartaoDAO;
        this.lancamentoDAO = lancamentoDAO;
        this.faturaDAO = faturaDAO;
    }

    @GetMapping("/{id}/lancamentos")
    public ResponseEntity<?> getLancamentosByCartaoId(@PathVariable("id") Long id){
        checkCartaoById(id);

        return new ResponseEntity<>(lancamentoDAO.findAllByCartaoId(id), HttpStatus.OK);
    }

    @PostMapping("/{id}/addlancamento")
    public ResponseEntity<?> putLancamento(@PathVariable("id") Long id, @RequestBody Lancamento lancamento){
        checkCartaoById(id);

        Cartao cartao = cartaoDAO.findById(id).get();
        isCartaoAtivo(cartao);

        lancamento.setCartao(cartao);
        lancamento.setNomePortador(cartao.getNomePortador());
        Fatura fatura = faturaDAO.findByCartaoId(id);
        fatura.getLancamentos().add(lancamento);
        fatura.atualizaValorTotal();
        lancamento.setFatura(fatura);

        cartao.getLancamentos().add(lancamento);
        //cartaoDAO.save(cartao);
        faturaDAO.save(fatura);
        lancamentoDAO.save(lancamento);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/{lanc_id}")
    public ResponseEntity<?> deleteLancamentoByCartaoIdAndLancamentoId(@PathVariable("id")Long id,
                                                                       @PathVariable("lanc_id")Long lanc_id){

        checkCartaoById(id);
        checkLancamentoById(lanc_id);
        isCartaoAtivo(cartaoDAO.findById(id).get());

        lancamentoDAO.deleteById(lanc_id);
        Fatura fatura = faturaDAO.findByCartaoId(id);
        fatura.atualizaValorTotal();
        faturaDAO.save(fatura);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editLancamentoByCartaoId (@PathVariable("id")Long id,
                                                       @RequestBody Lancamento lancamento){

        checkCartaoById(id);
        checkLancamentoById(lancamento.getId());

        lancamentoDAO.save(lancamento);
        Fatura fatura = faturaDAO.findByCartaoId(id);
        fatura.atualizaValorTotal();

        return new ResponseEntity<>(lancamento, HttpStatus.OK);
    }

    private void checkCartaoById(Long id){
        if(!cartaoDAO.existsById(id))
            throw new ResourceNotFoundException("Cartão não encontrado");
    }

    private void checkLancamentoById(Long id){
        if (!lancamentoDAO.existsById(id))
            throw new ResourceNotFoundException("Lançamento não encontrado");
    }

    private void isCartaoAtivo(Cartao cartao){
        if (!cartao.isAtivo())
            throw new ResourceNotFoundException("Este cartão está desativado");
    }
}
