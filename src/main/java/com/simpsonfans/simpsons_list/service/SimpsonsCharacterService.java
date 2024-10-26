package com.simpsonfans.simpsons_list.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
	
	public List<SimpsonsCharacter> getCharacterByGUID(String guid) {
		try {
			UUID convertedGUID = UUID.fromString(guid);
			return simpsonsRepository.findByGUID(convertedGUID);
		} catch (IllegalArgumentException error) {
			List<SimpsonsCharacter> output = new ArrayList<SimpsonsCharacter>();
			return output;
		}
	}
}
