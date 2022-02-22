package com.irdaislakhuafa.kopmart.controllers.admin;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletResponse;

import com.irdaislakhuafa.kopmart.helpers.DataToCsvHelper;
import com.irdaislakhuafa.kopmart.helpers.UserHelper;
import com.irdaislakhuafa.kopmart.helpers.ViewHelper;
import com.irdaislakhuafa.kopmart.models.entities.Category;
import com.irdaislakhuafa.kopmart.models.entities.Product;
import com.irdaislakhuafa.kopmart.models.entities.dto.ProductDto;
import com.irdaislakhuafa.kopmart.services.CategoryService;
import com.irdaislakhuafa.kopmart.services.ProductService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/kopmart/admin/produk")
public class ProductController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    // GET product new
    @GetMapping("/new")
    public String newProduct(Model model) {
        try {
            model.addAttribute("title", ViewHelper.APP_TITLE_ADMIN);
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("product", new Product());
            model.addAttribute("newProductUrl", "/kopmart/admin/produk/new");
        } catch (Exception e) {
            // e.printStackTrace();
            UserHelper.errorLog("gagal memuat halaman untuk membuat new product", this);
        }
        return "admin/produk/new";
    }

    // POST product new
    @PostMapping("/new")
    public String newProduct(
            Model model,
            @RequestParam("name") String name,
            @RequestParam("harga") Double harga,
            @RequestParam("simpleDesc") String simpleDesc,
            @RequestParam("fullDesc") String fullDesc,
            @RequestParam("foto") MultipartFile foto,
            @RequestParam("categoryId") String categoryId,
            @RequestParam("stok") Integer stok,
            RedirectAttributes redirectAttributes) {

        Product product = new Product();
        model.addAttribute("title", ViewHelper.APP_TITLE_ADMIN);
        model.addAttribute("categories", categoryService.findAll());

        try {
            String fileName = new Date().toInstant()
                    + foto.getOriginalFilename();
            // get path of file
            Path path = Paths
                    .get(
                            System.getProperty("user.home")
                                    + "/.cache/"
                                    + fileName);

            // get bytes of file
            byte[] bytes = foto.getBytes();

            // write file to path
            Files.write(path, bytes);

            // set product properties
            product.setName(name);
            product.setHarga(harga);
            product.setSimpleDesc(simpleDesc);
            product.setFullDesc(fullDesc);
            product.setFotoUrl(fileName);
            product.setCategoryId(categoryService.findById(categoryId).get());
            product.setStok(stok);

            // save products
            productService.save(product);

        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage",
                    e.getMessage().contains("ConstraintViolationException") ? "Maaf nama foto tidak boleh sama!" : "");

            product.setName(name);
            product.setHarga(harga);
            product.setSimpleDesc(simpleDesc);
            product.setFullDesc(fullDesc);
            product.setFotoUrl(foto.getOriginalFilename());
            product.setCategoryId(categoryService.findById(categoryId).get());
            product.setStok(stok);

            model.addAttribute("product", product);
            return "admin/produk/new";
        } catch (Exception e) {
            // e.printStackTrace();
            UserHelper.errorLog("terjadi error saat membuat product baru admin", this);
        }
        return "redirect:/kopmart/admin/produk/list";
    }

    // GET list product
    @GetMapping({ "/list", "/" })
    public String listProducts(
            Model model,
            @RequestParam("requestPage") Optional<Integer> requestPage,
            @RequestParam("requestData") Optional<Integer> requestData,
            @RequestParam("requestSort") Optional<String> requestSort) {

        try {
            model.addAttribute("title", ViewHelper.APP_TITLE_ADMIN);

            // get pageable of products
            Page<Product> productPages = productService.findAll(
                    PageRequest.of(
                            // request page number
                            requestPage.orElse(0),
                            // request size of data
                            requestData.orElse(10),
                            // request sorting data
                            (requestSort.orElse("asc").equalsIgnoreCase("asc")
                                    || requestSort.orElse("asc").equalsIgnoreCase("ascending")
                                    || requestSort.orElse("asc").startsWith("asc")) ? // true/false
                                            Sort.by("name").ascending() : // if true
                                            Sort.by("name").descending())); // if false

            // list numbers of page
            List<Integer> productPageNumbers = IntStream.rangeClosed( // get integer range
                    // from 1
                    1,
                    // to total page numbers
                    productPages.getTotalPages())
                    // convert to Stream<Integer>
                    .boxed()
                    // convert to list
                    .collect(Collectors.toList());

            model.addAttribute("productPages", productPages);
            model.addAttribute("productPageNumbers", productPageNumbers);
        } catch (Exception e) {
            // e.printStackTrace();
            UserHelper.errorLog("terjadi kesakahan saat memuat list product admin", this);
        }
        return "admin/produk/list";
    }

    // GET for edit
    @GetMapping("/edit/{id}")
    public String editProduct(
            Model model,
            @PathVariable("id") Optional<String> productId) {

        try {
            model.addAttribute("title", ViewHelper.APP_TITLE_ADMIN);
            // if product is exists
            if (productId.isPresent()) {
                Product product = productService
                        .findById(
                                productId.get())
                        .get();

                model.addAttribute("product", product);
            }

            model.addAttribute("categories", categoryService.findAll());
        } catch (Exception e) {
            // e.printStackTrace();
            UserHelper.errorLog("terjadi error saat mngedit product admin", this);
        }
        return "admin/produk/edit";
    }

    @PostMapping("/edit")
    public String editProduct(
            Model model,
            Product product,
            @RequestParam("oldPic") Optional<Boolean> oldPic,
            RedirectAttributes redirectAttributes) {
        try {
            // if use old pic
            if (oldPic.isPresent()) {
                product.setFotoUrl(
                        productService.findById(
                                product.getId()).get()
                                .getFotoUrl());
            } else if (product.getFotoUrl() == null) {
                redirectAttributes.addFlashAttribute("fotoErrorMessage",
                        "Foto tidak boleh kosong, gunakan foto sebelumnya jika tidak ingin merubah!");

                return "redirect:/kopmart/admin/produk/edit/" + product.getId();
            }

            // update product
            productService.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            UserHelper.errorLog("terjadi kesalahan saat menyimpan product yg sudah di edit admin", this);
        }
        return "redirect:/kopmart/admin/produk/list";
    }

    @PostMapping("/delete")
    public String delete(
            Model model,
            @RequestParam("categoryId") String categoryId) {

        try {
            productService.removeById(categoryId);
        } catch (Exception e) {
            e.printStackTrace();
            UserHelper.errorLog("Terjadi kesalahan saat menghapus product admin", this);
        }
        return "redirect:/kopmart/admin/produk/list";
    }

    @GetMapping("/upload/csv")
    public String uploadCsv(Model model) {
        try {
            model.addAttribute("title", ViewHelper.APP_TITLE_ADMIN);
            model.addAttribute("uploadCsvUrl", "/kopmart/admin/produk/upload/csv");
            model.addAttribute("backActionUrl", "/kopmart/admin/produk/list");
            model.addAttribute("downloadCsvUrl", "/kopmart/admin/produk/download/sample/produk.csv");
        } catch (Exception e) {
            UserHelper.errorLog("terjadi kesalahan saat memuat halaman upload csv!");
        }
        return "admin/produk/upload/csv";
    }

    @PostMapping("/upload/csv")
    public String uploadCsv(
            Model model,
            @RequestParam(name = "fileCsv") MultipartFile fileCsv,
            @RequestParam(name = "backUrl", required = false) Optional<String> backUrl,
            RedirectAttributes redirectAttributes) {

        try (Reader fileCsvReader = new InputStreamReader(fileCsv.getInputStream())) {
            model.addAttribute("title", ViewHelper.APP_TITLE_ADMIN);
            if (fileCsv.isEmpty()) { // error
                // if file csv not selected
                redirectAttributes.addFlashAttribute("fileError", "silahkan masukan file CSV!");
            } else if (!fileCsv.getContentType().equalsIgnoreCase("text/csv")) { // warning
                // if content type not csv file
                redirectAttributes.addFlashAttribute("fileError",
                        "file yang masukan bukan file CSV!");
            } else { // success
                // convert csv file to bean
                CsvToBean<ProductDto> productBean = new CsvToBeanBuilder<ProductDto>(fileCsvReader)
                        .withType(ProductDto.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withThrowExceptions(true)
                        .build();

                // parse file bean to ArrayList
                List<ProductDto> listProductBeans = productBean.parse();

                // create list object products
                List<Product> products = new ArrayList<>();

                // insert list product bean to products
                listProductBeans.forEach((product) -> {
                    products.add(
                            new Product(
                                    // id
                                    null,
                                    // foto url
                                    null,
                                    // name
                                    product.getName(),
                                    // harga
                                    product.getHarga(),
                                    // simple desc
                                    product.getSimpleDesc(),
                                    // full desc
                                    product.getFullDesc(),
                                    // category
                                    (categoryService
                                            .findByNameIgnoreCase(
                                                    product.getCategory().trim())
                                            .isPresent()) ? categoryService // if category exists
                                                    .findByNameIgnoreCase(
                                                            product.getCategory().trim())
                                                    .get()
                                                    // if not exists,
                                                    // it will save new category with name categori from
                                                    // fileCsv
                                                    : categoryService.save(
                                                            new Category(null, product.getCategory().trim(), "-")),
                                    // stok
                                    product.getStok()));
                });
                // save alll products on list
                productService.saveAll(products);
                redirectAttributes.addFlashAttribute("fileSuccess",
                        "Berhasil menyimpan file \"" + fileCsv.getOriginalFilename() + "\" ke database :D");
            }
        } catch (DataIntegrityViolationException e) {
            // if file already exists
            redirectAttributes.addFlashAttribute("fileWarning", "data pada file \"" + fileCsv.getOriginalFilename()
                    + "\" mungkin sudah ada! silahkan periksa kembali file anda!");
        } catch (Exception e) {
            // if getting error
            redirectAttributes.addFlashAttribute("fileError",
                    "gagal memproses file \"" + fileCsv.getOriginalFilename() + "\"!. silahkan cek format file anda!");
            UserHelper.errorLog("terjadi kesalahan saat memproses file \"" + fileCsv.getOriginalFilename() + "\"!");
            e.printStackTrace();
        }
        return "redirect:" + backUrl.orElse("/kopmart/admin/produk/upload/csv");
    }

    // TODO : create download sample file CSv for data product
    // download sample csv
    @GetMapping("/download/sample/produk.csv")
    public void downloadSampleCsv(HttpServletResponse response) {
        try {
            // set content response type
            response.setContentType("text/csv");
            // set file name to download
            response.setHeader("Content-Disposition",
                    "attachment; file=" + DataToCsvHelper.generateFileCsvName("produk.csv"));

            // create list object of HashMap
            List<Map<String, Object>> listData = new ArrayList<>(
                    Arrays.asList(
                            new HashMap<String, Object>() {
                                {
                                    put("nama", "kacang goreng (maksimal 100 karakter ya, ga boleh sama :D)");
                                    put("harga", 2000);
                                    put("deskripsi singkat",
                                            "ini adalah deskripsi singkat untuk produk ini (maksimal 500 karakter ya)");
                                    put("deskripsi lengkap",
                                            "ini adalah deskripsi lengkap untuk produk ini, Lorem ipsum dolor, sit amet consectetur adipisicing elit. Culpa at corrupti dolores (maksimal 1500 karakter ya)");
                                    put("kategori", "gorengan (maksimal 100 karakter ya)");
                                    put("stok", 1000);
                                }
                            },
                            new HashMap<String, Object>() {
                                {
                                    put("nama", "tahu");
                                    put("harga", 2000);
                                    put("deskripsi singkat",
                                            "ini adalah deskripsi singkat untuk produk ini ");
                                    put("deskripsi lengkap",
                                            "ini adalah deskripsi lengkap untuk produk ini, Lorem ipsum dolor, sit amet consectetur adipisicing elit. Culpa at corrupti dolores ");
                                    put("kategori", "atk (maksimal 100 karakter ya)");
                                    put("stok", 1000);
                                }
                            },
                            new HashMap<String, Object>() {
                                {
                                    put("nama", "apel");
                                    put("harga", 2000);
                                    put("deskripsi singkat",
                                            "ini adalah deskripsi singkat untuk produk ini ");
                                    put("deskripsi lengkap",
                                            "ini adalah deskripsi lengkap untuk produk ini, Lorem ipsum dolor, sit amet consectetur adipisicing elit. Culpa at corrupti dolores ");
                                    put("kategori", "buah (maksimal 100 karakter ya)");
                                    put("stok", 1000);
                                }
                            },
                            new HashMap<String, Object>() {
                                {
                                    put("nama", "tempe goreng");
                                    put("harga", 2000);
                                    put("deskripsi singkat",
                                            "ini adalah deskripsi singkat untuk produk ini ");
                                    put("deskripsi lengkap",
                                            "ini adalah deskripsi lengkap untuk produk ini, Lorem ipsum dolor, sit amet consectetur adipisicing elit. Culpa at corrupti dolores ");
                                    put("kategori", "gorengan (maksimal 100 karakter ya)");
                                    put("stok", 1000);
                                }
                            }));

            // write file data to file csv
            DataToCsvHelper.writeDataToCsv(response.getWriter(), listData);

            // force any content buffer written to client
            response.flushBuffer();
        } catch (IOException e) {

        } catch (Exception e) {
            UserHelper
                    .errorLog("terjadi kesalahan yang tidak di ketahui saat memuat file csv! silahkan hubungi admin!");
            e.printStackTrace();
        }
    }
}
