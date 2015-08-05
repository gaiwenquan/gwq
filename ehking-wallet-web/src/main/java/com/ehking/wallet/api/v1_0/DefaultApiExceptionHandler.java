package com.ehking.wallet.api.v1_0;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.ehking.commons.web.api.BaseApiExceptionHandler;

@ControllerAdvice(annotations = {
		org.springframework.web.bind.annotation.RestController.class,
		org.springframework.stereotype.Controller.class })
public class DefaultApiExceptionHandler extends BaseApiExceptionHandler {

	final static Logger LOGGER = LoggerFactory
			.getLogger(DefaultApiExceptionHandler.class);

}
