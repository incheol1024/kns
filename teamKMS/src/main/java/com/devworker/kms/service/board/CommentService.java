package com.devworker.kms.service.board;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.devworker.kms.dao.UserDao;
import com.devworker.kms.dao.board.BoardDao;
import com.devworker.kms.dao.board.CommentDao;
import com.devworker.kms.dic.LikeType;
import com.devworker.kms.exception.FileTransactionException;
import com.devworker.kms.exception.NotExistException;
import com.devworker.kms.repo.UserRepo;
import com.devworker.kms.repo.board.CommentRepo;
import com.devworker.kms.repo.board.DocRepo;
import com.devworker.kms.util.CommonUtil;
import com.devworker.kms.util.FileTransactionUtil;

/**
 * Comment Service 클래스 입니다. Comment와 관련 된 CRUD 메소드가 구현되어 있습니다.
 * 
 * @author Hwang In Cheol
 */

@Service
public class CommentService {

	@Autowired
	CommentRepo commentRepo;

	@Autowired
	DocRepo docRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	@Qualifier("fileHandlerImplLocal")
	FileHandler fileHandler;

	/**
	 * Comment(댓글) 등록 시 호출 되는 메소드입니다.
	 * 
	 * @param
	 * @return CommentDao
	 */
	public CommentDao addComment(CommentDao commentDao) throws Exception {
		String userId = CommonUtil.getCurrentUser();
		if (userId == null)
			throw new NotExistException("userId");

		Optional<UserDao> optionalUserDao = userRepo.findById(userId);
		commentDao.setCmtUserId(optionalUserDao.get().getName());
		return commentRepo.save(commentDao);
	}

	public CommentDao addComment(CommentDao commentDao, String fileTransactKey, int fileCount) throws Exception {
		String userId = CommonUtil.getCurrentUser();
		if (userId == null)
			throw new NotExistException("userId");

		Optional<UserDao> optionalUserDao = userRepo.findById(userId);
		commentDao.setCmtUserId(optionalUserDao.get().getName());

		if (FileTransactionUtil.isSameTransaction(fileTransactKey, fileCount)) {
			
			throw new FileTransactionException();
		}
		// 동일 트랜잭션이 아니면 예외를 던집니다.

		FileTransactionUtil.removeFileInfoMemory(fileTransactKey);
		// 동일 트랜잭션에서 파일이 처리되었는지 확인되면 해당 키에
		// 메모리를 해제합니다.

		// 조건2. 파일 등록처리가 완료 되면 메모리를 해제해야 한다.
		// 조건3. 파일 등록 처리를 하다가 예외가 발생하면 메모리를 해제해야한다.

		return commentRepo.save(commentDao);
	}

	public List<CommentDao> findByBoardId(BoardDao boardId) throws Exception {
		return commentRepo.findByBoardId(boardId);
	}

	/**
	 * Comment(댓글) 수정 시 호출 되는 메소드입니다.
	 * 
	 * @param
	 * @return CommentDao
	 */
	public CommentDao updateComment(CommentDao commentDao) throws Exception {

		Optional<CommentDao> opComment = commentRepo.findById(commentDao.getCmtId());
		CommentDao newComment = opComment.get();
		newComment.setCmtContents(commentDao.getCmtContents());

		CommentDao newCommentDao = commentRepo.save(newComment);

		if (newCommentDao == null)
			throw new RuntimeException();

		return newCommentDao;
	}

	/**
	 * Comment(댓글) 삭제 시 호출되는 메소드입니다.
	 * 
	 * @param cmtId must not be {@literal null}. Comment ID 값이 되야 합니다.
	 * 
	 */
	public void deleteComment(Integer cmtId) {
		commentRepo.deleteById(cmtId);
	}

	/**
	 * 댓글의 좋아요 버튼 기능입니다.
	 * 
	 * @param commentDao must not be {@literal null}.
	 * @return CommentDao
	 */
	public CommentDao updateCommentLike(int cmtId) {

		Optional<CommentDao> opComment = commentRepo.findById(cmtId);

		if (!opComment.isPresent())
			throw new RuntimeException();

		CommentDao oldComment = opComment.get();
		oldComment.setCmtLike(LikeType.LIKE.getLikeValue());

		return commentRepo.save(oldComment);
	}

	/**
	 * 댓글의 싫어요 버튼 기능입니다.
	 * 
	 * @param commentDao must not be {@literal null}.
	 * @return CommentDao
	 */
	public CommentDao updateCommentUnLike(CommentDao commentDao) {
		Optional<CommentDao> opComment = commentRepo.findById(commentDao.getCmtId());

		if (!opComment.isPresent())
			throw new RuntimeException();

		CommentDao oldComment = opComment.get();
		oldComment.setCmtUnlike(LikeType.UNLIKE.getLikeValue());

		return commentRepo.save(oldComment);

	}

}