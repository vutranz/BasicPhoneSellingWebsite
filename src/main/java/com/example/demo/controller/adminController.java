package com.example.demo.controller;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.ProductRepo;
import com.example.demo.repository.UserRepo;

@Controller
public class adminController {
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private UserRepo userepo;
	
	@RequestMapping("/admin/quanlysanpham")
	public String viewProduct(Model model) {
		Product product = new Product();
		model.addAttribute("product",product);
		List<Product> listProduct = productRepo.findAll();
		model.addAttribute("products", listProduct);
		return "quanlysanpham";
	}
	
	@RequestMapping("/quanlysanpham")
	public String viewQuanLySanPham(Model model)
	{
		Product product = new Product();
		model.addAttribute("product",product);
		return "redirect:/admin/quanlysanpham";	
	}
	
	
	@RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") Product p, Model model)
	{
//		String name= p.getName();
//		String image=p.getImage();
//		String price = p.getPrice();
//		String descrip= p.getDescrip();
		productRepo.save(p);
		return "redirect:/admin/quanlysanpham";
	}
	
	@RequestMapping("/admin/quanlytaikhoan")
	public String listAccout(Model model) {
		List<User> listAccout = userepo.findAll();
		model.addAttribute("users", listAccout);
		return "quanlytaikhoan";
	}
	
	@RequestMapping("/admin/deleteProduct/{id}")
	public String deleteProduct(@PathVariable(name = "id") int id) {
		try {
			productRepo.deleteById(id);
		}catch(Exception e){
			return "redirect:/admin/quanlysanpham";	
		}
		return "redirect:/admin/quanlysanpham";
	}
	
	@RequestMapping("/admin/deleteAccount/{id}")
	public String deleteAccount(@PathVariable(name = "id") int id) {
		try {
			userepo.deleteById(id);
		}catch(Exception e){
			return "redirect:/admin/quanlytaikhoan";	
		}
		return "redirect:/admin/quanlytaikhoan";
	}
	
	@RequestMapping("/admin/editProduct/{id}")
	public String viewFormEditProduct(@PathVariable(name="id") int id,Model model)
	{
		List<Product> p = productRepo.findAll();
		
		for(int i =0;i<p.size();i++)
		{
			if(p.get(i).getId()==id)
			{
				Product product = new Product();
				product.setId(p.get(i).getId());
				product.setName(p.get(i).getName());
				product.setImage(p.get(i).getImage());
				product.setPrice(p.get(i).getPrice());
				product.setDescrip(p.get(i).getDescrip());
				model.addAttribute("product",product);
			}
		}
		
		return "editProduct";	
	}
	
	@RequestMapping(value = "/saveProductEdit/{id}", method = RequestMethod.POST)
	public String saveProductEdit(@ModelAttribute("product") Product p, Model model)
	{
		productRepo.save(p);
		return "redirect:/admin/quanlysanpham";
	}

	@RequestMapping("/admin/editAccount/{id}")
	public String viewFormEditAccount(@PathVariable(name="id") int id,Model model)
	{
		List<User> u = userepo.findAll();
		
		for(int i =0;i<u.size();i++)
		{
			if(u.get(i).getId()==id)
			{
				User user = new User();
				user.setId(u.get(i).getId());
				user.setUsername(u.get(i).getUsername());
				user.setPassword(u.get(i).getPassword());
				model.addAttribute("user",user);
			}
		}
		
		return "editAccount";	
	}
	
	@RequestMapping(value = "/editAccount/{id}", method = RequestMethod.POST)
	public String saveAccout(@ModelAttribute("user") User u, Model model)
	{
		userepo.save(u);
		return "redirect:/admin/quanlytaikhoan";
	}
	
	
	
}
