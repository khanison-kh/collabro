package com.khanison.collabro.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a project.
 * Contains the tasks to do and the members that participate on the project.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectUser> projectUsers = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Task> tasks = new HashSet<>();

    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void addMember(User user, ProjectUserRole role) {
        boolean alreadyMember = projectUsers.stream()
                .anyMatch(pu -> pu.getUser().equals(user));
        if (alreadyMember)
            return;

        ProjectUser projectUser = new ProjectUser();

        ProjectUserId projectUserId = new ProjectUserId(this.id, user.getId());
        projectUser.setId(projectUserId);
        projectUser.setProject(this);
        projectUser.setUser(user);
        projectUser.setRole(role);
        projectUser.setJoinedAt(LocalDateTime.now());

        projectUsers.add(projectUser);
        user.getProjectUsers().add(projectUser);
    }

    public void removeMember(User user) {
        projectUsers.removeIf(pu -> pu.getUser().equals(user));

        user.getProjectUsers().removeIf(pu -> pu.getProject().equals(this));
    }

}
