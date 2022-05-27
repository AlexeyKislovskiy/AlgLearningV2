package fertdt.alglearningv2.service;

import fertdt.alglearningv2.dto.FileLinkDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface FileService {
    FileLinkDto upload(MultipartFile multipart);

    void addFileToResponse(String fileName, HttpServletResponse response);

    void delete(String fileName);
}
