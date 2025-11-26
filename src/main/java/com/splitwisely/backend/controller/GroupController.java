package com.splitwisely.backend.controller;

import com.splitwisely.backend.dto.BalanceUpdateDTO;
import com.splitwisely.backend.dto.GroupRequestDTO;
import com.splitwisely.backend.dto.GroupResponseDTO;
import com.splitwisely.backend.service.GroupService;
import com.splitwisely.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;

    // ------------------------ GET ALL GROUPS FOR USER ------------------------
    @GetMapping
    public ResponseEntity<List<GroupResponseDTO>> getAllUserGroups() {
        return ResponseEntity.ok(groupService.getAllUserGroups());
    }

    // ------------------------ CREATE GROUP ------------------------
    @PostMapping
    public ResponseEntity<String> createGroup(@RequestBody GroupRequestDTO dto) {
        String groupId = groupService.createNewGroup(dto);
        return ResponseEntity.ok(groupId);
    }

    // ------------------------ DELETE GROUP ------------------------
    @DeleteMapping("/{groupId}")
    public ResponseEntity<Boolean> deleteGroup(@PathVariable String groupId) {
        Boolean deleted = groupService.deleteGroupPerm(groupId);
        return ResponseEntity.ok(deleted);
    }

    // ------------------------ ADD USER TO GROUP ------------------------
    @PostMapping("/{groupId}/users/{userId}")
    public ResponseEntity<String> addUserToGroup(
            @PathVariable String groupId,
            @PathVariable String userId
    ) {
        String response = groupService.addUserToGroup(groupId, userId);
        return ResponseEntity.ok(response);
    }

    // ------------------------ REMOVE USER FROM GROUP ------------------------
    @DeleteMapping("/{groupId}/users/{userId}")
    public ResponseEntity<String> removeUserFromGroup(
            @PathVariable String groupId,
            @PathVariable String userId
    ) {
        String response = groupService.removeUserFromGroup(groupId, userId);
        return ResponseEntity.ok(response);
    }

    // ------------------------ UPDATE BALANCES ------------------------
    @PostMapping("/{groupId}/balances")
    public ResponseEntity<Boolean> updateBalances(
            @PathVariable String groupId,
            @RequestBody List<BalanceUpdateDTO> updates
    ) {
        boolean success = userService.updateUserBalances(groupId, updates);
        return success
                ? ResponseEntity.ok(true)
                : ResponseEntity.badRequest().body(false);
    }
}
