package raf.vp.jmijatovic11421rn.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.codec.digest.DigestUtils;
import raf.vp.jmijatovic11421rn.entities.User;
import raf.vp.jmijatovic11421rn.repositories.user.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.List;


public class UserService {

    @Inject
    UserRepository userRepository;

    public User getUser(String email) {
        return this.userRepository.findUser(email);
    }

    public String login(String email, String password) {
        String hashPassword = DigestUtils.sha256Hex(password);

        User user = this.userRepository.findUser(email);
        if (user == null || !user.getPassword().equals(hashPassword) || !user.isActive()) {
            return null;
        }
        Date issuedAt = new Date();

        Algorithm algorithm = Algorithm.HMAC256("secret");

        return JWT.create()
                .withIssuedAt(issuedAt)
                .withSubject(email)
                .withClaim("type", user.getUserType())
                .sign(algorithm);
    }

    public List<User> getAllUsers() {
        return this.userRepository.getAllUsers();
    }

    public List<User> getUsersByPage(Integer page) {
        return this.userRepository.getUsersByPage(page);
    }

    public void editUser(String email, User user) {
        this.userRepository.editUser(email, user);
    }
    public void addUser(User user) {
        this.userRepository.addUser(user);
    }

    public void setUserActive(Boolean active, String email) {
        this.userRepository.setUserActive(active, email);
    }

    public boolean isAuthorized(String token) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        String email = jwt.getSubject();
        User user = this.userRepository.findUser(email);

        return user != null;
    }
}
