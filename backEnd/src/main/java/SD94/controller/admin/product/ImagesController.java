package SD94.controller.admin.product;

import SD94.entity.sanPham.HinhAnh;
import SD94.repository.ProductImagesRepository;
import SD94.service.service.HinhAnhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/productImg")
public class ImagesController {

    @Autowired
    HinhAnhService hinhAnhService;

    @Autowired
    ProductImagesRepository productImagesRepository;

    @GetMapping("/list")
    public ResponseEntity<List<HinhAnh>> getImg() {
        List<HinhAnh> list = productImagesRepository.findAll();
        return ResponseEntity.ok().body(list);
    }

//    @PostMapping("/add")
//    public ResponseEntity<ProductImages> add(@RequestPart(value = "file", required = false) MultipartFile file,
//                                             @RequestPart("requestData") ProductImages images) throws IOException {
//        images.setImages(file.getBytes());
//        return ResponseEntity.ok(productImagesService.saveOrUpdate(images));
//    }

    @GetMapping("/detail/{id}")
    public HinhAnh detail(@PathVariable(value = "id") Long id) {
        HinhAnh hinhAnh = hinhAnhService.findByMaSanPham(id);
        return hinhAnh;
    }

    @PostMapping("/{productId}/uploadImage")
    public ResponseEntity<String> uploadImage(@PathVariable Long productId, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok("Image uploaded successfully.");
    }

//    @PostMapping("/saveCreate")
//    public ResponseEntity<ProductImages> saveCreate(@RequestBody ProductImages images,
//                                                    @RequestParam("file") MultipartFile file) throws IOException {
//        images.setImages(file.getBytes());
//        return productImagesService.saveCreate(images);
//    }

}
