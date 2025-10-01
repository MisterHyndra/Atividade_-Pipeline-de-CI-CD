package com.biblioteca.bibliotecapessoal.controller;

import com.biblioteca.bibliotecapessoal.model.Livro;
import com.biblioteca.bibliotecapessoal.repository.LivroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LivroControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LivroRepository livroRepository;

    @Test
    public void testCriarLivro() {
        Livro livro = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", 1954, true);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Livro> request = new HttpEntity<>(livro, headers);
        
        ResponseEntity<Livro> response = restTemplate.postForEntity("/api/livros", request, Livro.class);
        
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("O Senhor dos Anéis", response.getBody().getTitulo());
        assertEquals("J.R.R. Tolkien", response.getBody().getAutor());
        assertEquals(1954, response.getBody().getAnoPublicacao());
        assertTrue(response.getBody().isLido());
    }

    @Test
    public void testListarLivros() {
        // Criar um livro primeiro
        Livro livro = new Livro("1984", "George Orwell", 1949, false);
        livroRepository.save(livro);

        ResponseEntity<Livro[]> response = restTemplate.getForEntity("/api/livros", Livro[].class);
        
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    public void testBuscarLivroPorId() {
        // Criar um livro primeiro
        Livro livro = new Livro("Dom Casmurro", "Machado de Assis", 1899, true);
        Livro livroSalvo = livroRepository.save(livro);

        ResponseEntity<Livro> response = restTemplate.getForEntity("/api/livros/" + livroSalvo.getId(), Livro.class);
        
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Dom Casmurro", response.getBody().getTitulo());
        assertEquals("Machado de Assis", response.getBody().getAutor());
    }

    @Test
    public void testAtualizarLivro() {
        // Criar um livro primeiro
        Livro livro = new Livro("Cem Anos de Solidão", "Gabriel García Márquez", 1967, false);
        Livro livroSalvo = livroRepository.save(livro);

        // Atualizar o livro
        livroSalvo.setLido(true);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Livro> request = new HttpEntity<>(livroSalvo, headers);

        ResponseEntity<Livro> response = restTemplate.exchange("/api/livros/" + livroSalvo.getId(), 
                HttpMethod.PUT, request, Livro.class);
        
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isLido());
    }

    @Test
    public void testDeletarLivro() {
        // Criar um livro primeiro
        Livro livro = new Livro("A Arte da Guerra", "Sun Tzu", -500, false);
        Livro livroSalvo = livroRepository.save(livro);

        ResponseEntity<Void> response = restTemplate.exchange("/api/livros/" + livroSalvo.getId(), 
                HttpMethod.DELETE, null, Void.class);
        
        assertEquals(204, response.getStatusCodeValue());
    }
}