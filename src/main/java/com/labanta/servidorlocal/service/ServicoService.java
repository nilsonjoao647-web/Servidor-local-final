package com.labanta.servidorlocal.service;

import com.labanta.servidorlocal.exception.ServicoNaoEncontradoException;
import com.labanta.servidorlocal.model.ServicoModel;
import com.labanta.servidorlocal.repository.ServicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {
    private final ServicoRepository repositorio;

    public ServicoService(ServicoRepository repositorio) {
        this.repositorio = repositorio;
    }

    public Page<ServicoModel> ListarTodos(Pageable pageable) {
        return repositorio.findAll(pageable);
    }

    public ServicoModel CreateServico(ServicoModel novoServico) {
        return repositorio.save(novoServico);
    }

    public List<ServicoModel> aplicarDescontoEmAtivos(double percentagem) {
        List<ServicoModel> lista = repositorio.findByEstaAtivoTrue();
        if (percentagem < 0 || percentagem > 100) {
            throw new IllegalArgumentException("Desconto inválido.");
        }
        for (ServicoModel servico : lista) {
            if (servico.getPreco() >= 100 && percentagem == 10) {
                double precoComDEsconto = servico.getPreco() - (servico.getPreco() * percentagem / 100);
                servico.setPreco(precoComDEsconto);
            }
        }

        return repositorio.findByEstaAtivoTrue();
    }


    public ServicoModel buscarServicoPorId(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new ServicoNaoEncontradoException(
                        "O serviço com o ID " + id + " não existe no catálogo."
                ));
    }

    public List<ServicoModel> pesquisarPorTitulo(String termo) {
        return repositorio.findByTituloContainingIgnoreCase(termo);
    }


}
