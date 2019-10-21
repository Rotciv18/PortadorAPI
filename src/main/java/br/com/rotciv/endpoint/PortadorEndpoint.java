package br.com.rotciv.endpoint;

import br.com.rotciv.throwableResponses.ResourceNotFoundException;
import br.com.rotciv.model.Portador;
import br.com.rotciv.model.Proposta;
import br.com.rotciv.repository.CartaoRepositorio;
import br.com.rotciv.repository.FaturaRepositorio;
import br.com.rotciv.repository.PortadorRepositorio;
import br.com.rotciv.services.PropostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("v1")
@CrossOrigin
public class PortadorEndpoint {
    private final PortadorRepositorio portadorDAO;
    private final CartaoRepositorio cartaoDAO;
    private final PropostaService propostaService;

    @Autowired
    public PortadorEndpoint(PortadorRepositorio propostaDAO, CartaoRepositorio cartaoDAO, PropostaService propostaService) {
        this.portadorDAO = propostaDAO;
        this.cartaoDAO = cartaoDAO;
        this.propostaService = propostaService;
    }

    @GetMapping("portador")
    public ResponseEntity<?> getPortadores(){
        return new ResponseEntity<>(portadorDAO.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "portador/{id}")
    public ResponseEntity<?> getPortadorById(@PathVariable("id") Long id){
        checkPortadorById(id);

        return new ResponseEntity<>(portadorDAO.findById(id), HttpStatus.OK);
    }

    @PostMapping("portador/add")
    public void postPortador(@RequestBody Proposta proposta){
        propostaService.enviarProposta(proposta);
    }

    @PutMapping(path = "protected/portador/edit")
    public ResponseEntity<?> editPortador(@RequestBody Portador portador){
        checkPortadorById(portador.getId());

        return new ResponseEntity<>(portadorDAO.save(portador), HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("protected/portador/delete/{id}")
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
