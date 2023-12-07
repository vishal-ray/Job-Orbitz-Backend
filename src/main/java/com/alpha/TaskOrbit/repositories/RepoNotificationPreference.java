package com.alpha.TaskOrbit.repositories;

import com.alpha.TaskOrbit.modules.NotificationPreference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoNotificationPreference extends JpaRepository<NotificationPreference,Long> {
}
