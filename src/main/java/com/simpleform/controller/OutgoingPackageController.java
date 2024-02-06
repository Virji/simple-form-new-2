package com.simpleform.controller;


import com.simpleform.entity.Client;
import com.simpleform.entity.IncomingPackage;
import com.simpleform.entity.OutgoingPackage;
import com.simpleform.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Outgoing_package")
public class OutgoingPackageController {

    @Autowired
    PackageService packageService;

    @PostMapping("/addOutgoingPackage")
    public String addOutgoingPackageEmployee(OutgoingPackage outgoingPackage_) {
        System.out.println("Received Package data: " + outgoingPackage_);
        packageService.addOutgoingPackageEmployee(outgoingPackage_);
        return "redirect:/registerOutgoingPackage_page.html";
    }

    @PostMapping("/search_Outgoing")
    public String searchOutgoingPackageEmployee(@RequestParam("searchTerm") String searchTerm, Model model) {
        List<OutgoingPackage> packages;
        if (searchTerm.equals("ALL") || searchTerm.equals(("all"))) {
            packages = packageService.findAllOutgoingPackages();
        } else {
            try {
                int packageId = Integer.parseInt(searchTerm);
                Optional<OutgoingPackage> result = packageService.findOutgoingPackageById(packageId);
                packages = result.isPresent() ? Collections.singletonList(result.get()) : new ArrayList<>();
            } catch (NumberFormatException e) {
                // searchTerm is not 'ALL' or a number, handle the case appropriately
                packages = Collections.emptyList();
            }
        }

        model.addAttribute("results", packages);
        return "searchOutgoingPackage_page.html"; // make sure the view name is correct
    }


    @PostMapping("/outgoing")
    public String addOutgoingPackageEmployee(
            @RequestParam("sender") String sender,
            @RequestParam("recipient") String recipient,
            @RequestParam("deliveryAddress") String deliveryAddress,
            @RequestParam("weight") String weight,
            @RequestParam(value = "isOfficeDelivery", defaultValue = "false") boolean isOfficeDelivery, // Set a default value
            @RequestParam("price") String price,
            Model model) {

        try {
            // Parse the weight and price
            double numericWeight = Double.parseDouble(weight);
            double numericPrice = Double.parseDouble(price);

            // Create a new IncomingPackage object
            OutgoingPackage outgoingPackage_ = new OutgoingPackage();
            outgoingPackage_.setSender(sender);
            outgoingPackage_.setRecipient(recipient);
            outgoingPackage_.setDeliveryAddress(deliveryAddress);
            outgoingPackage_.setWeight(weight);
            outgoingPackage_.setOfficeDelivery(isOfficeDelivery);
            outgoingPackage_.setPrice(numericPrice);

            // Save the IncomingPackage object
            packageService.addOutgoingPackageEmployee(outgoingPackage_);

            // Redirect to a success page or display a success message
            model.addAttribute("message", "Package registered successfully!");
            return "registerOutgoingPackage_page.html";
        } catch (NumberFormatException e) {
            // Handle parse errors for weight and price
            model.addAttribute("error", "Invalid weight or price format.");
            return "error_page.html";
        } catch (Exception e) {
            // Handle other errors
            System.err.println("Error saving package: " + e.getMessage());
            model.addAttribute("error", "Error saving package.");
            return "error_page.html";
        }
    }

    @GetMapping("/shipmentsSentByCustomer")
    public String getShipmentsSentByCustomer(@RequestParam("customerId") Integer customerId, Model model) {
        List<OutgoingPackage> shipments = packageService.getShipmentsSentByCustomer(customerId);
        model.addAttribute("shipments", shipments);
        return "shipments_sent_by_customer_page"; // Create a corresponding HTML page to display the results
    }








































}
