package com.alpha.TaskOrbit.services;

import com.alpha.TaskOrbit.dto.SignUpDTO;
import com.alpha.TaskOrbit.modules.EmailVerificationDetails;
import com.alpha.TaskOrbit.modules.User;
import com.alpha.TaskOrbit.repositories.RepoEmailVerificationDetails;
import com.alpha.TaskOrbit.repositories.RepoUser;
import com.alpha.TaskOrbit.utils.EmailUtil;
import com.alpha.TaskOrbit.utils.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.*;

@Service
public class AuthService implements IAuthService {
    @Autowired
    private RepoUser repoUser;
    @Autowired
    private RepoEmailVerificationDetails repoEmailVerificationDetails;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public AuthService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Value("${spring.project.mail.smtp.host}")
    private String host;
    @Value("${spring.project.mail.starttls.enable}")
    private String starttls_enable;
    @Value("${spring.project.mail.smtp.auth}")
    private String smtp_auth;
    @Value("${spring.project.mail.smtp.port}")
    private String port;
    @Value("${spring.project.smtp.hostEmailId}")
    private String fromEmail;
    @Value("${spring.project.smtp.emailPassword}")
    private String emailPassword;

    @Value("${BASE_URL}")
    private String baseUrl;

    private final Logger logger = LoggerFactory.getLogger(AuthService.class);
    public ResponseEntity<String> signUp(@RequestBody SignUpDTO signUpDTO) {
        logger.info("New User Details Received", signUpDTO);
        String firstName = signUpDTO.getFirstName();
        String lastName = signUpDTO.getLastName();
        String email = signUpDTO.getEmailID();
        String password = signUpDTO.getPassword();
        String mobileNo = signUpDTO.getMobileNo();
        logger.info("Checking for existing user with same Email ID");
        Optional<User> existingUser = repoUser.findByEmailID(email);
        if (existingUser.isPresent()) {
            return new ResponseEntity<>("User with this email already exists", HttpStatus.BAD_REQUEST);
        }
//      New User Set-Up
        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmailID(email);
        newUser.setPassword(passwordEncoder.encode(password));
        if(!mobileNo.isEmpty())
        {
            newUser.setMobileNo(mobileNo);
        }
        repoUser.save(newUser);

        logger.info("New User Saved to Database",newUser);
        logger.info("Email Sending Process Begins");
        final String toEmail = email; // New User's email ID.

        Properties props = new Properties();
        props.put("mail.smtp.host", host); //SMTP Host
        props.put("mail.smtp.port", port); //TLS Port
        props.put("mail.smtp.auth", smtp_auth); //enable authentication
        props.put("mail.smtp.starttls.enable", starttls_enable); //enable STARTTLS

        String uniqueCode = String.valueOf(UUID.randomUUID());
        String md5Hash = MD5Util.md5(uniqueCode);

        EmailVerificationDetails emailVerificationDetails = new EmailVerificationDetails();
        emailVerificationDetails.setEmailID(email);
        emailVerificationDetails.setHashCode(md5Hash);
        repoEmailVerificationDetails.save(emailVerificationDetails);

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, emailPassword);
            }
        };
        System.out.println("Message sending");
        Session session = Session.getInstance(props, auth);
        String body = "<html>\n" +
                "<head>\n" +
                "\t<title></title>\n" +
                "\t<link href=\"https://svc.webspellchecker.net/spellcheck31/lf/scayt3/ckscayt/css/wsc.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
                "</head>\n" +
                "<body data-gr-ext-disabled=\"forever\" data-gr-ext-installed=\"\" data-new-gr-c-s-check-loaded=\"14.1125.0\">Hi " + firstName + ",<br />\n" +
                "<strong>Welcome to Task Orbit!</strong><br />\n" +
                "To complete the verification process, kindly click on the below link<br />\n" +
                "<a href=\"" + baseUrl + "/auth/verify/"+md5Hash+ "\">" + baseUrl + "/auth/verify/"+ md5Hash+ "</a><br />\n" +
                "&nbsp;</body>\n" +
                "</html>";
        EmailUtil.sendEmail(session, toEmail,"Task Orbit E-Mail Verification", body);
        return new ResponseEntity<>("User signup successful", HttpStatus.CREATED);
    }


    public ResponseEntity<String> verifyUser(@PathVariable String hashCode)
    {
        System.out.println("Inside Verify Function");
        EmailVerificationDetails emailVerificationDetails= repoEmailVerificationDetails.findByHashCode(hashCode);
        if(emailVerificationDetails != null) {
            System.out.println("Inside If ");
            String email = emailVerificationDetails.getEmailID();
            System.out.println(email);
            Optional<User> user = repoUser.findByEmailID(email);
            System.out.println(user.get().getEmailID());
            user.ifPresent(value -> value.setIsEmailVerified(true));
            repoUser.save(user.get());
            return new ResponseEntity<>("Email Id Verified Successfully", HttpStatus.ACCEPTED);
        }
        else
            return new ResponseEntity<>("Error in verifying Email Id",HttpStatus.BAD_REQUEST);
    }
    public List<User> getUsers()
    {
        return repoUser.findAll();
    }
}