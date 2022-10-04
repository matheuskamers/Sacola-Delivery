package me.dio.sacoladelivery.service;

import me.dio.sacoladelivery.model.Item;
import me.dio.sacoladelivery.model.Sacola;
import me.dio.sacoladelivery.resource.dto.ItemDto;

public interface SacolaService {
    
    Item incluirItemSacola(ItemDto itemDto);
    
    Sacola verSacola(Long id);

    Sacola fecharSacola(Long id, int formaPagamento);
}
