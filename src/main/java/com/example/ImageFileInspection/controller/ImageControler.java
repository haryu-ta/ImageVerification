package com.example.ImageFileInspection.controller;

import com.example.ImageFileInspection.dto.ImageFileDto;
import com.example.ImageFileInspection.form.ImageForm;
import com.example.ImageFileInspection.form.OptionForm;
import com.example.ImageFileInspection.service.ImageFileService;
import com.example.ImageFileInspection.service.OptionService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

@Controller
@RequestMapping("/image")
public class ImageControler {
	
	@Autowired
	ImageFileService service;

	@Autowired
	OptionService option;

	/**
	 * アップロードするページを表示
	 * @param model
	 * @return アップロード画面
	 */
	@GetMapping
	public String disp(Model model) {

		System.out.println("=======DispPage======");

		TreeMap<String,String> image_list = option.getOption();

		model.addAttribute(new ImageForm());
		model.addAttribute(new OptionForm());
		model.addAttribute("selectItems", image_list);
		return "image/index";
		
	}

	/**
	 * アップロード処理
	 * @param imageForm
	 * @return アップロード画像確認画面
	 * @throws IOException
	 */
	@PostMapping(path="/upload")
	public String upload(@ModelAttribute ImageForm imageForm, RedirectAttributes redirect) throws IOException {

		ImageFileDto dto = new ImageFileDto();
		String base64 = new String(Base64.encodeBase64(imageForm.getImage().getBytes()),"ASCII");

		dto.setFilename(imageForm.getImage().getOriginalFilename());
		dto.setImage(base64);

		int id = service.insertDb(dto);

		// リダイレクト先にはFlashAttribute
		redirect.addFlashAttribute("id",id);
		System.out.println(id);
		return "redirect:showregist";
	}

	/**
	 * 拡大縮小検証ページを表示
	 * @return
	 */
	@GetMapping(path="/show")
	public String dispImage() {
		return "image/dispImage";
	}

	/**
	 * 登録した画像を取得
	 * @param model
	 * @return
	 */
	@GetMapping(path="/showregist")
	public String dispImage2(@ModelAttribute("id") int id, Model model){

		// Modelから取得することも可能
		//int id = (int) model.getAttribute("id");

		ImageFileDto dto = service.readDb(id);
		model.addAttribute("base64image","data:image/jpeg;base64," + dto.getImage());
		return "image/registImage";
	}

	/**
	 * 登録なしで画像表示
	 * @param model
	 * @return
	 */
	@PostMapping(path="/disp")
	public String dispNonUpload(@ModelAttribute OptionForm optionForm,Model model, RedirectAttributes redirect){
		redirect.addFlashAttribute("id",optionForm.getFileid());
		return "redirect:showregist";
	}

	/**
	 * 画像回転など確認画面に遷移
	 * @return
	 */
	@GetMapping(path="/disp")
	public String dispRotateDisp(Model model){
		List<ImageFileDto> data = service.read();
		for(ImageFileDto d : data){
			d.setImage("data:image/jpeg;base64," + d.getImage());
		}
		model.addAttribute("ImageFileDto",data);
		return "image/test";
	}

}
