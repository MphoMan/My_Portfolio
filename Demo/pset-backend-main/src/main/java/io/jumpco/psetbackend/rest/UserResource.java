package io.jumpco.psetbackend.rest;

import io.jumpco.psetbackend.model.Authority;
import io.jumpco.psetbackend.model.PsetUser;
import io.jumpco.psetbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(value = {"*"})
@AllArgsConstructor
public class UserResource {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Page<PsetUser>> findAll(Pageable pageable){
        return ResponseEntity.ok().body(userService.findAll(pageable));
    }

    @PostMapping("/users/register")
    public ResponseEntity<PsetUser> register(@RequestBody PsetUser budgetUser){
        Authority authority = userService.findByName("ROLE_USER");
        if(authority != null){
            budgetUser.addAuthority(authority);
        }else {
            Authority userAuthority = new Authority();
            userAuthority.setName("ROLE_USER");
            userService.createAuthority(userAuthority);
            budgetUser.addAuthority(userAuthority);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(budgetUser));
    }

    @GetMapping("/users/confirm-email/{token}")
    public ResponseEntity<PsetUser> confirmEmail(@PathVariable String token){
        PsetUser confirmBudgetUser = userService.confirmEmail(token);
        return ResponseEntity.ok(confirmBudgetUser);
    }
}
