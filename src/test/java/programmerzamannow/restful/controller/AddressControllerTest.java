package programmerzamannow.restful.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import programmerzamannow.restful.entity.Address;
import programmerzamannow.restful.entity.Contact;
import programmerzamannow.restful.entity.User;
import programmerzamannow.restful.model.AddressResponse;
import programmerzamannow.restful.model.CreateAddressRequest;
import programmerzamannow.restful.model.UpdateAddressRequest;
import programmerzamannow.restful.model.WebResponse;
import programmerzamannow.restful.repository.AddressRepository;
import programmerzamannow.restful.repository.ContactRepository;
import programmerzamannow.restful.repository.UserRepository;
import programmerzamannow.restful.security.BCrypt;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AddressControllerTest {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		addressRepository.deleteAll();
		contactRepository.deleteAll();
		userRepository.deleteAll();

		User user = new User();
		user.setUsername("username");
		user.setPassword(BCrypt.hashpw("password", BCrypt.gensalt()));
		user.setName("name");
		user.setToken("token");
		user.setTokenExpiredAt(System.currentTimeMillis() + 100000000);
		userRepository.save(user);

		Contact contact = new Contact();
		contact.setId("testId");
		contact.setUser(user);
		contact.setFirstName("Andre");
		contact.setLastName("Hutabarat");
		contact.setEmail("andre@gmail.com");
		contact.setPhone("1234567890");
		contactRepository.save(contact);
	}

	@Test
	void createAdressBadRequest() throws Exception {
		CreateAddressRequest request = new CreateAddressRequest();
		request.setCountry("");

		mockMvc.perform(
				post("/api/contacts/test/addresses")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request))
						.header("X-API-TOKEN", "token")
		).andExpectAll(
				status().isBadRequest()
		).andDo(result -> {
			WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
			});

			assertNotNull(response.getErrors());
		});
	}

	@Test
	void createAddressSuccess() throws Exception {
		CreateAddressRequest request = new CreateAddressRequest();
		request.setStreet("testStreet");
		request.setCity("testCity");
		request.setProvince("testProvince");
		request.setPostalCode("123456");
		request.setCountry("Indonesia");

		mockMvc.perform(
				post("/api/contacts/testId/addresses")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request))
						.header("X-API-TOKEN", "token")
		).andExpectAll(
				status().isOk()
		).andDo(result -> {
			WebResponse<AddressResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
			});

			assertNull(response.getErrors());
			assertEquals(request.getStreet(), response.getData().getStreet());
			assertEquals(request.getCity(), response.getData().getCity());
			assertEquals(request.getProvince(), response.getData().getProvince());
			assertEquals(request.getCountry(), response.getData().getCountry());
			assertEquals(request.getPostalCode(), response.getData().getPostalCode());

			assertTrue(addressRepository.existsById(response.getData().getId()));
		});
	}

	@Test
	void getAddressNotFound() throws Exception {
		mockMvc.perform(
				get("/api/contacts/test/addresses/test")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.header("X-API-TOKEN", "token")
		).andExpectAll(
				status().isNotFound()
		).andDo(result -> {
			WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
			});

			assertNotNull(response.getErrors());
		});
	}

	@Test
	void getAddressSuccess() throws Exception {
		Contact contact = contactRepository.findById("testId").orElseThrow();

		Address address = new Address();
		address.setContact(contact);
		address.setId("testId");
		address.setStreet("testStreet");
		address.setCity("testCity");
		address.setProvince("testProvince");
		address.setCountry("testCountry");
		address.setPostalCode("123456");

		addressRepository.save(address);

		mockMvc.perform(
				get("/api/contacts/testId/addresses/testId")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.header("X-API-TOKEN", "token")
		).andExpectAll(
				status().isOk()
		).andDo(result -> {
			WebResponse<AddressResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
			});

			assertNull(response.getErrors());
			assertEquals(address.getId(), response.getData().getId());
			assertEquals(address.getStreet(), response.getData().getStreet());
			assertEquals(address.getCity(), response.getData().getCity());
			assertEquals(address.getProvince(), response.getData().getProvince());
			assertEquals(address.getCountry(), response.getData().getCountry());
			assertEquals(address.getPostalCode(), response.getData().getPostalCode());
		});
	}

	@Test
	void updateAdressBadRequest() throws Exception {
		UpdateAddressRequest request = new UpdateAddressRequest();
		request.setCountry("");

		mockMvc.perform(
				put("/api/contacts/testId/addresses/testId")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request))
						.header("X-API-TOKEN", "token")
		).andExpectAll(
				status().isBadRequest()
		).andDo(result -> {
			WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
			});

			assertNotNull(response.getErrors());
		});
	}

	@Test
	void updateAddressSuccess() throws Exception {
		Contact contact = contactRepository.findById("testId").orElseThrow();

		Address address = new Address();
		address.setContact(contact);
		address.setId("testId");
		address.setStreet("testStreetLama");
		address.setCity("testCityLama");
		address.setProvince("testProvinceLama");
		address.setCountry("testCountryLama");
		address.setPostalCode("123456");

		addressRepository.save(address);

		UpdateAddressRequest request = new UpdateAddressRequest();
		request.setStreet("testStreetBaru");
		request.setCity("testCityBaru");
		request.setProvince("testProvinceBaru");
		request.setPostalCode("123457");
		request.setCountry("IndonesiaBaru");

		mockMvc.perform(
				put("/api/contacts/testId/addresses/testId")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request))
						.header("X-API-TOKEN", "token")
		).andExpectAll(
				status().isOk()
		).andDo(result -> {
			WebResponse<AddressResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
			});

			assertNull(response.getErrors());
			assertEquals(request.getStreet(), response.getData().getStreet());
			assertEquals(request.getCity(), response.getData().getCity());
			assertEquals(request.getProvince(), response.getData().getProvince());
			assertEquals(request.getCountry(), response.getData().getCountry());
			assertEquals(request.getPostalCode(), response.getData().getPostalCode());

			assertTrue(addressRepository.existsById(response.getData().getId()));
		});
	}

	@Test
	void deleteAddressNotFound() throws Exception {
		mockMvc.perform(
				delete("/api/contacts/test/addresses/test")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.header("X-API-TOKEN", "token")
		).andExpectAll(
				status().isNotFound()
		).andDo(result -> {
			WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
			});

			assertNotNull(response.getErrors());
		});
	}

	@Test
	void deleteAddressSuccess() throws Exception {
		Contact contact = contactRepository.findById("testId").orElseThrow();

		Address address = new Address();
		address.setContact(contact);
		address.setId("testId");
		address.setStreet("testStreet");
		address.setCity("testCity");
		address.setProvince("testProvince");
		address.setCountry("testCountry");
		address.setPostalCode("123456");

		addressRepository.save(address);

		mockMvc.perform(
				delete("/api/contacts/testId/addresses/testId")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.header("X-API-TOKEN", "token")
		).andExpectAll(
				status().isOk()
		).andDo(result -> {
			WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
			});

			assertNull(response.getErrors());
			assertEquals("OK", response.getData());

			assertFalse(addressRepository.existsById("testId"));
		});
	}

	@Test
	void listAddressNotFound() throws Exception {
		mockMvc.perform(
				get("/api/contacts/wrongContact/addresses")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.header("X-API-TOKEN", "token")
		).andExpectAll(
				status().isNotFound()
		).andDo(result -> {
			WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
			});

			assertNotNull(response.getErrors());
		});
	}

	@Test
	void listAddressSuccess() throws Exception {
		Contact contact = contactRepository.findById("testId").orElseThrow();

		for (int i = 0; i < 5; i++) {
			Address address = new Address();
			address.setContact(contact);
			address.setId("testId-" + i);
			address.setStreet("testStreet");
			address.setCity("testCity");
			address.setProvince("testProvince");
			address.setCountry("testCountry");
			address.setPostalCode("123456");

			addressRepository.save(address);

		}

		mockMvc.perform(
				get("/api/contacts/testId/addresses")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.header("X-API-TOKEN", "token")
		).andExpectAll(
				status().isOk()
		).andDo(result -> {
			WebResponse<List<AddressResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
			});

			assertNull(response.getErrors());
			assertEquals(5, response.getData().size());
		});
	}
}