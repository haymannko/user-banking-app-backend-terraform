package com.personalbanking.gateway.dto.nickname;

// new service creation for the project
public record CreateNicknameRequestDto(
		Long fromAccount,Long toAccount,String nickname,Long createdBy
) {}
