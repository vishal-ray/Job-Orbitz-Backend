package com.alpha.TaskOrbit.repositories;

import com.alpha.TaskOrbit.modules.EmailVerificationDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoEmailVerificationDetails extends JpaRepository<EmailVerificationDetails,Long> {
    EmailVerificationDetails findByHashCode(String hashCode);
}
