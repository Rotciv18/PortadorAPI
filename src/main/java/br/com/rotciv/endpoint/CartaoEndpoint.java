package br.com.rotciv.endpoint;

import br.com.rotciv.model.Fatura;
import br.com.rotciv.model.Portador;
import br.com.rotciv.error.ResourceNotFoundException;
import br.com.rotciv.model.Cartao;
import br.com.rotciv.repository.CartaoRepositorio;
import br.com.rotciv.repository.PortadorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1")
@CrossOrigin
public class CartaoEndpoint {
    private final CartaoRepositorio cartaoDAO;
    private final PortadorRepositorio portadorDAO;

    @Autowired
    public CartaoEndpoint(CartaoRepositorio cartaoDAO, PortadorRepositorio portadorDAO) {
        this.cartaoDAO = cartaoDAO;
        this.portadorDAO = portadorDAO;
    }

    @GetMapping("cartao/{id}")
    public ResponseEntity<?> getCartaoById(@PathVariable("id") Long id){
        return new ResponseEntity<>(cartaoDAO.findById(id), HttpStatus.OK);
    }

    @PostMapping("cartao/add/{id}")
    public ResponseEntity<?> addCartaoByPortadorId(@PathVariable("id") Long id){
        checkPortadorById(id);

        Portador portador = portadorDAO.findById(id).get();
        Cartao cartao = new Cartao(portador.getNome());
        cartao.setPortador(portador);
        portador.getCartoes().add(cartao);
        Fatura fatura = new Fatura();
        fatura.setCartao(cartao);

        cartaoDAO.save(cartao);
        portadorDAO.save(portador);
        //faturaDAO.save(fatura);
        return new ResponseEntity<>(cartao, HttpStatus.OK);
    }

    @DeleteMapping("protected/cartao/desativar/{id}")
    public ResponseEntity<?> desativarCartaoById(@PathVariable("id") Long id){
        checkCartaoById(id);

        Cartao cartao = cartaoDAO.findById(id).get();
        cartao.setAtivo(false);

        return new ResponseEntity<>(cartaoDAO.save(cartao), HttpStatus.OK);
    }

    @PutMapping("protected/cartao/edit")
    public ResponseEntity<?> editCartao(@RequestBody Cartao cartao){
        checkCartaoById(cartao.getId());

        return new ResponseEntity<>(cartaoDAO.save(cartao), HttpStatus.OK);
    }

    @PutMapping("cartao/ativar/{id}")
    public ResponseEntity<?> ativarCartaoById(@PathVariable("id") Long id){
        checkCartaoById(id);

        Cartao cartao = cartaoDAO.findById(id).get();
        cartao.setAtivo(true);

        return new ResponseEntity<>(cartaoDAO.save(cartao), HttpStatus.OK);
    }

    private boolean checkPortadorId(Long id){
        return portadorDAO.existsById(id);
    }

    private void checkCartaoById(Long id){
        if(!cartaoDAO.existsById(id))
            throw new ResourceNotFoundException("Cartão não encontrado");
    }

    private void checkPortadorById(Long id){
        if(!portadorDAO.existsById(id))
            throw new ResourceNotFoundException("Portador não encontrado");
    }
}
