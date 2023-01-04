package com.dz.shop.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {
	private String userId;
	private String pwd;
	private String name;
	private String sex;
	private String address;
	private String phone;
	private String email;
	private String userStatus;
	private boolean delete_yn;
	private boolean isAdmin;
	private LocalDateTime createdate;
	private LocalDateTime logindatetime;
}
