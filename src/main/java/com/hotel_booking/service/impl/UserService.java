package com.hotel_booking.service.impl;

import com.hotel_booking.dto.LoginRequest;
import com.hotel_booking.dto.Response;
import com.hotel_booking.dto.UserDTO;
import com.hotel_booking.entity.User;
import com.hotel_booking.exception.OurException;
import com.hotel_booking.repo.UserRepository;
import com.hotel_booking.service.interfac.IUserService;
import com.hotel_booking.utils.JWTUtils;
import com.hotel_booking.utils.Utils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;

    @Override
    public Response register(@Valid User loginRequest) {
        Response response=new Response();
        try{
            if(loginRequest.getRole()==null || loginRequest.getRole().isBlank()){
                loginRequest.setRole("USER");
            }
            if(userRepository.existsByEmail(loginRequest.getEmail())){
                throw new OurException("Email already exists");
            }
            loginRequest.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
            User saveduser=userRepository.save(loginRequest);
            UserDTO userDTO= Utils.mapUserEntityToUserDTO(saveduser);
            response.setStatusCode(200);
            response.setUser(userDTO);
        }catch (OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred During User Registration "  + e.getMessage());
        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response=new Response();
        try{
             authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));

             var user=userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()->new OurException("User not found"));

             var token=jwtUtils.generateToken(user);
             response.setStatusCode(200);
             response.setToken(token);
             response.setExpirationTime("7 Days");
             response.setMessage("Successfully login");
        }catch (OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred During User Login Incorrect email or password"  + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllUsers() {
        Response response=new Response();
        try{
            List<User> userList=userRepository.findAll();
            List<UserDTO> userDTOList=Utils.mapUserListEntityToUserListDTO(userList);

            response.setStatusCode(200);
            response.setMessage("success");
            response.setUserList(userDTOList);
        }catch (OurException e){
            response.setStatusCode(400);
            response.setMessage("Error getting All users "  + e.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting All users "  + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserBookingHistory(String userId) {
        Response response=new Response();
        try{
            User user=userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new OurException("User not found"));

            UserDTO userDTO=Utils.mapUserEntityToUserDTOPlusUserBookingAndRoom(user);

            response.setStatusCode(200);
            response.setMessage("successfull");
            response.setUser(userDTO);
        }catch (OurException e){
            response.setStatusCode(400);
            response.setMessage("Error getting Booking Histories "  + e.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting Booking Histories "  + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteUser(String userId) {
        Response response=new Response();
        try{
            User user=userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new OurException("User not found"));

            userRepository.deleteById(user.getId());
            response.setStatusCode(200);
            response.setMessage("successfull Delete");
        }catch (OurException e){
            response.setStatusCode(400);
            response.setMessage("Error Deleting user "  + e.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Deleting  user "  + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserById(String userId) {
        Response response=new Response();
        try{
            User user=userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new OurException("User not found"));

            UserDTO userDTO=Utils.mapUserEntityToUserDTO(user);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDTO);
        }catch (OurException e){
            response.setStatusCode(400);
            response.setMessage("Error Getting user "  + e.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Getting  user "  + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getMyInfo(String email) {
        Response response=new Response();
        try{
            User user=userRepository.findByEmail(email).orElseThrow(()->new OurException("User not found"));

            UserDTO userDTO=Utils.mapUserEntityToUserDTO(user);

            response.setStatusCode(200);
            response.setMessage("successful Delete");
            response.setUser(userDTO);
        }catch (OurException e){
            response.setStatusCode(400);
            response.setMessage("Error Getting My Information "  + e.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Getting  My Information "  + e.getMessage());
        }
        return response;
    }
}
