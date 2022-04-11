package com.likeservice.likeservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likeservice.likeservice.enums.BloodGroup;
import com.likeservice.likeservice.enums.Gender;
import com.likeservice.likeservice.model.Like;
import com.likeservice.likeservice.model.LikeDto;
import com.likeservice.likeservice.model.User;
import com.likeservice.likeservice.service.LikeService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(LikeController.class)
class LikeControllerTest {

    @MockBean
    LikeService likeService;

    @Autowired
    MockMvc mockMvc;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private User createOneUser() throws  ParseException {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        User user1 = new User();

        user1.setUserId("123");
        user1.setFirstName("FirstID");
        user1.setMiddleName("J");
        user1.setLastName("S");
        user1.setPhoneNumber("9090909090");
        user1.setEmail("prabhu@mail.com");
        user1.setDateOfBirth(c);
        user1.setEmployeeNumber("12345");
        user1.setBloodGroup(BloodGroup.A_NEG);
        user1.setGender(Gender.MALE);
        user1.setAddress("Pune");
        return user1;
    }

    private Like createDummy(){
        return new Like("1","12","123",null);
    }

    private LikeDto createDummyLikeDTO() throws ParseException {
        return new LikeDto("1","12",createOneUser(),null);

    }

    @Test
    void createLike() throws Exception {
        Like like =createDummy();
        LikeDto likeDTO =createDummyLikeDTO();
        Mockito.when(likeService.likeCreate(like,"1")).thenReturn(likeDTO);

        mockMvc.perform(post("/postsOrComments/1/likes")
                        .content(asJsonString(like))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.likeID", Matchers.is("1")));

    }

    @Test
    void deleteLike() throws Exception {

        Mockito.when(likeService.deleteLike("1")).thenReturn("deleted successful ");

        mockMvc.perform(delete("/postsOrComments/1/likes/1"))
                .andDo(print())
                .andExpect(status().isAccepted());

    }

@Test
void testCount() throws Exception {
      Integer s= count();

    Mockito.when(likeService.countLikes("1")).thenReturn(s);

    mockMvc.perform(delete("/postsOrComments/1/likes/count"))
            .andDo(print())
            .andExpect(status().isAccepted());

}
    private  int count(){
        List<Like> list= new ArrayList<>();
        Like like1= new Like();
        Like like2= new Like();
        Like like3= new Like();
        list.add(like1);
        list.add(like2);
        list.add(like3);
        return list.size();

    }


    @Test
    void findById() throws Exception {
        Like like =createDummy();
        LikeDto likeDTO =createDummyLikeDTO();
        Mockito.when(likeService.likeDetailsOnID("1")).thenReturn(likeDTO);

        mockMvc.perform(post("/postsOrComments/1/likes")
                        .content(asJsonString(like))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());


    }

    private List<Like> getListLike(){
        List<Like> likes= new ArrayList<>();
        Like like1= new Like("1","1","hello", null);
        Like like12= new Like("2","1","hello",null);
        Like like3= new Like("3","1","hello", null);
        likes.add(like1);
        likes.add(like12);
        likes.add(like3);
        return likes;

    }
    private List<LikeDto> getLikeDto() throws ParseException {
        List<LikeDto> like= new ArrayList<>();
        LikeDto likeDto1= new LikeDto("1","1",createOneUser(),null);
        LikeDto likeDto2= new LikeDto("2","1",createOneUser(),null);
        LikeDto likeDto3= new LikeDto("3","1",createOneUser(),null);
        like.add(likeDto1);
        like.add(likeDto2);
        like.add(likeDto3);
        return  like;
    }

    @Test
    void getPages() throws Exception {
        List<Like> likeList=getListLike();
        List<LikeDto> dtoList=getLikeDto();
        Mockito.when(likeService.likesPage("1",1,1)).thenReturn(dtoList);
        mockMvc.perform(get("/postsOrComments/1/likes?page=1&pageSize=1"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$[0].likeID", Matchers.is("1")));

    }


}