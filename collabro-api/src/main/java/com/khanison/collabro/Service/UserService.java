package com.khanison.collabro.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khanison.collabro.Entity.Task;
import com.khanison.collabro.Entity.User;
import com.khanison.collabro.Exception.EmailAlreadyExistsException;
import com.khanison.collabro.Exception.ResourceNotFoundException;
import com.khanison.collabro.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
    }

    @Transactional
    public User create(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }

        user.setId(null);
        return userRepository.save(user);
    }

    @Transactional
    public User update(Long id, User updatedUser) {
        User existingUser = findById(id);
        String updatedEmail = updatedUser.getEmail();

        // If email is being changed, check if new email already exists
        if (!existingUser.getEmail().equals(updatedEmail) &&
                userRepository.existsByEmail(updatedEmail)) {
            throw new EmailAlreadyExistsException(updatedEmail);
        }

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedEmail);
        existingUser.setPassword(updatedUser.getPassword());
        return userRepository.save(existingUser);
    }

    @Transactional
    public void delete(Long id) {
        User user = findById(id);

        for (Task task : user.getAssignedTasks()) {
            task.setAssignedUser(null);
        }

        userRepository.delete(user);
    }
}
