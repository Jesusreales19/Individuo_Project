package com.project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.project.service.IIndividuoService;
import com.project.domain.Individuo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private IIndividuoService individuoService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(value = "palabraClave", required = false) String palabraClave,
                        Authentication authentication) {

        var lista = (palabraClave != null && !palabraClave.isEmpty())
                ? individuoService.buscarIndividuos(palabraClave)
                : individuoService.listarIndividuos();

        model.addAttribute("lista", lista);
        model.addAttribute("palabraClave", palabraClave);

        boolean esAdmin = false;
        String usuarioActual = "No autenticado";
        String rolesActuales = "Sin roles";

        if (authentication != null) {
            usuarioActual = authentication.getName();
            rolesActuales = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .filter(a -> a.startsWith("ROLE_"))
                    .collect(Collectors.joining(", "));

            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    esAdmin = true;
                    break;
                }
            }
        }

        model.addAttribute("esAdmin", esAdmin);
        model.addAttribute("usuarioActual", usuarioActual);
        model.addAttribute("rolesActuales", rolesActuales);

        return "index";
    }

    @GetMapping("/detalle")
    public String detalle(@RequestParam("id") Integer id, Model model) {
        Individuo individuo = individuoService.buscarIndividuoPorId(id);
        model.addAttribute("individuo", individuo);
        return "detalle";
    }

    @GetMapping("/guardarDemo")
    public String guardarDemo() {
        Individuo nuevo = new Individuo();
        nuevo.setNombre("Pepito");
        nuevo.setApellido("Suarez");
        nuevo.setEdad(19);
        nuevo.setCorreo("suar_pep19@gmail.com");
        nuevo.setTelefono("3154525468");
        individuoService.guardarIndividuo(nuevo);
        return "redirect:/";
    }

    @GetMapping("/eliminar")
    public String eliminar(@RequestParam("id") Integer id) {
        individuoService.eliminarIndividuo(id);
        return "redirect:/";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("individuo", new Individuo());
        return "/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Individuo individuo, BindingResult resultado) {
        if (resultado.hasErrors()) {
            return "form";
        }
        individuoService.guardarIndividuo(individuo);
        return "redirect:/";
    }

    @GetMapping("/editar")
    public String editar(@RequestParam("id") Integer id, Model model) {
        Individuo individuo = individuoService.buscarIndividuoPorId(id);
        model.addAttribute("individuo", individuo);
        return "/form";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
