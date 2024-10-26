package com.simpsonfans.simpsons_list.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simpsonfans.simpsons_list.model.SimpsonsCharacter;

public interface SimpsonsRepository extends JpaRepository<SimpsonsCharacter, UUID> {
	List<SimpsonsCharacter> findByGUID(UUID guid);
}
