package com.simpsonfans.simpsons_list.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.simpsonfans.simpsons_list.model.SimpsonsCharacter;
import com.simpsonfans.simpsons_list.repository.SimpsonsRepository;

@Service
public class SimpsonsCharacterService {
	
	private final SimpsonsRepository simpsonsRepository;
	
	public SimpsonsCharacterService(SimpsonsRepository simpsonsRepository) {
		this.simpsonsRepository = simpsonsRepository;
	}
	
	public List<SimpsonsCharacter> getAllCharacters() {
		return simpsonsRepository.findAll();
	}
}
