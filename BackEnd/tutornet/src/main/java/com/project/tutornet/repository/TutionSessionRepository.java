package com.project.tutornet.repository;
import java.util.UUID;

import com.project.tutornet.entity.TutionSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TutionSessionRepository extends JpaRepository<TutionSession, UUID>  {
   // boolean exexistsBytse (UUID tsessionId);
}
