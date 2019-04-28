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
import com.devworker.kms.entity.solution.SolutionDao;
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
	
	@GetMapping("/{menuId}")
	public List<BoardDao> solutionHome(@PathVariable String menuId){
		List<BoardDao> list = solutionService.getFirstPageList(menuId);
		return list;
	}
	
	@PostMapping("/register")
	public BoardDao solutionRegiser(@Valid @RequestBody BoardDto boardDao, @Valid @RequestBody SolutionDto solutionDao) {
		BoardDao BD = boardComponent.register(boardDao);
		solutionDao.setBoardId(BD.getBoardId());
		solutionService.registerSolution(solutionDao);
		return BD;
	}
	
	@PostMapping("/edit")
	public BoardDao solutionEdit(@Valid @RequestBody BoardDto boardDao, @Valid @RequestBody SolutionDto solutionDao) {
		solutionService.editSolution(solutionDao);
		return boardComponent.edit(boardDao);
	}
	
	@DeleteMapping("/{boardId}")
	public void solutionDelete(@PathVariable String boardId) {
		boardComponent.delete(Integer.parseInt(boardId));
	}
	
	@GetMapping("/answer/{id}")
	public SolutionDao getSolutionById(@PathVariable Long id) {
		SolutionDao sd = null;
		if(id != 0)
			sd = solutionService.findById(id).get();		
		return sd;
	}
}
