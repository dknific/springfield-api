package com.simpsonfans.simpsons_list.controller;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simpsonfans.simpsons_list.model.SimpsonsCharacter;

@RestController
public class SimpsonsController {
	@GetMapping(value = "/")
	public ResponseEntity<ArrayList<SimpsonsCharacter>> getEndpoint() {
		System.out.println("COntroller reached!");
		SimpsonsCharacter myChar = new SimpsonsCharacter("homer", "simpsons", 32);
		ArrayList<SimpsonsCharacter> output = new ArrayList<SimpsonsCharacter>();
		output.add(myChar);
		return ResponseEntity.ok(output);
	}
}
