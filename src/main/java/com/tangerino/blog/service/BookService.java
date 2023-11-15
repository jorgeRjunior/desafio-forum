package com.tangerino.blog.service;

import com.tangerino.blog.dto.BookDTO;
import com.tangerino.blog.entity.Book;
import com.tangerino.blog.repository.BookRepository;
import com.tangerino.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tangerino.blog.utils.ConvertUtils.convert;

@Service
@AllArgsConstructor
public class BookService {
    final private BookRepository bookRepository;
    final private UserRepository userRepository;
    public List<BookDTO> findAll() {
      return bookRepository.findAll().stream().map(book ->  convert(book, BookDTO.class)).collect(Collectors.toList());
    }

    public BookDTO findById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        BookDTO bookDTO = new BookDTO();

        if (book.isPresent()){
            BeanUtils.copyProperties(book, bookDTO);
            return bookDTO;
        }
        throw new NoSuchElementException("Livro não encontrado");
    }


    public boolean delete(Long id, String login) {
        Optional<Book> reg = bookRepository.findById(id);
        if (reg.isPresent()){
            if (reg.get().getUser().getUsername().equalsIgnoreCase(login)){
                bookRepository.delete(reg.get());
                return true;
            }
        }
        return false;
    }

    public void saveBook(BookDTO bookDTO, String username) {
        var book = new Book();
        BeanUtils.copyProperties(bookDTO, book);
        book.setUser(userRepository.findByUsername(username).orElseThrow());
        bookRepository.save(book);
    }
}
