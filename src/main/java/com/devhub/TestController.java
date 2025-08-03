package com.devhub;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class TestController {

	@GetMapping("/")
	public String test() {
		return "::: Controller Tested ::: ";
	}
}
