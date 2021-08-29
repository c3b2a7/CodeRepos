package me.lolico.learning.springboot.web.controller;

import me.lolico.learning.springboot.common.annotation.CheckParam;
import me.lolico.learning.springboot.common.util.CaptchaGenerator;
import me.lolico.learning.springboot.pojo.vo.ResponseWithPageVO;
import me.lolico.learning.springboot.pojo.vo.TestVO;
import me.lolico.learning.springboot.repository.UserRepository;
import me.lolico.learning.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author lolico
 */
@Slf4j
@RequestMapping("/api/test")
@RestController
public class TestController {
    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService userService;

    @GetMapping("/date")
    public LocalDateTime getDate(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime date) {
        System.out.println(date);
        return date;
    }

    @PostMapping("/v")
    public TestVO post(@RequestBody TestVO testVO) {
        System.out.println(testVO);
        return testVO;
    }

    @GetMapping("/now")
    public LocalDateTime now() {
        return LocalDateTime.now();
    }

    @GetMapping(value = "/img", produces = MediaType.IMAGE_JPEG_VALUE)
    public BufferedImage getImage() {
        return CaptchaGenerator.getInstance().generateImage();
    }

    @GetMapping(value = "/img2", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage2() throws IOException {
        BufferedImage bufferedImage = CaptchaGenerator.getInstance().generateImage();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpeg", outputStream);
        return outputStream.toByteArray();
    }

    @GetMapping("/find")
    @CheckParam(index = {0, 1, 2})
    public ResponseWithPageVO find(String username, Integer pageNum, Integer size) {
        ResponseWithPageVO response = new ResponseWithPageVO();
        Optional.ofNullable(repository.findByUsernameLike(username, PageRequest.of(pageNum - 1, size, Sort.by(Sort.Direction.DESC, "id"))))
                .ifPresent(page -> {
                    response.setCode(200);
                    response.setTotalPages(page.getTotalPages());
                    response.setTotalElements(page.getTotalElements());
                    response.setNumberOfElements(page.getNumberOfElements());
                    response.setPageNumber(page.getNumber() + 1);
                    response.setPageSize(page.getSize());
                    response.setContent(page.getContent());
                });
        return response;
    }

}
