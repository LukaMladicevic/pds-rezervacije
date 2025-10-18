package com.example.usersservice.controller;


import com.example.usersservice.dto.UserDTO;
import com.example.usersservice.dto.UserEmailChangeDTO;
import com.example.usersservice.dto.UserSaveDTO;
import com.example.usersservice.entity.User;
import com.example.usersservice.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @GetMapping("/names")
    public List<String> getAllUserNames(){
        return userRepository.getAllUserNames();
    }

    @GetMapping
    public List<UserDTO> getAllUsers(){
         List<User> users = userRepository.findAll();
         List<UserDTO> dtos = users.stream().map(u->modelMapper.map(u,UserDTO.class)).collect(Collectors.toList());
         return dtos;
    }

//    @GetMapping("/users/{id}")
//    public UserDTO getUserByID(@PathVariable Integer id){
//        User user = userRepository.getUserByID(id).orElse(null);
//        return modelMapper.map(user,UserDTO.class);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserByID(@PathVariable @Min(1) Integer id){
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Korisnik sa ID "+id+" nije pronadjen");
        }
        UserDTO response = modelMapper.map(user,UserDTO.class);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserSaveDTO dto){
        if(userRepository.existsByEmail(dto.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email je vec u upotebi!");
        }
        User user = modelMapper.map(dto,User.class);
        user = userRepository.save(user);
        UserDTO response = modelMapper.map(user,UserDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable @Min(1) Integer id){
        if(userRepository.existsById(id))
        {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Korisnik sa ID "+id+" obrisan");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Korisnik sa ID "+id+" nije pronadjen");

    }

    @PostMapping("/{id}/email")
    public ResponseEntity<?> updateUserEmailByID(@PathVariable @Min(1) Integer id,@Valid @RequestBody UserEmailChangeDTO dto){
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Korisnik sa ID "+id+" nije pronadjen.");
        }
        String email = dto.getEmail();
        user.setEmail(email);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("Email uspesno promenjen na: "+dto.getEmail());
    }



}
