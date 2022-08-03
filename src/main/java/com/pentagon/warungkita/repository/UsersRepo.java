package com.pentagon.warungkita.repository;

import com.pentagon.warungkita.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface UsersRepo extends JpaRepository<Users, Long> {
}
