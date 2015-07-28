package com.wiysoft.cocoon.services;

import com.wiysoft.cocoon.repositories.SlotRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Created by weiliyang on 7/27/15.
 */
public class SlotServiceImplTest {

    @Test
    public void testFindDateAndNetValueMatrixByCode() {
        SlotRepository mockedSlotRepository = Mockito.mock(SlotRepository.class);

        Object[] mockedReturn = new Object[]{
                new Object[]{"2015-03-01", 1000L},
                new Object[]{"2015-03-02", 2000L},
                new Object[]{"2015-03-03", 3000L},
                new Object[]{"2015-03-04", -1000L},
        };
        Mockito.when(mockedSlotRepository.findAllDateAndNetValueByCode("600316")).thenReturn(mockedReturn);

        SlotServiceImpl impl = new SlotServiceImpl();
        impl.setSlotRepository(mockedSlotRepository);

        Object[][] actualMatrix = impl.findDateAndNetValueMatrixByCode("600316");
        Assert.assertArrayEquals(new Object[]{"2015-03-01", 1000L, "red"}, actualMatrix[0]);
        Assert.assertArrayEquals(new Object[]{"2015-03-02", 2000L, "red"}, actualMatrix[1]);
        Assert.assertArrayEquals(new Object[]{"2015-03-03", 3000L, "red"}, actualMatrix[2]);
        Assert.assertArrayEquals(new Object[]{"2015-03-04", -1000L, "green"}, actualMatrix[3]);
    }

    @Test
    public void testFindDateAndNetValueMatrixByCode2() {
        SlotRepository mockedSlotRepository = Mockito.mock(SlotRepository.class);

        Object[] mockedReturn = new Object[0];
        Mockito.when(mockedSlotRepository.findAllDateAndNetValueByCode("600316")).thenReturn(mockedReturn);

        SlotServiceImpl impl = new SlotServiceImpl();
        impl.setSlotRepository(mockedSlotRepository);

        Object[][] actualMatrix = impl.findDateAndNetValueMatrixByCode("600316");
        Assert.assertEquals(0, actualMatrix.length);
    }

    @Test
    public void testFindDateAndNetValueMatrixByCode3() {
        SlotRepository mockedSlotRepository = Mockito.mock(SlotRepository.class);

        Mockito.when(mockedSlotRepository.findAllDateAndNetValueByCode("600316")).thenReturn(null);

        SlotServiceImpl impl = new SlotServiceImpl();
        impl.setSlotRepository(mockedSlotRepository);

        Object[][] actualMatrix = impl.findDateAndNetValueMatrixByCode("600316");
        Assert.assertEquals(0, actualMatrix.length);
    }
}
