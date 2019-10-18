package br.com.rotciv.endpoint;

import br.com.rotciv.error.ResourceNotFoundException;
import br.com.rotciv.model.Cartao;
import br.com.rotciv.model.Portador;
import br.com.rotciv.repository.CartaoRepositorio;
import br.com.rotciv.repository.PortadorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("portadores")
public class PortadorEndpoint {
    private final PortadorRepositorio portadorDAO;
    private final CartaoRepositorio cartaoDAO;

    @Autowired
    public PortadorEndpoint(PortadorRepositorio propostaDAO, CartaoRepositorio cartaoDAO) {
        this.portadorDAO = propostaDAO;
        this.cartaoDAO = cartaoDAO;
    }

    @GetMapping
    public ResponseEntity<?> getPortadores(){
        return new ResponseEntity<>(portadorDAO.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getPortadorById(@PathVariable("id") Long id){
        checkPortadorById(id);

        return new ResponseEntity<>(portadorDAO.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postPortador(@RequestBody Portador portador){
        Cartao cartao = new Cartao(portador.getNome());
        portador.getCartoes().add(cartao);
        cartao.setPortador(portador);
        try {
            portadorDAO.save(portador);
            cartaoDAO.save(cartao);
            return new ResponseEntity<>(portador, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping(path = "/{id}/addCartao")
    public ResponseEntity<?> putCartao(@PathVariable("id") Long id){
        checkPortadorById(id);

        Portador portador = portadorDAO.findById(id).get();
        Cartao cartao = new Cartao(portador.getNome());
        cartao.setPortador(portador);
        portador.getCartoes().add(cartao);

        cartaoDAO.save(cartao);
        portadorDAO.save(portador);
        return new ResponseEntity<>(cartao, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> editPortador(@RequestBody Portador portador){
        checkPortadorById(portador.getId());

        return new ResponseEntity<>(portadorDAO.save(portador), HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePortador(@PathVariable("id") Long id){
        checkPortadorById(id);

        cartaoDAO.deleteAllByPortadorId(id);
        portadorDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void checkPortadorById(Long id){
        if(!portadorDAO.existsById(id))
            throw new ResourceNotFoundException("Portador não encontrado");
    }
}
