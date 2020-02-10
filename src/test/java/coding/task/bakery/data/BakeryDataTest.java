package coding.task.bakery.data;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import coding.task.bakery.dto.Pack;

@RunWith(MockitoJUnitRunner.class)
public class BakeryDataTest {

	@InjectMocks
	private BakeryData data;

	@Test
	public void findByCode() {
		List<Pack> packs = data.findByCode("MB11");
		assertNotNull(packs);
		
		packs = data.findByCode("INVALID");
		assertNull(packs);
		
		packs = data.findByCode(null);
		assertNull(packs);
	}
}
