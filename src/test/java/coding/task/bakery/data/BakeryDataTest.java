package coding.task.bakery.data;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import coding.task.bakery.entity.Pack;
import coding.task.bakery.exception.ApiException;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BakeryDataTest {

	@InjectMocks
	private BakeryData data;

	@Test
	public void findByCode() throws ApiException {
		List<Pack> packs = data.findByCode("MB11");
		assertNotNull(packs);
	}

	@Test
	public void findByCode_InvalidCode() throws ApiException {
		Assertions.assertThrows(ApiException.class, () -> {
			data.findByCode("INVALID");
		});
	}

	@Test
	public void findByCode_EmptyCode() throws ApiException {
		Assertions.assertThrows(ApiException.class, () -> {
			data.findByCode(null);
		});
	}
}
