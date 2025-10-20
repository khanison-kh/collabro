package com.khanison.collabro.Entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Defines the composite key consisting of project and user IDs used to uniquely
 * identify a ProjectUser.
 */
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ProjectUserId implements Serializable {

    private Long projectId;

    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProjectUserId projectUserId = (ProjectUserId) o;
        return Objects.equals(projectId, projectUserId.projectId)
                && Objects.equals(userId, projectUserId.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, userId);
    }
}
