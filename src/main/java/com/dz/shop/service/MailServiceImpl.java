package com.dz.shop.service;

import com.dz.shop.entity.MailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Random;

@Service
public class MailServiceImpl {
    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendMail(MailVO mailVO) throws UnsupportedEncodingException {
        String to = mailVO.getEmail();
        String subject = "[DZ] 아이디 찾기 메일";
        String body ;

        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/mailTemplate.html"), "utf-8"));

        StringBuilder str = new StringBuilder();
        reader.lines().forEach(str::append);
        String htmlStr = str.toString();

        String tempNumber = tempNumber();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        htmlStr = htmlStr.replace("${USERNAME}", "홍길동");
        htmlStr = htmlStr.replace("${DATETIME}", sdf.format(Calendar.getInstance().getTime()));
        htmlStr = htmlStr.replace("${TEMPNUMBER}", tempNumber);



        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper mh =
                    new MimeMessageHelper(message, true, "UTF-8");
            mh.setFrom("kingle1024@gmail.com", "마사모");
            mh.setTo(to);
            mh.setSubject(subject);
            mh.setText(body);
            mailSender.send(message);
        }catch (Exception e){

        }
    }

    public String tempNumber(){
        Random random = new Random();		//랜덤 함수 선언
        int createNum = 0;  			//1자리 난수
        String ranNum = ""; 			//1자리 난수 형변환 변수
        int letter    = 6;			//난수 자릿수:6
        String resultNum = "";  		//결과 난수

        for (int i=0; i<letter; i++) {
            createNum = random.nextInt(9);		//0부터 9까지 올 수 있는 1자리 난수 생성
            ranNum =  Integer.toString(createNum);  //1자리 난수를 String으로 형변환
            resultNum += ranNum;			//생성된 난수(문자열)을 원하는 수(letter)만큼 더하며 나열
        }
        return resultNum;
    }
}
