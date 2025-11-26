package com.splitwisely.backend.service;

import com.splitwisely.backend.dto.GroupRequestDTO;
import com.splitwisely.backend.dto.GroupResponseDTO;
import com.splitwisely.backend.model.Group;
import com.splitwisely.backend.model.User;
import com.splitwisely.backend.repository.GroupRepository;
import com.splitwisely.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    // Get all groups
    public List<GroupResponseDTO> getAllUserGroups() {
        List<Group> groups = groupRepository.findAll();

        return groups.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Create a new group
    public String createNewGroup(GroupRequestDTO dto) {
        Group group = new Group();
        group.setName(dto.getName());

        // Save group to DB
        Group saved = groupRepository.save(group);

        return String.valueOf(saved.getId()); // UUID string
    }

    // Delete group permanently
    public Boolean deleteGroupPerm(String groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        groupRepository.delete(group);
        return true;
    }

    // Remove user from group
    public String removeUserFromGroup(String groupId, String userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        User user = userRepository.findById(String.valueOf(Long.valueOf(userId)))
                .orElseThrow(() -> new RuntimeException("User not found"));

        group.getMembers().remove(user);
        groupRepository.save(group);

        return "User removed from group";
    }

    // Add user to group
    public String addUserToGroup(String groupId, String userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        User user = userRepository.findById(String.valueOf(Long.valueOf(userId)))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!group.getMembers().contains(user)) {
            group.getMembers().add(user);
        }

        groupRepository.save(group);
        return "User added to group";
    }

    // Convert Group → GroupResponseDTO
    private GroupResponseDTO convertToDTO(Group group) {
        return new GroupResponseDTO(
        );
    }
}
