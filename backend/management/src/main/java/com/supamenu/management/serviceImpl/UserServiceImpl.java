package com.supamenu.management.serviceImpl;

import com.supamenu.management.models.User;
import com.supamenu.management.payload.request.UpdateUserDTO;
import com.supamenu.management.repositories.UserRepository;
import com.supamenu.management.service.UserService;
import com.supamenu.management.utils.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public User getById(UUID id){
        return this.userRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("User not found with id: " + id)
        );
    }
    @Override
    public User create(User user){
        try{
            Optional<User> userOptional = this.userRepository.findByEmail(user.getEmail());
            if (userOptional.isPresent())
                throw new IllegalArgumentException(String.format("User with email '%s' already exists", user.getEmail()));
            return this.userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            String errorMessage = Utility.getConstraintViolationMessage(ex, user);
            throw new IllegalArgumentException(errorMessage, ex);
        }
    }
    @Override
    public User save(User user){
        try{
            return this.userRepository.save(user);
        }catch (DataIntegrityViolationException ex){
            String errorMessage = Utility.getConstraintViolationMessage(ex, user);
            throw new IllegalArgumentException(errorMessage, ex);
        }
    }
    @Override
    public User update(UUID id, UpdateUserDTO dto){
        User entity = this.userRepository.findById(id).orElseThrow(
                ()-> new NoSuchElementException("User not found by id" +id)
        );
        Optional<User> userOptional = this.userRepository.findByEmail(dto.getEmail());
        if (userOptional.isPresent() && (userOptional.get().getId() != entity.getId()))
            throw new IllegalArgumentException(String.format("User with email '%s' already exists", entity.getEmail()));

        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setMobile(dto.getMobile());

        return this.userRepository.save(entity);
    }

    @Override
    public boolean delete(UUID id){
        this.userRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("User not found by Id" +id)
        );
        this.userRepository.deleteById(id);
        return true;
    }

    @Override
    public User getLoggedInUser(){
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        }else {
            email = principal.toString();
        }
        return this.userRepository.findByEmail(email).orElseThrow(
                ()-> new NoSuchElementException("User not found by email" +email)
        );
    }
    @Override
    public User getByEmail(String email){
        return this.userRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("User not found with "+ email));
    }

    @Override
    public Page<User> getAll(Pageable pageable){
        return this.userRepository.findAll(pageable);
    }

    @Override
    public Page<User> search(Pageable pageable, String searchKey){
        return this.userRepository.search(pageable, searchKey);
    }
}
