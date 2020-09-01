package com.library.librarymicroservice.services;

import com.library.librarymicroservice.model.Library;
import com.library.librarymicroservice.repository.LibraryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;


class LibraryServiceImplTest {

    @InjectMocks
    LibraryServiceImpl service;

    @Mock
    LibraryRepository repository;

    @Mock
    LibraryMapperImpl mapper;

    Library library1;

    Library library2;

    LibraryDTO libraryDTO;

    List<Library> libraryList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        library1 = Library.builder().id((long) 1).name("test").address("test").phone("test").build();
        library2 = Library.builder().id((long) 2).name("test2").address("test2").phone("test2").build();
        libraryDTO = LibraryDTO.builder().id((long) 1).name("test").address("test").phone("test").build();
    }

    @Test
    void getLibrarys_GivenList_ReturnList() {
        libraryList.add(library1);
        libraryList.add(library2);

        when(repository.findAll()).thenReturn(libraryList);

        assertThat(service.getLibrarys().size()).isEqualTo(2);
    }

    @Test
    void getLibrary_GivenLibrary_ReturnLibrary() {
        when(repository.getOne((long) 1)).thenReturn(library1);

        assertThat(service.getLibrary((long)1)).isEqualTo(library1);
    }

    @Test
    void createLibrary_GivenLibrary_ReturnLibrary() {
        when(repository.save(mapper.librabyDtoToLibrary(libraryDTO))).thenReturn(library1);
        assertThat(service.createLibrary(libraryDTO)).isEqualTo(library1);
    }

    @Test
    void updateLibrary() {
    }

    @Test
    void deleteLibrary() {
    }
}
