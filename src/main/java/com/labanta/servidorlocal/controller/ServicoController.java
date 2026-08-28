package com.labanta.servidorlocal.controller;

import com.labanta.servidorlocal.service.FileStorageService;
import com.labanta.servidorlocal.service.ServicoService;
import com.labanta.servidorlocal.dto.ServicoResponseDTO;
import com.labanta.servidorlocal.model.ServicoModel;
import com.labanta.servidorlocal.service.EmailService;
import com.labanta.servidorlocal.service.ExchangeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/servicos")
public class ServicoController {
    private final ServicoService service;
    private final ExchangeService exchangeService;
    private final EmailService emailService;
    private final FileStorageService fileStorageService;

    public ServicoController(ServicoService service, ExchangeService exchangeService, EmailService emailService, FileStorageService fileStorageService) {
        this.exchangeService = exchangeService;
        this.service = service;
        this.emailService = emailService;
        this.fileStorageService = fileStorageService;
    }

    @Operation(
            summary = "Listar todos os servicos",
            description = "Rota para listar todos os servicos existentes na plataforma"
    )
    @GetMapping
    public Page<ServicoModel> listarTodos(
            @PageableDefault(
                    page = 0,
                    size = 5,
                    sort = "id",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
            ) {
        return service.ListarTodos(pageable);
    }

    @GetMapping("/{id}")
    public ServicoModel buscarPorId(@PathVariable Long id) {
        return service.buscarServicoPorId(id);
    }

    @Operation(
            summary = "Criar um novo servico",
            description = "Rota para criar um novo servico"
    )
    @SecurityRequirement(name = "BearerAuth")
    @PostMapping("/create")
    public ServicoModel createServico(@RequestBody ServicoModel novoServico) {
        return service.CreateServico(novoServico);
    }

    @Operation(
            summary = "Aplicar um novo servico de desconto",
            description = "Rota aplicar um novo servico de desconto"
    )
    @SecurityRequirement(name = "BearerAuth")
    @PostMapping("/aplicar-desconto")
    public List<ServicoResponseDTO> aplicarDesconto(@RequestBody Double percentagem) {

        List<ServicoModel> servicos = service.aplicarDescontoEmAtivos(percentagem);

        List<ServicoResponseDTO> resposta = new ArrayList<>();

        for (ServicoModel servico : servicos) {
            resposta.add(
                    new ServicoResponseDTO(
                            servico.getTitulo(),
                            servico.getPrecoComDesconto()
                    )
            );
        }


        return resposta;
    }

    @Operation(
            summary = "Criar um novo servico de id para orcamento",
            description = "Rota para criar um id para orcamento"
    )
    @SecurityRequirement(name = "BearerAuth")
    @PostMapping("/{id}/orcamento")
    public String orcamento(
            @PathVariable Long id,
            @RequestParam String emailDestino,
            @RequestParam(defaultValue = "CVE") String moeda
    ) {

        // 1. Ir á Base de Dados buscar o Servico
        ServicoModel servico = service.buscarServicoPorId(id);

        // 2. Ir á internet converter o preço (aula 16)
        Double precoConvertido = exchangeService.converterPreco(servico.getPreco(), moeda);

        // 3. enviar o resultado para o email do cliente (aula 15)
        emailService.enviarOrcamentoPorEmail(emailDestino, servico.getTitulo(), precoConvertido, moeda);

        return "Orcamento calculado e enviado com sucesso para" + emailDestino + "!";
    }

    @GetMapping("/pesquisa")
    public List<ServicoModel> pesquisarServicos(@RequestParam String termo) {

        return service.pesquisarPorTitulo(termo);
    }

    @Operation(summary = "carregar capa do servico",
            description = "rota para caregar capas de servico com base no ID"
    )

    @SecurityRequirement(name = "BearerAuth")
    @PostMapping(value = "/{id}/upload-capa", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(
            @RequestParam MultipartFile file,
            @PathVariable Long id
    ){
        ServicoModel servico = service.buscarServicoPorId(id);

        String fileUploaded = fileStorageService.storeImage(file);

        servico.setImagemCapa(fileUploaded);
        service.CreateServico(servico);

        return ResponseEntity.ok("Ficheiro carregado com sucesso! " + fileUploaded);
    }


}