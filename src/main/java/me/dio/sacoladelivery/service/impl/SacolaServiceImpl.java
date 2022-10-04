package me.dio.sacoladelivery.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.dio.sacoladelivery.enumeration.FormaPagamento;
import me.dio.sacoladelivery.model.Item;
import me.dio.sacoladelivery.model.Restaurante;
import me.dio.sacoladelivery.model.Sacola;
// import me.dio.sacoladelivery.repository.ItemRepository;
import me.dio.sacoladelivery.repository.ProdutoRepository;
import me.dio.sacoladelivery.repository.SacolaRepository;
import me.dio.sacoladelivery.resource.dto.ItemDto;
import me.dio.sacoladelivery.service.SacolaService;

@Service
@RequiredArgsConstructor
public class SacolaServiceImpl implements SacolaService {

    private final SacolaRepository sacolaRepository;
    private final ProdutoRepository produtoRepository;
//    private final ItemRepository itemRepository;
    
    @Override
    public Item incluirItemSacola(ItemDto itemDto) {
        Sacola sacola = verSacola(itemDto.getIdSacola());

        if (sacola.isFechada()) {
            throw new RuntimeException("Esta sacola está fechada.");
        }

        Item itemParaInserir = Item.builder()
            .quantidade(itemDto.getQuantidade())
            .sacola(sacola)
            .produto(produtoRepository.findById(itemDto.getIdProduto()).orElseThrow(() -> {throw new RuntimeException ("Essa produto não existe.");}))
            .build();
        
        List<Item> itensDaSacola = sacola.getItens();
        if (itensDaSacola.isEmpty()) {
            itensDaSacola.add(itemParaInserir);
        } else {
            Restaurante restauranteAtual = itensDaSacola.get(0).getProduto().getRestaurante();
            Restaurante restauranteItem = itemParaInserir.getProduto().getRestaurante();
            if (restauranteAtual.equals(restauranteItem)) {
                itensDaSacola.add(itemParaInserir);
            } else {
                throw new RuntimeException("Não é possível adicionar produtos de restaurantes diferentes.");
            }
        }

        List<Double> valoresItens = new ArrayList<>();
        
        for(Item itemSacola: itensDaSacola) {
            double valorTotalItem = itemSacola.getProduto().getValorUnitario() * itemSacola.getQuantidade();
            valoresItens.add(valorTotalItem);
        }

        double valorTotalSacola = valoresItens.stream().mapToDouble(valorTotalItem -> valorTotalItem).sum();
    sacola.setValorTotal(valorTotalSacola);
    sacolaRepository.save(sacola);  
    return itemParaInserir; // itemRepository.save(itemParaInserir);
    }

    @Override
    public Sacola verSacola(Long id) {

        return sacolaRepository.findById(id).orElseThrow(() -> {throw new RuntimeException ("Essa sacola não existe.");});
    }

    @Override
    public Sacola fecharSacola(Long id, int numeroFormaPagamento) {
        Sacola sacola = verSacola(id);
        if (sacola.getItens().isEmpty()) {
            throw new RuntimeException ("Inclua itens na sacola.");
        }
        FormaPagamento formaPagamento = numeroFormaPagamento == 0 ? FormaPagamento.DINHEIRO : FormaPagamento.MAQUINA;
        sacola.setFormaPagamento(formaPagamento);
        sacola.setFechada(true);
        return sacolaRepository.save(sacola);
        
    }
    
}
