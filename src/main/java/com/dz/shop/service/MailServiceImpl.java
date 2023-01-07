package com.dz.shop.service;

import com.dz.shop.Dao.MailDAO;
import com.dz.shop.Dao.MemberDAO;
import com.dz.shop.entity.MailVO;
import com.dz.shop.entity.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MemberDAO memberDAO;

    @Autowired
    private MailDAO mailDAO;

    public long welcomSendMail(MemberVO memberVO) {
        String body = readMailTemplate("/mail/mailTemplate.html");

        String tempNumber = getTempNumber();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        body = body.replace("${USERNAME}", memberVO.getName());
        body = body.replace("${DATETIME}", sdf.format(Calendar.getInstance().getTime()));
        body = body.replace("${TEMPNUMBER}", tempNumber);

        sendSearchIdMail(memberVO.getEmail(), "[DZ] 회원가입 환영 메일", body);

        return 1;
    }

    public long sendSearchIdMail(Map<String, Object> map) {
        String email = (String) map.get("email");
        String name = (String) map.get("name");
        System.out.println("email = " + email);
        System.out.println("name = " + name);
        MemberVO member = memberDAO.findByEmailAndName(map);
        System.out.println("member = " + member);
        if(member == null){
            System.out.println("정보를 확인해주세요.");
            return -1;
        }

        String to = (String) map.get("email");
        String body = readMailTemplate("/mail/idTemplate.html");

        body = body.replace("${USERID}", member.getUserId());

        sendSearchIdMail(to, "[DZ] 아이디 찾기 메일", body);

        return 1;
    }

    public long sendSearchPwdMail(Map<String, Object> map) {
        System.out.println("MailServiceImpl.sendSearchPwdMail");
        System.out.println("map = " + map);
        MemberVO member = memberDAO.findByIdAndEmail(map);
        System.out.println("member = " + member);
        if(member == null){
            System.out.println("정보를 확인해주세요.");
            return -1;
        }

        String to = (String) map.get("email");
        String body = readMailTemplate("/mail/pwdTemplate.html");
        String tempNumber = getTempNumber();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        body = body.replace("${USERNAME}", "홍길동");
        body = body.replace("${DATETIME}", sdf.format(Calendar.getInstance().getTime()));
        body = body.replace("${TEMPNUMBER}", tempNumber);
        sendSearchIdMail(to, "[DZ] 비밀번호 찾기 메일", body);
        MailVO mailVO = MailVO.builder()
                .value(tempNumber)
                .email((String) map.get("email"))
                .create_date(LocalDateTime.now())
                .content(body)
                .user_id((String) map.get("userId"))
                .build();
        mailDAO.insert(mailVO);
        return 1;
    }

    public String readMailTemplate(String templatePath) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(
                        getClass().getResourceAsStream(templatePath))
                , StandardCharsets.UTF_8));

        StringBuilder str = new StringBuilder();
        reader.lines().forEach(str::append);
        return str.toString();
    }

    void sendSearchIdMail(String to, String subject, String body) {
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
            e.printStackTrace();
        }
    }

    public String getTempNumber(){
        Random random = new Random();		//랜덤 함수 선언
        int createNum;  			//1자리 난수
        String ranNum; 			//1자리 난수 형변환 변수
        int letter    = 6;			//난수 자릿수:6
        StringBuilder resultNum = new StringBuilder();  		//결과 난수

        for (int i=0; i<letter; i++) {
            createNum = random.nextInt(9);		//0부터 9까지 올 수 있는 1자리 난수 생성
            ranNum =  Integer.toString(createNum);  //1자리 난수를 String으로 형변환
            resultNum.append(ranNum);			//생성된 난수(문자열)을 원하는 수(letter)만큼 더하며 나열
        }
        return resultNum.toString();
    }

    public MailVO checkValue(Map<String, Object> map) {
        return mailDAO.findByValueAndEmail(map);
    }

    public String createRetValue(Map<String, Object> map) {
        String tempNumber = getTempNumber();
        map.put("retValue", tempNumber);
        System.out.println("createRetValue map = " + map);
        if(mailDAO.edit(map) > 0)
            return tempNumber;
        else
            return null;
    }

    public long validRetValue(Map<String, Object> map) {
        MailVO mail = mailDAO.findByEmail((String) map.get("email"));
        String retValue = (String) map.get("retValue");

        if(mail.getRetValue().equals(retValue)){
            return 1;
        }else {
            return -1;
        }
    }
}
