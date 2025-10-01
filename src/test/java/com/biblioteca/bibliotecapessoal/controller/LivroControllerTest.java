package com.biblioteca.bibliotecapessoal.controller;

import com.biblioteca.bibliotecapessoal.model.Livro;
import com.biblioteca.bibliotecapessoal.repository.LivroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureTestMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureTestMvc
public class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCriarLivro() throws Exception {
        Livro livro = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", 1954, true);
        
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(livro)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value("O Senhor dos Anéis"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.autor").value("J.R.R. Tolkien"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.anoPublicacao").value(1954))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lido").value(true))
                .andDo(print());
    }

    @Test
    public void testListarLivros() throws Exception {
        // Criar um livro primeiro
        Livro livro = new Livro("1984", "George Orwell", 1949, false);
        livroRepository.save(livro);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/livros")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andDo(print());
    }

    @Test
    public void testBuscarLivroPorId() throws Exception {
        // Criar um livro primeiro
        Livro livro = new Livro("Dom Casmurro", "Machado de Assis", 1899, true);
        Livro livroSalvo = livroRepository.save(livro);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/livros/" + livroSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value("Dom Casmurro"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.autor").value("Machado de Assis"))
                .andDo(print());
    }

    @Test
    public void testAtualizarLivro() throws Exception {
        // Criar um livro primeiro
        Livro livro = new Livro("Cem Anos de Solidão", "Gabriel García Márquez", 1967, false);
        Livro livroSalvo = livroRepository.save(livro);

        // Atualizar o livro
        livroSalvo.setLido(true);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/livros/" + livroSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(livroSalvo)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lido").value(true))
                .andDo(print());
    }

    @Test
    public void testDeletarLivro() throws Exception {
        // Criar um livro primeiro
        Livro livro = new Livro("A Arte da Guerra", "Sun Tzu", -500, false);
        Livro livroSalvo = livroRepository.save(livro);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/livros/" + livroSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(print());
    }
}
