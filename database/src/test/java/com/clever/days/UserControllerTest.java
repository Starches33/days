//package com.clever.days;
//
//import org.apache.commons.lang3.RandomStringUtils;
//import org.ctnb.back.app.domain.BaseServiceTest;
//import org.ctnb.back.app.domain.entity.CarEO;
//import org.ctnb.back.app.domain.mapper.NotFoundException;
//import org.ctnb.back.app.domain.repository.CarRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class DomCarServiceTest extends BaseServiceTest {
//
//    @Autowired
//    private DomCarService domCarService;
//    @Autowired
//    private CarRepository carRepository;
//
//    @Test
//    public void getCarByIdWithException() {
//        assertThrows(NotFoundException.class, () -> domCarService.getCarById(UUID.randomUUID()));
//    }
//
//    @Test
//    public void getCarById() {
//        var expected = CarEO.builder()
//                .name(RandomStringUtils.randomAlphabetic(40))
//                .build();
//        carRepository.save(expected);
//
//        var car = domCarService.getCarById(expected.getId());
//
//        assertEquals(expected.getId(), car.getId());
//        assertEquals(expected.getName(), car.getName());
//    }
//
//    @Test
//    public void createCar() {
//        var car = domCarService.createCar(RandomStringUtils.randomAlphabetic(40));
//
//        assertNotNull(car);
//        assertNotNull(car.getId());
//
//        var actual = carRepository.findById(car.getId()).orElse(null);
//
//        assertNotNull(actual);
//        assertEquals(car.getName(), actual.getName());
//    }
//}
//
//
//
////import com.clever.days.DatabaseApplication;
////import com.clever.days.controller.UserController;
////import com.clever.days.exception.NotFoundException;
////import com.clever.days.model.User;
////import com.clever.days.service.UserService;
////import org.junit.jupiter.api.BeforeEach;
////import org.junit.jupiter.api.Test;
////import org.junit.jupiter.api.extension.ExtendWith;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
////import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
////import org.springframework.boot.test.context.SpringBootTest;
////import org.springframework.boot.test.mock.mockito.MockBean;
////import org.springframework.http.MediaType;
////import org.springframework.test.context.junit.jupiter.SpringExtension;
////import org.springframework.test.web.servlet.MockMvc;
////import org.springframework.test.web.servlet.ResultMatcher;
////
////import java.util.List;
////import java.util.UUID;
////
////import static net.bytebuddy.matcher.ElementMatchers.is;
////import static org.hamcrest.Matchers.hasSize;
////import static org.mockito.BDDMockito.given;
////import static org.mockito.BDDMockito.willReturn;
////import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
////
////import static org.mockito.ArgumentMatchers.any;
////import static org.mockito.ArgumentMatchers.anyLong;
////
////import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
////import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
////import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
////import static org.hamcrest.Matchers.is;
////import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
////
////
////
////@ExtendWith(SpringExtension.class)
////@SpringBootTest(classes = UserController.class) // Укажите ваш основной класс приложения или конфигурацию
////@AutoConfigureMockMvc
////
////public class UserControllerTest {
//////
//////    @Autowired
//////    private MockMvc mockMvc;
//////
//////    @MockBean
//////    private UserService userService;
////////
////////    private User user;
//////
//////    private static final String dbApiUrl = "http://localhost:8080";
//////
//////    //users
//////    private static final String dbUsersApiUrl = dbApiUrl + "/users";
//////    private static final String saveUserApiUrl = dbUsersApiUrl + "/create";
//////
////////    @BeforeEach
////////    public void setup() {
////////        user = new User(UUID.randomUUID(), anyLong(), "Test User");
////////    }
//////
//////    @Test
//////    public void createUserTest() throws Exception {
//////        long tgId = 12345L;
//////        String name = "Test";
////////        var user = new User(UUID.randomUUID(), tgId, "Test User");
////////
////////
////////        given(userService.createUser(tgId, name)).willReturn(user);
//////
//////        mockMvc.perform(post(saveUserApiUrl)
//////                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//////                .accept(MediaType.APPLICATION_JSON)
//////                .param("tgId", String.valueOf(tgId))
//////                .param("name", name))
//////                .andExpect(status().isOk());
////////                .andExpect(jsonPath("$.name", org.hamcrest.Matchers.is(user.getName())));
//////    }
//////
////////    @Test
////////    public void getAllUsersTest() throws Exception {
////////        given(userService.getAllUsers()).willReturn(List.of(user));
////////        mockMvc.perform(get("/users")
////////                .contentType(MediaType.APPLICATION_JSON))
////////                .andExpect(status().isOk())
////////                .andExpect(jsonPath("$", hasSize(1)))
////////                .andExpect((ResultMatcher) jsonPath("$[0].name", is(user.getName())));
////////    }
////////
////////    @Test
////////    public void getUserByIdTest() throws Exception {
////////        given(userService.getUserById(user.getId())).willReturn(user);
////////        mockMvc.perform(get("/users/{id}", user.getId())
////////                .contentType(MediaType.APPLICATION_JSON))
////////                .andExpect(status().isOk())
////////                .andExpect((ResultMatcher) jsonPath("$.name", is(user.getName())));
////////    }
//////
//////
//////
//////
////////    @Test
////////    public void userNotFoundTest() throws Exception {
////////        given(userService.getUserById(UUID.randomUUID())).willThrow(new NotFoundException("User not found"));
////////        mockMvc.perform(get("/users/{id}", anyLong())
////////                .contentType(MediaType.APPLICATION_JSON))
////////                .andExpect(status().isNotFound());
////////    }
//////}
