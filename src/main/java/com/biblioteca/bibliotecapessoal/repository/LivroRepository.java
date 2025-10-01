package com.biblioteca.bibliotecapessoal.repository;

import com.biblioteca.bibliotecapessoal.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    
    // Métodos customizados podem ser adicionados aqui se necessário
    // Por exemplo:
    // List<Livro> findByAutor(String autor);
    // List<Livro> findByLido(boolean lido);
    // List<Livro> findByAnoPublicacao(int ano);
}
