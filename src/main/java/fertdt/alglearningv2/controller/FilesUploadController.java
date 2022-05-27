package fertdt.alglearningv2.controller;

import fertdt.alglearningv2.dto.FileLinkDto;
import fertdt.alglearningv2.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/AlgLearning/files")
public class FilesUploadController {
    private final FileService fileService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/upload")
    @ResponseBody
    public FileLinkDto upload(@RequestParam("background-img") MultipartFile multipart) {
        log.debug("Uploading file " + multipart.getOriginalFilename());
        return fileService.upload(multipart);
    }

    @GetMapping("/{file-name:.+}")
    public void getFile(@PathVariable("file-name") String fileName, HttpServletResponse response) {
        fileService.addFileToResponse(fileName, response);
    }

}
