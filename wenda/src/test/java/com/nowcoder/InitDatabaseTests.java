package com.nowcoder;

import com.nowcoder.dao.QuestionDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.Question;
import com.nowcoder.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

/**
 * Created by dell on 2017/6/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendaApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTests {

    @Autowired
    UserDAO  userDAO;
    @Autowired
    QuestionDAO questionDAO;

    @Test
    public void contextLoads(){
        Random random = new Random();
        for(int i=0 ; i< 11 ; ++i){
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png" , random.nextInt(1000)));
            user.setName(String.format("user %d" , i));
            user.setSalt("");
            user.setPassword("");
            userDAO.addUser(user);
            user.setPassword("1234");
            userDAO.updatePassword(user);

            Question question = new Question();
            question.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() + 1000*3600*5*i);
            question.setCreatedDate(date);
            question.setTitle(String.format("title {%d}" , i));
            question.setContent(String.format("南京有很多所大学，比如中国药科大学，南京大学。。。 %d" , i));
            question.setUserId(i+1);
            questionDAO.addQuestion(question);
        }
        Assert.assertEquals("1234" , userDAO.selectById(3).getPassword());

    }
}
