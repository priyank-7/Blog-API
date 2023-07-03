package com.blog;

import com.blog.Repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApiApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void repoTest(){
        System.out.println(this.userRepository.getClass().getName());
        System.out.println(this.userRepository.getClass().getPackageName());
    }

}
