package fertdt.alglearningv2.service.impl;

import fertdt.alglearningv2.dto.FileLinkDto;
import fertdt.alglearningv2.exception.FileNotFoundException;
import fertdt.alglearningv2.model.FileInfoEntity;
import fertdt.alglearningv2.repository.FileInfoRepository;
import fertdt.alglearningv2.service.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    @Value("${user.backgrounds.storage.path}")
    private String storagePath;

    @Value("${user.backgrounds.url}")
    private String filesUrl;

    private final FileInfoRepository fileInfoRepository;


    @Override
    public FileLinkDto upload(MultipartFile multipart) {
        try {
            String extension = multipart.getOriginalFilename().substring(multipart.getOriginalFilename().lastIndexOf("."));
            String storageFileName = UUID.randomUUID() + extension;
            FileInfoEntity file = FileInfoEntity.builder()
                    .type(multipart.getContentType())
                    .originalFileName(multipart.getOriginalFilename())
                    .storageFileName(storageFileName)
                    .size(multipart.getSize())
                    .build();
            Files.copy(multipart.getInputStream(), Paths.get(storagePath, file.getStorageFileName()));
            fileInfoRepository.save(file);
            return FileLinkDto.builder()
                    .link(filesUrl + file.getStorageFileName())
                    .build();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public void addFileToResponse(String fileName, HttpServletResponse response) {
        FileInfoEntity file = fileInfoRepository.findByStorageFileName(fileName).orElseThrow(FileNotFoundException::new);
        response.setContentLength(file.getSize().intValue());
        response.setContentType(file.getType());
        response.setHeader("Content-Disposition", "filename=\"" + file.getOriginalFileName() + "\"");
        try {
            IOUtils.copy(new FileInputStream(storagePath + "\\" + file.getStorageFileName()), response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public void delete(String filePath) {
        String fileName = filePath.substring(filesUrl.length());
        FileInfoEntity file = fileInfoRepository.findByStorageFileName(fileName).orElseThrow(FileNotFoundException::new);
        fileInfoRepository.delete(file);
        try {
            Files.delete(Path.of(storagePath, file.getStorageFileName()));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
