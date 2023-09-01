package com.example.HelpDesk.repository;
import com.example.HelpDesk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findById(Long id);

    List<User> findByName(String name);

    List<User> findByNameContaining(String name);

    Optional<User> findByEmail(String email);


    Optional<User> findByEmailAndPassword(String email, String password);
}
