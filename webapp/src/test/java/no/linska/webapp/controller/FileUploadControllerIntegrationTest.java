package no.linska.webapp.controller;


import no.linska.webapp.exception.StorageFileNotFoundException;
import no.linska.webapp.service.StorageService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class FileUploadControllerIntegrationTest {



    @Autowired
    private MockMvc mvc;

    @MockBean
    private StorageService storageService;

    @Test
    @WithMockUser(value = "spring")
    public void shouldListAllFiles() throws Exception {
        given(this.storageService.loadAll())
                .willReturn(Stream.of(Paths.get("first.txt"), Paths.get("second.txt")));

        this.mvc.perform(get("/upload")).andExpect(status().isOk())
                .andDo(print())
                .andExpect(model().attribute("files",
                        Matchers.contains("http://localhost/upload/files/first.txt",
                                "http://localhost/upload/files/second.txt")));
    }

    @Test
    @WithMockUser(value = "spring")
    public void shouldSaveUploadedFile() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", "Spring Framework".getBytes());
        this.mvc.perform(multipart("/upload").file(multipartFile).with(csrf()))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/upload"));

        then(this.storageService).should().store(multipartFile);
    }

    @SuppressWarnings("unchecked")
    @Test
    @WithMockUser(value = "spring")
    public void should404WhenMissingFile() throws Exception {
        given(this.storageService.loadAsResource("test.txt"))
                .willThrow(StorageFileNotFoundException.class);

        this.mvc.perform(get("/files/test.txt")).andExpect(status().isNotFound());
    }

}

