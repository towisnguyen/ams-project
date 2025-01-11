package com.r23.ams.controller;

import com.r23.ams.mapper.AppUserMapper;
import com.r23.ams.models.dto.AppUserDto;
import com.r23.ams.models.entities.AppUser;
import com.r23.ams.models.request.JwtRequest;
import com.r23.ams.models.request.JwtResponse;
import com.r23.ams.service.user.JwtUserDetailsService;
import com.r23.ams.utils.JwtTokenUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r23.ams.utils.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws JsonMappingException, JsonProcessingException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
            final UserDetails userDetails =
                    userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

            final String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(token));
        }catch(Exception ex)
        {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree("{\"Message\": \"Incorrect User or password\"}");
            return ResponseEntity.ok(json);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<AppUserDto> getUserInfo(){
        AppUser currentUser = SessionHelper.getCurrentUser();
        return ResponseEntity.ok(AppUserMapper.getInstance().toDTO(currentUser));
    }
}
