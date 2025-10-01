package com.biblioteca.bibliotecapessoal.controller;

import com.biblioteca.bibliotecapessoal.model.Livro;
import com.biblioteca.bibliotecapessoal.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/livros")
@CrossOrigin(origins = "*")
public class LivroController {
    
    @Autowired
    private LivroRepository livroRepository;
    
    // GET /api/livros - Listar todos os livros
    @GetMapping
    public ResponseEntity<List<Livro>> listarTodosLivros() {
        List<Livro> livros = livroRepository.findAll();
        return ResponseEntity.ok(livros);
    }
    
    // GET /api/livros/{id} - Buscar livro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarLivroPorId(@PathVariable Long id) {
        Optional<Livro> livro = livroRepository.findById(id);
        if (livro.isPresent()) {
            return ResponseEntity.ok(livro.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // POST /api/livros - Criar novo livro
    @PostMapping
    public ResponseEntity<Livro> criarLivro(@RequestBody Livro livro) {
        try {
            Livro livroSalvo = livroRepository.save(livro);
            return ResponseEntity.status(HttpStatus.CREATED).body(livroSalvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // PUT /api/livros/{id} - Atualizar livro existente
    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizarLivro(@PathVariable Long id, @RequestBody Livro livroAtualizado) {
        Optional<Livro> livroExistente = livroRepository.findById(id);
        if (livroExistente.isPresent()) {
            Livro livro = livroExistente.get();
            livro.setTitulo(livroAtualizado.getTitulo());
            livro.setAutor(livroAtualizado.getAutor());
            livro.setAnoPublicacao(livroAtualizado.getAnoPublicacao());
            livro.setLido(livroAtualizado.isLido());
            
            Livro livroSalvo = livroRepository.save(livro);
            return ResponseEntity.ok(livroSalvo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // DELETE /api/livros/{id} - Deletar livro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id) {
        Optional<Livro> livro = livroRepository.findById(id);
        if (livro.isPresent()) {
            livroRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
