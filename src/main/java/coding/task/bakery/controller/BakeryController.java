package coding.task.bakery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import coding.task.bakery.dto.OrderRequest;
import coding.task.bakery.dto.OrderResponse;
import coding.task.bakery.service.BakeryService;

@RestController
public class BakeryController {

	@Autowired
	private BakeryService bakeryService;
	
	@PostMapping("collect-packs")
    public ResponseEntity<List<OrderResponse>> collectPacks(@RequestBody List<OrderRequest> request) {
        List<OrderResponse> list = bakeryService.collectPacks(request);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
