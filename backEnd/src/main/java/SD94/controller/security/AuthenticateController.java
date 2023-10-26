package SD94.controller.security;

import SD94.config.JwtUtils;
import SD94.entity.Staff;
import SD94.helper.UserNotFoundException;
import SD94.model.request.JwtRequest;
import SD94.model.response.JwtResponse;
import SD94.service.serviceImplement.StaffDetailsServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin("*")
public class AuthenticateController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private StaffDetailsServiceImpl staffDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    // generte token
    @SneakyThrows
    @PostMapping("/login")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) {
        try {
            authenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            throw new Exception("User not found ");
        }
        //////
        UserDetails userDetails = this.staffDetailsService.loadUserByUsername(jwtRequest.getEmail());
        System.out.println("userDetails --->" +userDetails);
        String token = this.jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("User disabled " + e.getMessage());
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            throw new Exception("Invalid Credentials " + e.getMessage());
        }
    }

    // return the details of current user
    @GetMapping("/current-user")
    public Staff getCurrentUser(Principal principal) {
        return (Staff) this.staffDetailsService.loadUserByUsername(principal.getName());
    }
}