package com.srhdp.PhotoAppApiUsers.ui.controller;

import com.srhdp.PhotoAppApiUsers.service.UsersService;
import com.srhdp.PhotoAppApiUsers.shared.UserDto;
import com.srhdp.PhotoAppApiUsers.ui.model.CreateUserRequestModel;
import com.srhdp.PhotoAppApiUsers.ui.model.CreateUserResponseModel;
import com.srhdp.PhotoAppApiUsers.ui.model.UserResponseModel;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private Environment env;
    @Autowired
    UsersService usersService;
    @GetMapping("/status/check")
    public String status(){
        return "Working on port " + env.getProperty("local.server.port") + ", with token = " + env.getProperty ("token.secret");
    }

    @PostMapping
    public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto createdUser = usersService.createUser(userDto);

        CreateUserResponseModel returnValue = modelMapper.map(createdUser, CreateUserResponseModel.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }
    @GetMapping(value="/{userId}")
    public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId) {

        UserDto userDto = usersService.getUserByUserId(userId);
        UserResponseModel returnValue = new ModelMapper().map(userDto, UserResponseModel.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
}
