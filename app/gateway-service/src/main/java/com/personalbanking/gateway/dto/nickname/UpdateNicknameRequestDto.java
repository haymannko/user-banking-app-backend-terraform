package com.personalbanking.gateway.dto.nickname;

public record UpdateNicknameRequestDto(
		Long toAccount,String nickname,Long updatedBy
) {}
