package com.splitwisely.backend.dto;

import com.splitwisely.backend.model.Expense;
import com.splitwisely.backend.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class GroupRequestDTO {
    private String name;
    private List<User> members = new ArrayList<>();
    private List<Expense> expenses = new ArrayList<>();
    private LocalDateTime createdAt = LocalDateTime.now();

}
