package coding.task.bakery.data;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import coding.task.bakery.dto.Pack;
import coding.task.bakery.exception.ApiException;

@RunWith(MockitoJUnitRunner.class)
public class BakeryDataTest {

	@InjectMocks
	private BakeryData data;

	@Test
	public void findByCode() throws ApiException {
		List<Pack> packs = data.findByCode("MB11");
		assertNotNull(packs);
	}

	@Test(expected = ApiException.class)
	public void findByCode_InvalidCode() throws ApiException {
		data.findByCode("INVALID");
	}

	@Test(expected = ApiException.class)
	public void findByCode_EmptyCode() throws ApiException {
		data.findByCode(null);
	}
}
