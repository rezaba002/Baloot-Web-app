package com.example.baloot6backend.Repository;

import com.example.baloot6backend.Model.BuyListItem;
import com.example.baloot6backend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public User findByUsername(String username);
    public User findByEmail(String email);
}
