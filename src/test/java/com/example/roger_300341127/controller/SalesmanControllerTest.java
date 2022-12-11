package com.example.roger_300341127.controller;

import com.example.roger_300341127.entities.Salesman;
import com.example.roger_300341127.repositories.SalesmanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
class SalesmanControllerTest {
    Salesman salesman;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    SalesmanRepository salesmanRepository;

    @Mock
    View mockView;

    @InjectMocks
    SalesmanController salesmanController;

    @BeforeEach
    void setup() throws ParseException {

        salesman = new Salesman();
        salesman.setId(1L);
        salesman.setName("John");

        String sDate1 = "2022/11/11";
        Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(sDate1);

        salesman.setDot(date1);
        salesman.setItem("Washing Machine");

        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(salesmanController).setSingleView(mockView).build();

    }

    @Test
    public void displayTest() throws Exception {
        List<Salesman> list = new ArrayList<>();
        list.add(salesman);
        list.add(salesman);

        when(salesmanRepository.findAll()).thenReturn(list);
        mockMvc.perform(get("/list")) //you declare the path
                .andExpect(status().isOk())
                .andExpect(model().attribute("salesmanList", list)) //you declare the list stored in the model
                .andExpect(view().name("list_data")) // you declare the name of the view
                .andExpect(model().attribute("salesmanList", hasSize(2)));

        verify(salesmanRepository, times(1)).findAll();
        verifyNoMoreInteractions(salesmanRepository);
    }


    @Test
    void addTest() throws Exception {
        when(salesmanRepository.save(salesman)).thenReturn(salesman);
        salesmanRepository.save(salesman);
        verify(salesmanRepository, times(1)).save(salesman);
    }

    @Test
    void deleteTest() throws Exception {
        ArgumentCaptor<Long> idCapture = ArgumentCaptor.forClass(Long.class);
        doNothing().when(salesmanRepository).deleteById(idCapture.capture());
        salesmanRepository.deleteById(1L);
        assertEquals(1L, idCapture.getValue());

        verify(salesmanRepository, times(1)).deleteById(1L);
    }

}