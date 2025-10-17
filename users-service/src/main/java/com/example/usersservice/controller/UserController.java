package com.example.usersservice.controller;


import com.example.usersservice.dto.UserDTO;
import com.example.usersservice.dto.UserSaveDTO;
import com.example.usersservice.entity.User;
import com.example.usersservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<String> getAllUserNames(){
        return userRepository.getAllUserNames();
    }

//    @GetMapping("/users/{id}")
//    public UserDTO getUserByID(@PathVariable Integer id){
//        User user = userRepository.getUserByID(id).orElse(null);
//        return modelMapper.map(user,UserDTO.class);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserByID(@PathVariable Integer id){
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Korisnik sa ID "+id+" nije pronadjen");
        }
        UserDTO dto = modelMapper.map(user,UserDTO.class);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/saveUser")
    public UserDTO saveUser(@RequestBody UserSaveDTO dto){
        User user = modelMapper.map(dto,User.class);
        user = userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id){
        if(userRepository.existsById(id))
        {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Korisnik sa ID "+id+" obrisan");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Korisnik sa ID "+id+" nije pronadjen");

    }

    @PostMapping("/update/{id}")
    public ResponseEntity<String> updateUserEmailByID(@PathVariable Integer id, @RequestBody UserSaveDTO dto){
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
