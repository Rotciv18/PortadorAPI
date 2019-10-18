package br.com.rotciv.endpoint;

import br.com.rotciv.error.ResourceNotFoundException;
import br.com.rotciv.model.Cartao;
import br.com.rotciv.model.Lancamento;
import br.com.rotciv.repository.CartaoRepositorio;
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

    @Autowired
    public LancamentoEndpoint(CartaoRepositorio cartaoDAO, LancamentoRepositorio lancamentoDAO) {
        this.cartaoDAO = cartaoDAO;
        this.lancamentoDAO = lancamentoDAO;
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
        lancamento.setCartao(cartao);
        lancamento.setNomePortador(cartao.getNomePortador());

        cartao.getLancamentos().add(lancamento);
        cartaoDAO.save(cartao);
        return new ResponseEntity<>(lancamentoDAO.save(lancamento), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/{lanc_id}")
    public ResponseEntity<?> deleteLancamento(@PathVariable("id")Long id, @PathVariable("lanc_id")Long lanc_id){
        checkCartaoById(id);
        checkLancamentoById(lanc_id);

        lancamentoDAO.deleteById(lanc_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void checkCartaoById(Long id){
        if(!cartaoDAO.existsById(id))
            throw new ResourceNotFoundException("Cartão não encontrado");
    }

    private void checkLancamentoById(Long id){
        if (!lancamentoDAO.existsById(id))
            throw new ResourceNotFoundException("Lançamento não encontrado");
    }
}
