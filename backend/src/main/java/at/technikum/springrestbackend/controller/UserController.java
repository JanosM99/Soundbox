package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.UserDto;
import at.technikum.springrestbackend.exception.EntityNotFoundException;
import at.technikum.springrestbackend.model.User;
import at.technikum.springrestbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/users")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/getAllUsers")
    public List<UserDto> getAll(){
        return userService.findAll().stream().map(userService::convertToDto).toList();
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) throws EntityNotFoundException {
        User user = userService.findById(id);
        return ResponseEntity.ok(userService.convertToDto(user));
    }

    @GetMapping("/getUserByName/{username}")
    public ResponseEntity<UserDto> getUserByName(@PathVariable String username) throws  EntityNotFoundException {
        User user = userService.findByName(username);
        return ResponseEntity.ok(userService.convertToDto(user));
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) throws Exception {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.convertToDto(userService.save(userDto)));
    }

    @DeleteMapping("deleteUser/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/updateUser")
    public ResponseEntity<UserDto> updateById(@RequestBody UserDto userDto) {
        try {
            User updatedUser = userService.updateUser(userDto);
            return ResponseEntity.ok(userService.convertToDto(updatedUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
