package com.simpleform.controller;

import com.simpleform.entity.IncomingPackage;
import com.simpleform.repository.IncomingPackageRepository;
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
@RequestMapping("/package")
public class IncomingPackageController {

    @Autowired
    PackageService packageService;


    @PostMapping("/add")
    public String addIncomingPackageAdmin(IncomingPackage incomingPackage_) {
        System.out.println("Received company data: " + incomingPackage_);
        packageService.addIncomingPackageAdmin(incomingPackage_);
        return "addPackage_page.html";
    }

    @PostMapping("/register_incoming")
    public String addIncomingPackageEmployee(
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
            IncomingPackage incomingPackage_ = new IncomingPackage();
            incomingPackage_.setSender(sender);
            incomingPackage_.setRecipient(recipient);
            incomingPackage_.setDeliveryAddress(deliveryAddress);
            incomingPackage_.setWeight(weight);
            incomingPackage_.setOfficeDelivery(isOfficeDelivery);
            incomingPackage_.setPrice(numericPrice);

            // Save the IncomingPackage object
            packageService.addIncomingPackageEmployee(incomingPackage_);

            // Redirect to a success page or display a success message
            model.addAttribute("message", "Package registered successfully!");
            return "registerIncomingPackage_page.html";
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


    @PostMapping("/search_Incoming")
    public String searchIncomingPackageEmployee(@RequestParam("searchTerm") String searchTerm, Model model){
        List<IncomingPackage> packages;
        if(searchTerm.equals("ALL")) {
            packages = packageService.findAllIncomingPackages();
        } else {
            try {
                int packageId = Integer.parseInt(searchTerm);
                Optional<IncomingPackage> result = packageService.findIncomingPackageById(packageId);
                packages = result.isPresent() ? Collections.singletonList(result.get()) : new ArrayList<>();
            } catch (NumberFormatException e) {
                // searchTerm is not 'ALL' or a number, handle the case appropriately
                packages = Collections.emptyList();
            }
        }

        model.addAttribute("results", packages);
        return "searchIncomingPackage_page.html"; // make sure the view name is correct
    }





    @PostMapping("/update")
    public String updateCompany(@RequestParam("id") Integer id,
                                @RequestParam(value = "sender", required = false) String sender,
                                @RequestParam(value = "recipient", required = false) String recipient,
                                @RequestParam(value = "deliveryAddress", required = false) String deliveryAddress,
                                @RequestParam(value = "weight", required = false) String weight
    ) {
        try {
            IncomingPackage existingIncomingPackage = packageService.getPackageById(id);
            if (existingIncomingPackage != null) {
                boolean isUpdated = false;
                if (sender != null && !sender.trim().isEmpty()) {
                    existingIncomingPackage.setSender(sender);
                    isUpdated = true;
                }
                if (recipient != null && !recipient.trim().isEmpty()) {
                    existingIncomingPackage.setRecipient(recipient);
                    isUpdated = true;
                }
                // Only update the contact number if it's provided and not empty
                if (deliveryAddress != null && !deliveryAddress.trim().isEmpty()) {
                    existingIncomingPackage.setDeliveryAddress(deliveryAddress);
                    isUpdated = true;
                }
                if (weight != null && !weight.trim().isEmpty()) {
                    existingIncomingPackage.setWeight(weight);
                    isUpdated = true;
                }
                // Save the changes only if at least one field was updated
                if (isUpdated) {
                    packageService.updatePackage(existingIncomingPackage);
                }
            }
            return "updatePackage_page.html";
        } catch (Exception e) {
            System.err.println("Error updating office: " + e.getMessage());
            return "error_page.html"; // Error page
        }
    }


    @PostMapping("/delete")
    public String deletePackage(@RequestParam Integer id) {
        IncomingPackage incomingPackage_ = new IncomingPackage();
        incomingPackage_.setId(id);
        packageService.deletePackage(incomingPackage_);
        return "deletePackage_page.html";
    }

    @GetMapping("/report")
    public String showPackageReport(Model model) {
        List<IncomingPackage> packages = packageService.findAllIncomingPackages();
        model.addAttribute("packages", packages);
        return "package_report";
    }
}
