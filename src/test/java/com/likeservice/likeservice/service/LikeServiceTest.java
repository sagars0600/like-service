package com.likeservice.likeservice.service;

import com.likeservice.likeservice.enums.BloodGroup;
import com.likeservice.likeservice.enums.Gender;
import com.likeservice.likeservice.exception.LikeNotFoundException;
import com.likeservice.likeservice.feign.FeignUser;
import com.likeservice.likeservice.model.Like;
import com.likeservice.likeservice.model.LikeDto;
import com.likeservice.likeservice.model.User;
import com.likeservice.likeservice.repo.LikeRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class LikeServiceTest {


    @InjectMocks
   LikeService service;

    @Mock
    LikeRepo likeRepo;

    @Mock
    FeignUser feignUser;

    @Test
    void likesPage() throws ParseException {
        Like likeModel = createLike();
        LikeDto likeDto = createLikeDTO();

        List<Like> list = new ArrayList<>();
        list.add(likeModel);

        PageImpl<Like> pageImpl = new PageImpl<Like>(list);
        Pageable firstPage = PageRequest.of(0, 2);
        when(this.likeRepo.findBypostorcommentID("12", firstPage)).thenReturn(list);
        assertEquals(1, this.service.likesPage("12", null, 2).size());
    }

    @Test
    void findByCommentId() throws ParseException {

        Like likeModel = createLike();
                LikeDto likeDto= createLikeDTO();
                when(this.likeRepo.findById("1")).thenReturn(Optional.of(likeModel));
        assertThat(this.service.likeDetailsOnID("1").getPostorcommentID()).isEqualTo(likeModel.getPostorcommentID());
        assertThrows(LikeNotFoundException.class,()->this.service.likeDetailsOnID("2"));


    }

    private User createUser() throws ParseException {
        User user = new User();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        user.setUserId("123");
        user.setFirstName("firstTest");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("9090909090");
        user.setEmail("natsu@mail.com");
        user.setDateOfBirth(c);
        user.setEmployeeNumber("12345");
        user.setBloodGroup(BloodGroup.A_POS);
        user.setGender(Gender.MALE);
        return user;

    }

    @Test
    void saveLike() throws ParseException {
        LikeDto likeDTO = createLikeDTO();
        Like like =createLike();

        Mockito.when(this.likeRepo.save(any(Like.class))).thenReturn(like);

        assertThat(this.service.likeCreate(like,"1").getPostorcommentID()).isEqualTo(likeDTO.getPostorcommentID());

    }
    private LikeDto createLikeDTO() throws ParseException {
        return new LikeDto("1","1",createUser(),null);
    }

    private Like createLike() throws ParseException {
        return new Like("1","1","hello",null);
    }


    @Test
    void deletebyCommentId() throws ParseException {

            Like like = createLike();
        LikeDto likeDto=createLikeDTO();

        doNothing().when(this.likeRepo).deleteById("1");
        when(this.likeRepo.findById("1")).thenReturn(Optional.of(like));

        assertThat(this.service.deleteLike("1"));

    }

    @Test
    void countLikes() {
        when(this.likeRepo.findAll()).thenThrow(new LikeNotFoundException("An error occurred"));
        assertThrows(LikeNotFoundException.class, () -> this.service.countLikes("12"));
        verify(this.likeRepo).findAll();
    }


}