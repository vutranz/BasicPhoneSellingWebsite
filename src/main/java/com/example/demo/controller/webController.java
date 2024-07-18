package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;

@Controller
public class webController {
	@Autowired
	private UserRepo userepo;
	
	
	@RequestMapping("/")
	public String viewLogin(Model model)
	{
		User user = new User();
		model.addAttribute("user",user);
		return "login";
	}
	
	@PostMapping("/userLogin")
	public String loginUser(@ModelAttribute("user") User user,Model model)
	{
		String u = user.getUsername();
		String p= user.getPassword();
		List<User> userdata= userepo.findAll();
		if(u.equals("admin") && p.equals("123"))
		{
			model.addAttribute("username", "admin");
			return "admin";	
		}
		else
		{
			for(int i =0;i<userdata.size();++i)
			{
				if(userdata.get(i).getUsername().equals(u) && userdata.get(i).getPassword().equals(p))
				{
					model.addAttribute("username", userdata.get(i).getUsername());
					return "user";
				}
			
			}
			model.addAttribute("errorLoginMessage", "Đăng nhập thất bại");
			return "login";
			
		}
		
	}
	
	
	@RequestMapping("/register")
	public String viewRegister(Model model)
	{
		User user = new User();
		model.addAttribute("user",user);
		return "register";
	}
	
	@RequestMapping(value = "/userRegister", method = RequestMethod.POST)
	public String saveAccount(@ModelAttribute("user") User tk, Model model) {
		String u = tk.getUsername();
		String p= tk.getPassword();
		List<User> userdata= userepo.findAll();
		
		try {
			if(u=="" || p=="")
			{
				model.addAttribute("errorRegisterMessage", "Tài khoản mật khẩu không được để trống");
				return "register";
			}
			for(int i =0;i<userdata.size();++i)
			{
				if(userdata.get(i).getUsername().equals(u))
				{
					model.addAttribute("errorRegisterMessage", "Tài khoản đã tồn tại");
					return "register";
				}
				
			}
			userepo.save(tk);
			model.addAttribute("errorRegisterMessage", "Đăng ký thành công");
			return "login";
		}catch(Exception e) {
			model.addAttribute("errorRegisterMessage", "Đăng kí thất bại");
			return "register";
		}
			
		
	}
	
	
	

}
