package com.app1.controller.auth;

import com.app1.entity.User;
import com.app1.payload.JWTTokenDto;
import com.app1.payload.LoginDto;
import com.app1.repository.UserRepository;
import com.app1.service.JWTService;
import com.app1.service.OTPService;
import com.app1.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private UserRepository userRepository;
    private UserService userService;
    private OTPService otpService;
    private JWTService jwtService;

    public AuthController(UserRepository userRepository, UserService userService, OTPService otpService, JWTService jwtService){   //constructor based dependency injection
        this.userRepository = userRepository;
        this.userService = userService;
        this.otpService = otpService;
        this.jwtService = jwtService;
    }


                                  //ACCESS FOR THE USER

    //http://localhost:8080/api/v1/auth/signup
    @PostMapping("/signup")                                    //these links are secure and we do not want these links to be secure that is why we will perform config operation in CONFIG CONTROLLER for making specific links secure
   public ResponseEntity<?> createUser(@RequestBody User user)
    {
        Optional<User> opUsername  = userRepository.findByUsername(user.getUsername());
        if(opUsername.isPresent()){
            return new ResponseEntity<>("USERNAME EXIST", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<User> opEmailId = userRepository.findByEmailId(user.getEmailId());
        if(opEmailId.isPresent()){
            return new ResponseEntity<>("EMAIL ID EXIST", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String hashpw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));            //this method is used to encrypt the password in database
        user.setPassword(hashpw);
        user.setRole("ROLE_USER");      //before signup it will add role also
        userRepository.save(user);
        return new ResponseEntity<>("USER CREATED", HttpStatus.CREATED);
    }

                            //ACCESS FOR THE CONTENT MANAGER


    @PostMapping("/content-manager-signup")                                    //these links are secure and we do not want these links to be secure that is why we will perform config operation in CONFIG CONTROLLER for making specific links secure
    public ResponseEntity<?> createContentManagerAccount (@RequestBody User user)
    {
        Optional<User> opUsername  = userRepository.findByUsername(user.getUsername());
        if(opUsername.isPresent()){
            return new ResponseEntity<>("USERNAME EXIST", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<User> opEmailId = userRepository.findByEmailId(user.getEmailId());
        if(opEmailId.isPresent()){
            return new ResponseEntity<>("EMAIL ID EXIST", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String hashpw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));            //this method is used to encrypt the password in database
        user.setPassword(hashpw);
        user.setRole("ROLE_CONTENTMANAGER");
        userRepository.save(user);
        return new ResponseEntity<>("CONTENT MANAGER CREATED", HttpStatus.CREATED);
    }


                               //ACCESS FOR THE BLOG MANAGER

    @PostMapping("/blog-manager-signup")                                    //these links are secure and we do not want these links to be secure that is why we will perform config operation in CONFIG CONTROLLER for making specific links secure
    public ResponseEntity<?> createBlogManagerAccount (@RequestBody User user)
    {
        Optional<User> opUsername  = userRepository.findByUsername(user.getUsername());
        if(opUsername.isPresent()){
            return new ResponseEntity<>("USERNAME EXIST", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<User> opEmailId = userRepository.findByEmailId(user.getEmailId());
        if(opEmailId.isPresent()){
            return new ResponseEntity<>("EMAIL ID EXIST", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String hashpw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));            //this method is used to encrypt the password in database
        user.setPassword(hashpw);
        user.setRole("ROLE_BLOGMANAGER");
        userRepository.save(user);
        return new ResponseEntity<>("BLOG MANAGER CREATED", HttpStatus.CREATED);
    }

                               //  SIGN IN

    @PostMapping("/signin")
   public ResponseEntity<?> userSignIN(@RequestBody LoginDto dto) { //here login data will get verified but in service layer

        String jwtToken  = userService.verifyLogin(dto);
      if(jwtToken!=null){
          JWTTokenDto tokenDto = new JWTTokenDto();
          tokenDto.setToken(jwtToken);
          tokenDto.setTokenType("JWT");
          return new ResponseEntity<>(tokenDto, HttpStatus.CREATED) ;
      }
      return new ResponseEntity<>("invalid token", HttpStatus.INTERNAL_SERVER_ERROR);

  }

                          // LOG IN USING OTP


  @PostMapping("/login-otp")
  public String generateOTP( @RequestParam String mobile){
      Optional<User> opUser  =  userRepository.findByMobile(mobile);
      if(opUser.isPresent()){

          String otp = otpService.generateOTP(mobile);
          return "OTP:-" + otp + "   " + "MOBILE:-" + mobile;
      }
      return "USER NOT FOUND";

  }
                         // now validate otp and generate  jwt token for it
    @PostMapping("/validate-Otp")
     public String validateOtp(@RequestParam String mobile,
                               @RequestParam String otp) {
        boolean status = otpService.validateOTP(mobile, otp);
        if (status) {
            //generate JWT token
            Optional<User> opUser = userRepository.findByMobile(mobile);
            if (opUser.isPresent()) {
                String jwtToken = jwtService.generateToken(opUser.get().getUsername());
                return jwtToken;
            }
        }
        return status? "OTP validation successfully" : "Invalid OTP";
    }





  @PostMapping("/message")
      public String getMessage(){
        return "Hello";

  }
}
