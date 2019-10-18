package br.com.rotciv.endpoint;

import br.com.rotciv.error.ResourceNotFoundException;
import br.com.rotciv.model.Cartao;
import br.com.rotciv.repository.CartaoRepositorio;
import br.com.rotciv.repository.PortadorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("portadores/cartoes")
public class CartaoEndpoint {
    private final CartaoRepositorio cartaoDAO;
    private final PortadorRepositorio portadorDAO;

    @Autowired
    public CartaoEndpoint(CartaoRepositorio cartaoDAO, PortadorRepositorio portadorDAO) {
        this.cartaoDAO = cartaoDAO;
        this.portadorDAO = portadorDAO;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCartoesByPortadorId(@PathVariable("id") Long id){
        return new ResponseEntity<>(cartaoDAO.findAllByPortadorId(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartaoById(@PathVariable("id") Long id){
        checkCartaoById(id);

        Cartao cartao = cartaoDAO.findById(id).get();
        Long portador_id = cartao.getPortador().getId();
        cartaoDAO.deleteById(id);

        //Portador deve ser deletado se tiver nenhum cartão
        if (portadorDAO.findById(portador_id).get().getCartoes().size() == 0)
            portadorDAO.deleteById(portador_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean checkPortadorId(Long id){
        return portadorDAO.existsById(id);
    }

    private void checkCartaoById(Long id){
        if(!cartaoDAO.existsById(id))
            throw new ResourceNotFoundException("Cartão não encontrado");
    }
}
