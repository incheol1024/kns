package com.devworker.kms.controller.solution;

import java.util.List;

import javax.validation.Valid;

import com.devworker.kms.dto.common.BoardDto;
import com.devworker.kms.dto.solution.SolutionDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.component.BoardComponent;
import com.devworker.kms.service.solution.SolutionService;

@RestController
@RequestMapping("/solution")
public class SolutionController {
	private Logger logger = LoggerFactory.getLogger(SolutionController.class);
	
	@Autowired
	SolutionService solutionService;

	@Autowired
	BoardComponent boardComponent;
	
	@GetMapping("")
	public List<BoardDao> solutionHome(){
		List<BoardDao> list = solutionService.getFirstPageList();
		return list;
	}
	
	@PostMapping("/register")
	public BoardDao solutionRegiser(@Valid @RequestBody BoardDto boardDao, SolutionDto solutionDao) {
		solutionService.registerSolution(solutionDao);
		return boardComponent.register(boardDao);
	}
	
	@PostMapping("/edit")
	public BoardDao solutionEdit(@Valid @RequestBody BoardDto boardDao) {
		return boardComponent.edit(boardDao);
	}
	
	@DeleteMapping("/{boardId}")
	public void solutionDelete(@PathVariable String boardId) {
		boardComponent.delete(Integer.parseInt(boardId));
	}
	
	@GetMapping("/answer/{id}")
	public BoardDao getSolutionById(@PathVariable Long id) {
		System.out.println(" -- answerid : " + id);
		return solutionService.findById(id).get();
	}
}
