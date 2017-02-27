package com.technical.test;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Tom Longdon
 */
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findActivityByTitle(String title);
    List<Activity> findActivityByContactId(Long contactId);
}
