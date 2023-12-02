package com.example.easystudy.service;

import com.example.easystudy.result.Result;
import org.springframework.web.multipart.MultipartFile;

public interface NoteService {
    Result saveNote(String noteName, String note);
    Result uploadImage(MultipartFile image);
}
