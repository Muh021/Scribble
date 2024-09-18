package dev.scribble.controller;

import dev.scribble.exception.UserNotFoundException;
import dev.scribble.dto.NewUserRequest;
import dev.scribble.models.User;
import dev.scribble.models.UserLogin;
import dev.scribble.repository.UserLoginRepository;
import dev.scribble.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserLoginRepository userLoginRepository;

    /**
     * Creates a new user with the provided details.
     *
     * @param newUserRequest The request object containing details for the new user.
     * @return The newly created user entity.
     */
    @PostMapping("/user/add")
    public User createUser(@Valid @RequestBody NewUserRequest newUserRequest) {

        // String firstName, String lastName, String email
        User user = new User(newUserRequest.getFirstName(), newUserRequest.getLastName(), newUserRequest.getEmail());

        // Save login information
        UserLogin login = new UserLogin(user, newUserRequest.getUsername(), newUserRequest.getPasswordHash());
        userRepository.save(user);
        userLoginRepository.save(login);

        return user;
    }

    /**
     * Retrieves a user entity by its unique identifier.
     *
     * @param userId The unique identifier of the user to retrieve.
     * @return The user entity with the specified ID.
     * @throws UserNotFoundException If no user entity with the specified ID is found.
     */
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable(value = "id") Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    /**
     * Updates an existing user entity with the provided details.
     *
     * @param userId       The unique identifier of the user to update.
     * @param userDetails  The updated details of the user.
     * @return The updated user entity.
     * @throws UserNotFoundException If no user entity with the specified ID is found.
     */
    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable(value = "id") Long userId, @Valid @RequestBody User userDetails) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if (userDetails.getFirstName() != null) {
            user.setFirstName(userDetails.getFirstName());
        }

        if (userDetails.getLastName() != null) {
            user.setLastName(userDetails.getLastName());
        }

        if (userDetails.getEmail() != null) {
            user.setEmail(userDetails.getEmail());
        }

        return userRepository.save(user);
    }

    /**
     * Deletes a user entity by its unique identifier.
     *
     * @param userId The unique identifier of the user to delete.
     * @return ResponseEntity indicating the success of the operation.
     * @throws UserNotFoundException If no user entity with the specified ID is found.
     */
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        UserLogin userLogin = userLoginRepository.findByUser(user);

        userLoginRepository.delete(userLogin);
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }
}
