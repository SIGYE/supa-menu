package com.supamenu.management.service;

import com.supamenu.management.models.User;
import com.supamenu.management.payload.request.UpdateUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {
    User getById(UUID id);

    User create(User user);

    User save(User user);

    User update(UUID id, UpdateUserDTO dto);

    boolean delete(UUID id);

    User getLoggedInUser();

    User getByEmail(String email);

    Page<User> getAll(Pageable pageable);
    Page<User> search(Pageable pageable, String searchKey);
}
