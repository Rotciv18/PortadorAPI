package br.com.rotciv.endpoint;

import br.com.rotciv.throwableResponses.ResourceNotFoundException;
import br.com.rotciv.repository.CartaoRepositorio;
import br.com.rotciv.repository.FaturaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
public class FaturaEndpoint {
    private final FaturaRepositorio faturaDAO;
    private final CartaoRepositorio cartaoDAO;

    @Autowired
    public FaturaEndpoint(FaturaRepositorio faturaDAO, CartaoRepositorio cartaoDAO) {
        this.faturaDAO = faturaDAO;
        this.cartaoDAO = cartaoDAO;
    }

    @GetMapping("fatura/{id}")
    public ResponseEntity<?> getFaturaByCartaoId (@PathVariable("id") Long id){
        checkCartaoById(id);

        return new ResponseEntity<>(faturaDAO.findByCartaoId(id), HttpStatus.OK);
    }

    private void checkCartaoById(Long id){
        if(!cartaoDAO.existsById(id))
            throw new ResourceNotFoundException("Cartão não encontrado");
    }
}
