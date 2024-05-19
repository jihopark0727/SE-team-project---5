package com.example.demo.Service;
import com.example.demo.Repository.CommentRepository;
import com.example.demo.DTO.CommentDto;
import com.example.demo.Entity.Comment;
import com.example.demo.DTO.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    public ResponseDto<?> addComment(CommentDto commentDto) {
        commentDto.setCreation_time(new Date());
        commentRepository.save(new Comment(commentDto));
        return ResponseDto.setSuccess("코멘트 생성에 성공했습니다.");
    }
}
