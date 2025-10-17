package com.khanison.collabro.Entity;

/**
 * The role of a user in a project.
 * OWNER: The one who has created the project. Can manage the project, the tasks
 * and the members.
 * MAINTAINER: Can manage the tasks and the members. Cannot manage the project.
 * MEMBER: Simple member that can do the tasks assigned to them.
 */
public enum ProjectUserRole {
    OWNER,
    MAINTAINER,
    MEMBER
}
