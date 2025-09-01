package org.companiesOnMarket.service;

import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.companiesOnMarket.dto.CompanyCreateDto;
import org.companiesOnMarket.dto.CompanyGetDto;
import org.companiesOnMarket.dto.CompanyUpdateDto;
import org.companiesOnMarket.entity.Company;
import org.companiesOnMarket.error.NotFoundException;
import org.companiesOnMarket.error.PersistenceException;
import org.companiesOnMarket.mapper.CompanyMapper;
import org.companiesOnMarket.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class CompanyServiceTest {

    CompanyService companyService;

    @Mock
    CompanyRepository companyRepo;

    @Mock
    CompanyMapper mapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        companyService = new CompanyService(companyRepo, mapper);
    }

    @Test
    @TestTransaction
    void getAllCompaniesReturnsMappedDtos() {
        Company company = new Company("TestCo", "US", "TST", null, null, Instant.now());
        CompanyGetDto dto = new CompanyGetDto();
        dto.setName("TestCo");

        when(companyRepo.getAll()).thenReturn(List.of(company));
        when(mapper.toGetCompanyDtoList(List.of(company))).thenReturn(List.of(dto));

        List<CompanyGetDto> result = companyService.getAllCompanies();

        assertEquals(1, result.size());
        assertEquals("TestCo", result.getFirst().getName());
    }

    @Test
    @TestTransaction
    void getCompanyByIdDelegatesToRepo() {
        Company company = new Company("TestCo", "US", "TST", null, null, Instant.now());
        when(companyRepo.findById(1L)).thenReturn(company);

        Company found = companyService.getCompanyById(1L);
        assertEquals("TestCo", found.getName());
    }

    @Test
    @TestTransaction
    void createCompanyPersistsMappedEntity() {
        CompanyCreateDto dto = new CompanyCreateDto();
        dto.setName("NewCo");

        Company mapped = new Company("NewCo", "US", "NEW", null, null, Instant.now());
        when(mapper.fromCreateDto(dto)).thenReturn(mapped);

        companyService.createCompany(dto);

        verify(companyRepo).create(mapped);
    }

    @Test
    @TestTransaction
    void createCompanyThrowsPersistenceExceptionOnFailure() {
        CompanyCreateDto dto = new CompanyCreateDto();
        Company mapped = new Company("BadCo", "US", "BAD", null, null, Instant.now());
        when(mapper.fromCreateDto(dto)).thenReturn(mapped);
        doThrow(new RuntimeException("db fail")).when(companyRepo).create(any());

        assertThrows(PersistenceException.class, () -> companyService.createCompany(dto));
    }

    @Test
    @TestTransaction
    void updateCompanyUpdatesAndReturnsDto() {
        Company existing = new Company("OldCo", "US", "OLD", null, null, Instant.now());
        when(companyRepo.findById(1L)).thenReturn(existing);

        CompanyUpdateDto updateDto = new CompanyUpdateDto();

        doNothing().when(mapper).updateEntityFromDto(updateDto, existing);
        doNothing().when(companyRepo).synchronize();
        doAnswer(invocation -> {
            CompanyGetDto dtoArg = invocation.getArgument(1);
            dtoArg.setName("Updated");
            return null;
        }).when(mapper).createGetCompanyResultDto(eq(existing), any(CompanyGetDto.class));

        CompanyGetDto result = companyService.updateCompany(1L, updateDto);

        assertEquals("Updated", result.getName());
    }

    @Test
    @TestTransaction
    void updateCompanyThrowsNotFoundIfMissing() {
        when(companyRepo.findById(999L)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> companyService.updateCompany(999L, new CompanyUpdateDto()));
    }
}