package com.devworker.kms.entity.common;

import com.devworker.kms.dto.common.BoardDetailDto;
import com.devworker.kms.dto.common.BoardDto;
import com.devworker.kms.util.CommonUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "KMS_BOARD")
public class BoardDao {

	@Id
	@Column( name = "board_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long boardId;

	@Column(name = "subject")
	private
	String subject;

	@Column(name = "contents")
	private
	String contents;

	@Column(name = "user_id")
	private
	String userId;

	@Column(name = "reg_date")
	private
	LocalDateTime regDate;

	@Column(name = "upd_date")
	private
	LocalDateTime updDate;

	@Column(name = "hits")
	private
	int hits;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "KMS_BOARD_DOC",
			joinColumns = @JoinColumn(name = "board_id"),
			inverseJoinColumns = @JoinColumn(name = "doc_id"))
	private List<DocDao> docDaos = new ArrayList<>();

	public BoardDao() {
	}
	public BoardDao(long boardId) {
		this.boardId = boardId;
	}

	public long getBoardId() {
		return boardId;
	}

	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public LocalDateTime getRegDate() {
		return regDate;
	}

	public void setRegDate(LocalDateTime regDate) {
		this.regDate = regDate;
	}

	public LocalDateTime getUpdDate() {
		return updDate;
	}

	public void setUpdDate(LocalDateTime updDate) {
		this.updDate = updDate;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits += hits;
	}

	public List<DocDao> getDocDaos() {
		return docDaos;
	}

	public void setDocDaos(List<DocDao> docDaos) {
		this.docDaos = docDaos;
	}

	public void addDoc(DocDao docDao) {

		if(!docDaos.contains(docDao))
			docDaos.add(docDao);
	}

	@Override
	public String toString() {
		return CommonUtil.toJson(this);
	}

	@JsonIgnore
	public BoardDto getBoardDto() {
		BoardDto boardDto = new BoardDto();
		boardDto.setBoardId(boardId);
		boardDto.setHits(hits);
		boardDto.setRegDate(regDate);
		boardDto.setSubject(subject);
		boardDto.setUpdDate(updDate);
		boardDto.setUserId(userId);
		return boardDto;
	}

	@JsonIgnore
	public BoardDetailDto getBoardDetailDto() {
		BoardDetailDto boardDto = new BoardDetailDto();
		boardDto.setBoardId(boardId);
		boardDto.setHits(hits);
		boardDto.setRegDate(regDate);
		boardDto.setSubject(subject);
		boardDto.setUpdDate(updDate);
		boardDto.setContents(contents);
		boardDto.setUserId(userId);
		return boardDto;
	}
}
