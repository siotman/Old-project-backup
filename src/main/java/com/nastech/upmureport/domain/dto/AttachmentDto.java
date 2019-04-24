package com.nastech.upmureport.domain.dto;

import java.math.BigInteger;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class AttachmentDto {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AttachmentReqDto {
		
		private String pdir;
		
	}
	
	
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AttachmentResDto {
		
		private BigInteger attachmentId;
		private String attachmentName;
		private Long volume;
		private LocalDate newDate;				
	}
	
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AttachmentDownDto {
		
		private String attachmentName;
		private byte[] file;
						
	}
}