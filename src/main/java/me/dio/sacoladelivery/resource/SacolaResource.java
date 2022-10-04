package me.dio.sacoladelivery.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import me.dio.sacoladelivery.model.Item;
import me.dio.sacoladelivery.model.Sacola;
import me.dio.sacoladelivery.resource.dto.ItemDto;
import me.dio.sacoladelivery.service.SacolaService;

@Api(value = "/sacolas")
@RestController
@RequestMapping("/sacolas")
@RequiredArgsConstructor
public class SacolaResource {
    
    private final SacolaService sacolaService;

    @PostMapping
    public Item incluirItemSacola(@RequestBody ItemDto itemDto) {
        return sacolaService.incluirItemSacola(itemDto);
    }

    @GetMapping("/{id}")
    public Sacola verSacola(@PathVariable("id") Long id) {
        return sacolaService.verSacola(id);
    }

    @PutMapping("/fecharsacola/{idSacola}")
    public Sacola fecharSacola(@PathVariable("idSacola") Long idSacola,
                                @RequestParam("formaPagamento") int formaPagamento) {
        return sacolaService.fecharSacola(idSacola, formaPagamento);
    }
}
