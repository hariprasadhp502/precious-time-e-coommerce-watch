package com.precious.watchapp.controller;

import com.precious.watchapp.model.Product;
import com.precious.watchapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class ProductController {

    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    @Autowired
    private ProductService productService;

    // ---------------- FETCH ALL PRODUCTS ----------------
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // ---------------- ADD PRODUCT ----------------
    @PostMapping("/addProduct")
    public ResponseEntity<String> addProduct(
            @RequestParam String name,
            @RequestParam String brand,
            @RequestParam String model,
            @RequestParam String category,
            @RequestParam String color,
            @RequestParam String description,
            @RequestParam double price,
            @RequestParam int stock,
            @RequestParam("image") MultipartFile file
    ) {
        try {
            String fileName = saveFile(file);

            Product p = new Product();
            p.setName(name);
            p.setBrand(brand);
            p.setModel(model);
            p.setCategory(category);
            p.setColor(color);
            p.setDescription(description);
            p.setPrice(price);
            p.setStock(stock);
            p.setImagePath("/uploads/" + fileName);

            productService.addProduct(p);
            return ResponseEntity.ok("success");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("file_upload_error");
        }
    }

    // ---------------- UPDATE PRODUCT ----------------
    @PostMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String brand,
            @RequestParam String model,
            @RequestParam String category,
            @RequestParam String color,
            @RequestParam String description,
            @RequestParam double price,
            @RequestParam int stock,
            @RequestParam(value = "image", required = false) MultipartFile file
    ) {
        Product p = productService.getProductById(id);
        if (p == null) return ResponseEntity.status(404).body("not_found");

        try {
            if (file != null && !file.isEmpty()) {
                String fileName = saveFile(file);
                p.setImagePath("/uploads/" + fileName);
            }

            p.setName(name);
            p.setBrand(brand);
            p.setModel(model);
            p.setCategory(category);
            p.setColor(color);
            p.setDescription(description);
            p.setPrice(price);
            p.setStock(stock);

            productService.updateProduct(p);
            return ResponseEntity.ok("updated");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("file_upload_error");
        }
    }

    // ---------------- DELETE PRODUCT ----------------
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        Product p = productService.getProductById(id);
        if (p == null) return ResponseEntity.status(404).body("not_found");

        // Delete image file
        if (p.getImagePath() != null) {
            File f = new File(System.getProperty("user.dir") + p.getImagePath());
            if (f.exists()) f.delete();
        }

        productService.deleteProduct(id);
        return ResponseEntity.ok("deleted");
    }

    // ---------------- HELPER ----------------
    private String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) throw new IOException("Empty file");

        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) dir.mkdirs();

        String fileName = System.currentTimeMillis() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
        File destination = new File(dir, fileName);
        file.transferTo(destination);

        return fileName;
    }
}
