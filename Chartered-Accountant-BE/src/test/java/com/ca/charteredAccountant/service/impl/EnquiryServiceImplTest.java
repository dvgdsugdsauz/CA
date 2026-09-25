package com.ca.charteredAccountant.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ca.charteredAccountant.common.enums.AdminRole;
import com.ca.charteredAccountant.common.enums.EnquiryStatus;
import com.ca.charteredAccountant.dao.model.AdminUserEntity;
import com.ca.charteredAccountant.dao.model.EnquiryEntity;
import com.ca.charteredAccountant.dao.model.FirmServiceEntity;
import com.ca.charteredAccountant.dao.model.LoggedInUserDetails;
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.repository.AdminUserRepository;
import com.ca.charteredAccountant.repository.EnquiryRepository;
import com.ca.charteredAccountant.repository.FirmServiceRepository;
import com.ca.charteredAccountant.repository.OfficeLocationRepository;
import com.ca.charteredAccountant.request.EnquiryRequest;
import com.ca.charteredAccountant.request.EnquiryUpdateRequest;
import com.ca.charteredAccountant.response.EnquiryStatusSummaryResponse;
import com.ca.charteredAccountant.response.EnquirySubmitResponse;

@ExtendWith(MockitoExtension.class)
class EnquiryServiceImplTest {

	@Mock
	private EnquiryRepository enquiryRepository;
	@Mock
	private FirmServiceRepository firmServiceRepository;
	@Mock
	private OfficeLocationRepository officeLocationRepository;
	@Mock
	private AdminUserRepository adminUserRepository;

	@InjectMocks
	private EnquiryServiceImpl enquiryService;

	private final LoggedInUserDetails admin = LoggedInUserDetails.builder()
			.userId(1L).username("admin").role(AdminRole.ADMIN).build();

	@Test
	void submitEnquiry_savesNewLeadAndReturnsReferenceNumber() throws CAException {
		when(firmServiceRepository.findById(4L))
				.thenReturn(Optional.of(FirmServiceEntity.builder().id(4L).title("GST Registration & Returns").active(true).build()));
		when(enquiryRepository.save(any(EnquiryEntity.class))).thenAnswer(inv -> {
			EnquiryEntity e = inv.getArgument(0);
			if (e.getId() == null) {
				e.setId(42L);
			}
			return e;
		});

		EnquiryRequest request = new EnquiryRequest();
		request.setFullName("  Priya Sharma ");
		request.setEmail(" Priya@Example.com ");
		request.setPhone("+91 98765 43210");
		request.setCompanyName("   ");
		request.setServiceId(4L);
		request.setSourcePage("/");

		EnquirySubmitResponse response = enquiryService.submitEnquiry(request);

		assertThat(response.getReferenceNo()).isEqualTo("ENQ-" + LocalDate.now().getYear() + "-000042");

		ArgumentCaptor<EnquiryEntity> captor = ArgumentCaptor.forClass(EnquiryEntity.class);
		verify(enquiryRepository, org.mockito.Mockito.times(2)).save(captor.capture());
		EnquiryEntity saved = captor.getValue();
		assertThat(saved.getFullName()).isEqualTo("Priya Sharma");
		assertThat(saved.getEmail()).isEqualTo("priya@example.com");
		assertThat(saved.getCompanyName()).isNull();
		assertThat(saved.getStatus()).isEqualTo(EnquiryStatus.NEW);
		assertThat(saved.getService().getId()).isEqualTo(4L);
	}

	@Test
	void submitEnquiry_ignoresInactiveService() throws CAException {
		when(firmServiceRepository.findById(9L))
				.thenReturn(Optional.of(FirmServiceEntity.builder().id(9L).active(false).build()));
		when(enquiryRepository.save(any(EnquiryEntity.class))).thenAnswer(inv -> {
			EnquiryEntity e = inv.getArgument(0);
			e.setId(7L);
			return e;
		});

		EnquiryRequest request = new EnquiryRequest();
		request.setFullName("A");
		request.setEmail("a@b.co");
		request.setPhone("9876543210");
		request.setServiceId(9L);

		enquiryService.submitEnquiry(request);

		ArgumentCaptor<EnquiryEntity> captor = ArgumentCaptor.forClass(EnquiryEntity.class);
		verify(enquiryRepository, org.mockito.Mockito.times(2)).save(captor.capture());
		assertThat(captor.getValue().getService()).isNull();
	}

	@Test
	void getStatusSummary_includesZeroCountsAndTotal() {
		when(enquiryRepository.countGroupByStatus()).thenReturn(List.of(
				new Object[] { EnquiryStatus.NEW, 3L },
				new Object[] { EnquiryStatus.CONVERTED, 2L }));

		EnquiryStatusSummaryResponse summary = enquiryService.getStatusSummary();

		assertThat(summary.getTotal()).isEqualTo(5L);
		assertThat(summary.getStatuses()).hasSize(EnquiryStatus.values().length);
		assertThat(summary.getStatuses()).filteredOn(s -> s.getStatus() == EnquiryStatus.CLOSED)
				.singleElement().extracting(EnquiryStatusSummaryResponse.StatusCount::getCount).isEqualTo(0L);
	}

	@Test
	void updateEnquiry_returnsNullWhenMissing() throws CAException {
		when(enquiryRepository.findById(99L)).thenReturn(Optional.empty());

		EnquiryUpdateRequest request = new EnquiryUpdateRequest();
		request.setEnquiryId(99L);
		request.setStatus(EnquiryStatus.CONTACTED);

		assertThat(enquiryService.updateEnquiry(request, admin)).isNull();
	}

	@Test
	void updateEnquiry_rejectsDisabledAssignee() {
		when(enquiryRepository.findById(1L)).thenReturn(Optional.of(EnquiryEntity.builder().id(1L).status(EnquiryStatus.NEW).build()));
		when(adminUserRepository.findById(5L)).thenReturn(Optional.of(AdminUserEntity.builder().id(5L).enabled(false).build()));

		EnquiryUpdateRequest request = new EnquiryUpdateRequest();
		request.setEnquiryId(1L);
		request.setStatus(EnquiryStatus.CONTACTED);
		request.setAssignedToId(5L);

		assertThatThrownBy(() -> enquiryService.updateEnquiry(request, admin)).isInstanceOf(CAException.class);
	}

}
