package com.nttdata.actividadfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nttdata.actividadfinal.entity.Usuario;
import com.nttdata.actividadfinal.service.AsignaturaService;
import com.nttdata.actividadfinal.service.UsuarioService;

@Controller
public class AsignaturaController {

	@Autowired
	AsignaturaService asignatura;
	
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping("/")
	public String index (Model model) {
		Usuario u = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("usuario", u);
		return "index";
	}
	
	@GetMapping("/error")
	public String error_page(){
		return "error";
	}
	
	@GetMapping("/listarUsuario")
	public String listarUsu(@RequestParam(name="rol", required=false, defaultValue="CONSULTA") String rol, Model model) {
		//Usuario usu = usuarioService.ListarUsuariosPorRol(rol);
		model.addAttribute("listaUsu", usuarioService.ListarUsuariosPorRol(rol.toUpperCase()));
		return "listaUsuarios";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/listarAsignaturas")
	public String listarAsig(Model model){
		model.addAttribute("listaAsig", asignatura.listar());
		return "asignaturas";
	}
	
	
	
}
