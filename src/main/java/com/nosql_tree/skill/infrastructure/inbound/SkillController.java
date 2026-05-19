package com.nosql_tree.skill.infrastructure.inbound;

import com.nosql_tree.skill.domain.dto.SkillRequest;
import com.nosql_tree.skill.domain.dto.SkillTreeMap;
import com.nosql_tree.skill.domain.model.Skill;
import com.nosql_tree.skill.domain.ports.inbound.CreateSkillPort;
import com.nosql_tree.skill.domain.ports.inbound.DeleteSkillPort;
import com.nosql_tree.skill.domain.ports.inbound.ReadSkillPort;
import com.nosql_tree.skill.domain.ports.inbound.UpdateSkillPort;
import com.nosql_tree.skill.infrastructure.outbound.SkillMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * SkillController.java
 * <p>
 * Description: [Add the description of the class]
 *
 * @author aleja
 * @since 17/05/2026
 */

@Slf4j
@RequestMapping("/api/skill")
@RestController
public class SkillController {
    private final CreateSkillPort createSkillPort;
    private final ReadSkillPort readSkillPort;
    private final DeleteSkillPort deleteSkillPort;
    private final UpdateSkillPort updateSkillPort;

    public SkillController(CreateSkillPort createSkillPort, ReadSkillPort readSkillPort, DeleteSkillPort deleteSkillPort, UpdateSkillPort updateSkillPort) {
        this.createSkillPort = createSkillPort;
        this.readSkillPort = readSkillPort;
        this.deleteSkillPort = deleteSkillPort;
        this.updateSkillPort = updateSkillPort;
    }

    @PostMapping
    public ResponseEntity<Skill> createSkill(@RequestBody SkillRequest request){
        log.info("REST request to create skill: {}", request.name());

        Skill newSkill = SkillMapper.toDomain(request);

        createSkillPort.createSkill(newSkill, request.requirements());
        log.info("Skill {} Created successfully", newSkill.getSlug());

        return new ResponseEntity<>(newSkill, HttpStatus.CREATED);
    }

    @PutMapping("/{slug}")
    public ResponseEntity<Skill> updateSkill(@PathVariable String slug, @RequestBody SkillRequest request){
        log.info("REST request to update current skill {} slug" , slug);

        Skill updatedSkill = SkillMapper.toDomain(request);

        Skill result = updateSkillPort.update(slug,updatedSkill,request.requirements());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<Skill> getBySlug(@PathVariable String slug){
        return ResponseEntity.ok(readSkillPort.findBySlug(slug));
    }

    @GetMapping("/tree")
    public ResponseEntity<SkillTreeMap> getFullTree(){
        log.info("REST request to get full skill tree");
        return ResponseEntity.ok(readSkillPort.getFullTree());
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> deleteBySlug(@PathVariable String slug){
        log.info("REST request to delete {} that leaf",slug);
        deleteSkillPort.deleteBySlug(slug);
        return ResponseEntity.ok().build();
    }
}
