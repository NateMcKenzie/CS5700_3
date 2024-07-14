import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MainTest {
    @Test
    fun stampConvertTest(){
        //Note that MDT is 7 hours behind GMT
        assertEquals("12/31/69 05:00PM", stampConvert(0))
        assertEquals("07/14/24 04:30PM", stampConvert(1720996239123))
        assertEquals("03/13/18 08:57PM", stampConvert(1520996239123))
        assertEquals("03/13/18 11:57AM", stampConvert(1520963839000))
        assertEquals("03/13/18 12:00PM", stampConvert(1520964019000))
        assertEquals("03/13/18 12:00PM", stampConvert(1520964019001))
        assertEquals("03/13/18 12:00PM", stampConvert(1520964019038))
        assertEquals("03/13/18 12:00PM", stampConvert(1520964019999))
        //17 Aug 292278994 07:12:55 GMT
        assertEquals("08/17/94 01:12AM", stampConvert(Long.MAX_VALUE))
        assertFailsWith<IllegalArgumentException>{stampConvert(Long.MIN_VALUE)}
    }
}