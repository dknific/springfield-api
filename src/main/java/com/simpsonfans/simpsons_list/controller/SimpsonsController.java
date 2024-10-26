package com.simpsonfans.simpsons_list.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.simpsonfans.simpsons_list.model.SimpsonsCharacter;
import com.simpsonfans.simpsons_list.service.SimpsonsCharacterService;

@RestController
public class SimpsonsController {
	
	private final SimpsonsCharacterService simpsonsCharacterService;
	
	public SimpsonsController(SimpsonsCharacterService simpsonsCharacterService) {
		this.simpsonsCharacterService = simpsonsCharacterService;
	}

	@GetMapping(value = "/api/characters")
	public List<SimpsonsCharacter> getEndpoint() {
		return simpsonsCharacterService.getAllCharacters();
	}
	
	@GetMapping(value = "/api/characters/{id}")
	public List<SimpsonsCharacter> getByGuid(@PathVariable("id") String uuid) {
		return simpsonsCharacterService.getCharacterByGUID(uuid);
	}
}
