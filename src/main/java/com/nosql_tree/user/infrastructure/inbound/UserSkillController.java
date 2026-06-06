package com.nosql_tree.user.infrastructure.inbound;

import com.nosql_tree.skill.domain.exception.SkillNotFoundException;
import com.nosql_tree.user.domain.exception.UserNotFoundException;
import com.nosql_tree.user.domain.ports.inbound.UserProgressPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * UserSkillController.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 30/05/2026
 */

@Slf4j
@RequestMapping("/api/me/skills")
@PreAuthorize("isAuthenticated()")
@RestController
public class UserSkillController {
    private final UserProgressPort userProgressPort;

    public UserSkillController(UserProgressPort userProgressPort) {
        this.userProgressPort = userProgressPort;
    }

    @GetMapping
    public ResponseEntity<Set<String>> getCompletedSkills(@AuthenticationPrincipal UserDetails userDetails){
        try {
            String email = userDetails.getUsername();
            Set<String> completedSkills = userProgressPort.getCompletedSkills(email);
            return ResponseEntity.ok(completedSkills);
        }catch (IllegalArgumentException | UserNotFoundException ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{slug}")
    public ResponseEntity<Void> addCompletedSkill(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String slug){
        try{
            String email = userDetails.getUsername();

            userProgressPort.addCompleteSkill(email,slug);
            return ResponseEntity.ok().build();
        }catch (IllegalArgumentException | SkillNotFoundException ex){
            return ResponseEntity.badRequest().build();
        }
    }
}
