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
@RequestMapping("v1")
@CrossOrigin
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

    @GetMapping("lancamento/{id}")
    public ResponseEntity<?> getLancamentosByCartaoId(@PathVariable("id") Long id){
        checkCartaoById(id);

        return new ResponseEntity<>(lancamentoDAO.findAllByCartaoId(id), HttpStatus.OK);
    }

    @PostMapping("lancamento/add/{id}")
    public ResponseEntity<?> postLancamentoByCartaoId(@PathVariable("id") Long id, @RequestBody Lancamento lancamento){
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

    @DeleteMapping("lancamento/delete/{id}")
    public ResponseEntity<?> deleteLancamentoById(@PathVariable("id")Long id){

        checkLancamentoById(id);
        Long cartao_id = lancamentoDAO.findById(id).get().getCartao().getId();
        isCartaoAtivo(cartaoDAO.findById(cartao_id).get());

        lancamentoDAO.deleteById(id);
        Fatura fatura = faturaDAO.findByCartaoId(cartao_id);
        fatura.atualizaValorTotal();
        faturaDAO.save(fatura);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("protected/lancamento/edit")
    public ResponseEntity<?> editLancamentoByCartaoId (@RequestBody Lancamento lancamento){

        checkLancamentoById(lancamento.getId());

        lancamentoDAO.save(lancamento);
        Fatura fatura = faturaDAO.findByCartaoId(lancamento.getCartao().getId());
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
