package com.simpleform.controller;
import com.simpleform.dto.clientDTO;
import com.simpleform.entity.Client;
import com.simpleform.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/client")
public class ClientController {


    @Autowired
    private ClientService clientService;


    @PostMapping("/add")
    public String addClient(@ModelAttribute clientDTO clientDto) {
        clientService.addClientDTO(clientDto);
        return "addClient_page.html";
    }




    @PostMapping("/delete")
    public String deleteClient(@RequestParam Long id) {
            Client client = new Client();
        client.setId(id);
        clientService.deleteClient(client);
        return "deleteClient_page.html";
    }

    @PostMapping("/update")
    public String updateClient(@ModelAttribute clientDTO clientDto) {
        clientService.updateClientDTO(clientDto);
        return "updateClient_page.html";
    }


    @PostMapping("/search")
    public String searchClient(@RequestParam("searchTerm") String searchTerm, Model model){
        List<Client> clients;
        if(searchTerm.equals("ALL")) {
            clients = clientService.findAllClients();
        } else {
            try {
                int clientID = Integer.parseInt(searchTerm);
                Optional<Client> result = clientService.findClientById(clientID);
                clients = result.isPresent() ? Collections.singletonList(result.get()) : new ArrayList<>();
            } catch (NumberFormatException e) {
                // searchTerm is not 'ALL' or a number, handle the case appropriately
                clients = Collections.emptyList();
            }
        }

        model.addAttribute("results", clients);
        return "searchClient_page.html"; // make sure the view name is correct
    }

    @GetMapping("/report")
    public String showClientReport(Model model) {
        List<Client> clients = clientService.getAllClients();
        model.addAttribute("clients", clients);
        return "client_report"; // Assuming Thymeleaf will resolve to "client_report.html"
    }

}
