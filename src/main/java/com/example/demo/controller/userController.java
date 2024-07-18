package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.repository.CartRepo;
import com.example.demo.repository.ProductRepo;



@Controller
public class userController {
		
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private CartRepo cartrepo;
	
	@RequestMapping("/danhsachsanpham")
	public String viewProduct(Model model) {
		Product product = new Product();
		model.addAttribute("product",product);
		List<Product> listProduct = productRepo.findAll();
		model.addAttribute("products", listProduct);
		return "danhsachsanpham";
	}
	
	@RequestMapping(value="/user/buyProduct/{id}")
	public String buyProduct(@PathVariable(name="id") int id,Model model)
	{
		List<Product> p = productRepo.findAll();
		
		for(int i =0;i<p.size();i++)
		{
			if(p.get(i).getId()==id)
			{
				String name = p.get(i).getName();
				String img = p.get(i).getImage();
				String price = p.get(i).getPrice();
				Cart cart = new Cart(name,img,price);
				cartrepo.save(cart);
				
			}
		}
		return "redirect:/danhsachsanpham";	
	}
	
	@RequestMapping("/lichsumuahang")
	public String viewHistoryBuyProductt(Model model) {
		Cart cart = new Cart();
		model.addAttribute("cart",cart);
		List<Cart> listCart = cartrepo.findAll();
		model.addAttribute("cart", listCart);
		model.addAttribute("Message", "Mua hàng thành công");
		
		return "lichsumuahang";
	}
}
