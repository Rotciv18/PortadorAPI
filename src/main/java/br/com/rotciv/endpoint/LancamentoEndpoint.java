package br.com.rotciv.endpoint;

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
        if (!checkCartaoId(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(lancamentoDAO.findAllByCartaoId(id), HttpStatus.OK);
    }

    @PostMapping("/{id}/addlancamento")
    public ResponseEntity<?> putLancamento(@PathVariable("id") Long id, @RequestBody Lancamento lancamento){
        if (!checkCartaoId(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Cartao cartao = cartaoDAO.findById(id).get();
        lancamento.setCartao(cartao);
        lancamento.setNomePortador(cartao.getNomePortador());

        cartao.getLancamentos().add(lancamento);
        cartaoDAO.save(cartao);
        return new ResponseEntity<>(lancamentoDAO.save(lancamento), HttpStatus.OK);
    }

    private boolean checkCartaoId(Long id){
        return cartaoDAO.existsById(id);
    }
}
