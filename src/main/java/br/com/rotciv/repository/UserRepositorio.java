package br.com.rotciv.repository;

import br.com.rotciv.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepositorio extends PagingAndSortingRepository<User, Long> {
    User findByUsername(String username);
}
